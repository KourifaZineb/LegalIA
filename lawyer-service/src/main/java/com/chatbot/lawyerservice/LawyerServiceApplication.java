package com.chatbot.lawyerservice;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.LawyerStatus;
import com.chatbot.commonlibrary.enums.Role;
import com.chatbot.commonlibrary.enums.Specialization;
import com.chatbot.lawyerservice.model.Lawyer;
import com.chatbot.lawyerservice.repository.LawyerRepository;
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
public class LawyerServiceApplication {



    private final Environment env;

    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(LawyerServiceApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(LawyerServiceApplication.class);
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
    CommandLineRunner commandLineRunner(LawyerRepository lawyerRepository){
        return args -> {
            Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
            lawyerRepository.save(Lawyer.builder()
                    .email("salwamounji5@gmail.com")
                    .name("salwa mounji")
                    .phoneNumber("0788966320")
                    .password(passwordEncoder.encode("salwasalwa"))
                    .adresse("Casablanca, Morocco")
                    .specialization(Specialization.FAMILY_LAW)
                    .languages(Language.arabe)
                    .role(Role.LAWYER)
                    .status(LawyerStatus.AVAILABLE)
                    .createdAt(instant)
                    .lastLogin(instant)
                    .build());
        };
    }
}
