package com.chatbot.commonlibrary.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "LegalAI Micro‑services API",
                version = "1.0.0",
                description = "Documentation centralisée des APIs",
                contact = @Contact(name = "LegalAI Team", email = "support@legalai.com")
        ),
        servers = @Server(url = "/", description = "Default Server")
)
public class OpenApiConfig { }