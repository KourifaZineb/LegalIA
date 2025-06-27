package com.chatbotservice;

import com.chatbot.userservice.UserServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ChatbotServiceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotServiceApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(ChatbotServiceApplication.class);
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
