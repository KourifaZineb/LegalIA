package com.chatbot.adminservice;

import com.chatbot.adminservice.model.Admin;
import com.chatbot.adminservice.repository.AdminRepository;
import com.chatbot.commonlibrary.enums.Role;
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
public class AdminServiceApplication {

    private final Environment env;
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(AdminServiceApplication.class);
        Environment env = app.run(args).getEnvironment();

        LOGGER.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://localhost:{}/swagger-ui/index.html\n\t" +
                        "External: \thttp://{}:{}/swagger-ui/index.html\n----------------------------------------------------------",
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port")
        );
    }

    @Bean
    CommandLineRunner commandLineRunner(AdminRepository adminRepository) {
        return args -> {
            Instant now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();

            Admin admin = new Admin();
            admin.setEmail("zinebkourifa@gmail.com");
            admin.setName("zineb");
            admin.setPassword(passwordEncoder.encode("zineb12"));
            admin.setCreatedAt(now);
            admin.setLastLogin(now);
            admin.setRole(Role.ADMIN);

            adminRepository.save(admin);
        };
    }
}
