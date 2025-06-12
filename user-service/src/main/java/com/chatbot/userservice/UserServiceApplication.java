package com.chatbot.userservice;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.UserStatus;
import com.chatbot.userservice.model.User;
import com.chatbot.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApplication {


	private final Environment env;

	private final PasswordEncoder passwordEncoder;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(UserServiceApplication.class);
		Environment env = app.run(args).getEnvironment();

		LOGGER.info("Access URLs:\n----------------------------------------------------------\n\t" +
						"Local: \t\thttp://localhost:{}/swagger-ui/index.html#/\n\t" +
						"External: \thttp://{}:{}/swagger-ui/#/\n----------------------------------------------------------",

				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port")
		);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository){
		return args -> {
			Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();

			userRepository.save(User.builder()
							.email("zinebkourifa@gmail.com")
							.name("zineb")
							.status(UserStatus.ACTIVE)
							.password(passwordEncoder.encode("zineb12"))
							.preferredLanguage(Language.FR)
							.phoneNumber("0639860383")
							.createdAt(instant)
							.lastLogin(instant)
							.solde(800.00)
							.build());
		};
	}
}