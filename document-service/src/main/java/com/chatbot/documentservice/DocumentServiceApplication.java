package com.chatbot.documentservice;

import com.chatbotservice.ChatbotServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // Change this import
// Remove: import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.chatbot.documentservice.repository") // Change this line
public class DocumentServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(DocumentServiceApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOGGER.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://localhost:{}/swagger-ui/index.html#/\n\t" +
                        "External: \thttp://{}:{}/swagger-ui/#/\n----------------------------------------------------------",
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port")
        );
    }
}