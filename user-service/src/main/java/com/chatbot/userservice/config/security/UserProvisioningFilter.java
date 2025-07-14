/*
package com.chatbot.userservice.config.security;


import java.io.IOException;

import com.chatbot.userservice.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class UserProvisioningFilter extends OncePerRequestFilter {


    private final UserService userService;

    public UserProvisioningFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("🛠️  ProvisioningFilter invoked, auth = {}", auth);

        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            log.debug("🛠️  JWT claims: sub={}, email={}, given_name={}, family_name={}",
                    jwt.getSubject(),
                    jwt.getClaim("email"),
                    jwt.getClaim("given_name"),
                    jwt.getClaim("family_name"));

            userService.provisionUser(
                    jwt.getSubject(),
                    jwt.getClaim("email"),
                    jwt.getClaim("given_name"),
                    jwt.getClaim("family_name")
            );
            log.debug("🛠️  provisionUser() returned");
        } else {
            log.debug("🛠️  no JWTAuthToken, skipping");
        }

        chain.doFilter(req, res);
    }
}
*/
package com.chatbot.userservice.config.security;

import java.io.IOException;

import com.chatbot.userservice.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserService userService;

    public UserProvisioningFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("🛠️  ProvisioningFilter invoked, auth = {}", auth);

        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            log.debug("🛠️  JWT claims: sub={}, email={}, given_name={}, family_name={}",
                    jwt.getSubject(),
                    jwt.getClaim("email"),
                    jwt.getClaim("given_name"),
                    jwt.getClaim("family_name"));

            /*userService.provisionUser(
                    jwt.getSubject(),
                    jwt.getClaim("email"),
                    jwt.getClaim("given_name"),
                    jwt.getClaim("family_name")
            );*/
            log.debug("🛠️  provisionUser() returned");
        } else {
            log.debug("🛠️  no JwtAuthenticationToken, skipping provisioning");
        }

        chain.doFilter(req, res);
    }
}
