/*
package com.chatbot.userservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserProvisioningFilter provisioningFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // all endpoints require a valid JWT
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                // configure resource‐server to decode and validate JWTs
                .oauth2ResourceServer(o -> o
                        .jwt(Customizer.withDefaults())
                );

        // run the provisioning filter after Spring’s JWT filter
        http.addFilterAfter(provisioningFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
}*/
