package com.chatbot.useractivityservice;

import com.chatbot.commonlibrary.enums.ActivityType;
import com.chatbot.useractivityservice.model.UserActivity;
import com.chatbot.useractivityservice.repository.UserActivityRepository;
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
public class UserActivityServiceApplication {

    private final Environment env;

    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserActivityServiceApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(UserActivityServiceApplication.class);
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
    CommandLineRunner commandLineRunner(UserActivityRepository userActivityRepository){
        return args -> {
            Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
            userActivityRepository.save(UserActivity.builder()
                    .userId(1L)
                    .activityType(ActivityType.LOGIN)
                    .details("Connexion réussie")
                    .timestamp(instant)
                    .build());
        };
    }
}
