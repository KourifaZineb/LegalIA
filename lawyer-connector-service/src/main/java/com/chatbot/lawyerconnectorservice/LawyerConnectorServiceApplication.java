package com.chatbot.lawyerconnectorservice;

import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.LawyerStatus;
import com.chatbot.commonlibrary.enums.Specialization;
import com.chatbot.lawyerconnectorservice.model.Connection;
import com.chatbot.lawyerconnectorservice.repository.ConnectionRepository;
import com.chatbot.lawyerservice.LawyerServiceApplication;
import com.chatbot.lawyerservice.model.Lawyer;
import com.chatbot.lawyerservice.repository.LawyerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class
LawyerConnectorServiceApplication {

    private final Environment env;

    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(LawyerConnectorServiceApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(LawyerConnectorServiceApplication.class);
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
    CommandLineRunner commandLineRunner(ConnectionRepository connectionRepository){
        return args -> {
            Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
            connectionRepository.save(Connection.builder()
                    .lawyerId(1L)
                    .userId(1L)
                    .caseDescription("Need help with a contract dispute")
                    .requestDate(instant)
                    .status(ConnectionStatus.ACCEPTED)
                    .build());
        };
    }
}
