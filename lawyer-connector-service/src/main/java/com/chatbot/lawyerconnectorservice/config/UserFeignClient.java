package com.chatbot.lawyerconnectorservice.config;

import com.chatbot.commonlibrary.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserFeignClient {
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
