package com.chatbot.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

	@Autowired
	private Environment env;

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

	@Override
	public void run(String... args) throws Exception {
		// Vous pouvez ajouter votre logique d'initialisation ici
	}
}