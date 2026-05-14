package com.chatbot.lawyerconnectorservice.client;

import com.chatbot.commonlibrary.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${user-service.url:http://localhost:8081}")
public interface UserFeignClient {

    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") String id,
                        @RequestHeader("Authorization") String token);
}