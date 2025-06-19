package com.chatbot.systemmetriqueservice;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.MetricCategory;
import com.chatbot.commonlibrary.enums.UserStatus;
import com.chatbot.systemmetriqueservice.model.SystemMetric;
import com.chatbot.systemmetriqueservice.repository.SystemMetricRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
public class SystemMetriqueServiceApplication {

    private final Environment env;

    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemMetriqueServiceApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(SystemMetriqueServiceApplication.class);
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
    CommandLineRunner commandLineRunner(SystemMetricRepository systemMetricRepository){
        return args -> {
            Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();

            systemMetricRepository.save(SystemMetric.builder()
                    .adminId(1L)
                    .category(MetricCategory.USAGE)
                    .metricValue(100.0)
                    .metricName("CPU Usage")
                    .timestamp(instant)
                    .build());
        };
    }
}
