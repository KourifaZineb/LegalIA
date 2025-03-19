package com.chatbot.userservice;

import com.chatbot.userservice.entities.Admin;
import com.chatbot.userservice.entities.Lawyer;
import com.chatbot.userservice.entities.User;
import com.chatbot.userservice.enums.*;
import com.chatbot.userservice.repository.AdminRepository;
import com.chatbot.userservice.repository.LawyerRepository;
import com.chatbot.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@SpringBootApplication

public class UserServiceApplication {

	@Autowired
	private Environment env;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(UserServiceApplication.class);
		Environment env = app.run(args).getEnvironment();

		LOGGER.info("Access URLs:\n----------------------------------------------------------\n\t" +
						"Local: \t\thttp://127.0.0.1:{}/swagger-ui/#/\n\t" +
						"External: \thttp://{}:{}/swagger-ui/#/\n----------------------------------------------------------",

				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port")
		);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, LawyerRepository lawyerRepository){
		return args -> {
			userRepository.save(User.builder()
					.email("zinebkourifa@gmail.com")
					.name("zineb")
					.status(userStatus.ACTIF)
					.password("zineb12")
					.preferredLanguage(Language.FRANÇAIS)
					.phoneNumber("0639860383")
					.createdAt(LocalDateTime.now())
					.lastLogin(LocalDateTime.now())
					.build());
			lawyerRepository.save(Lawyer.builder()
							.email("salwamounji5@gmail.com")
							.name("salwa mounji")
							.phoneNumber("0788966320")
							.password("salwasalwa")
							.rating(5000.00)
							.hourlyRate(200.00)
							.specialization(Speciality.LOCATIF)
							.languages(Language.ARABE)
							.status(lawyerStatus.DISPONIBLE)
							.createdAt(LocalDateTime.now())
							.lastLogin(LocalDateTime.now())
					.build());
		};
	}
}