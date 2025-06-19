package com.chatbot.notificationservice.config;

import com.chatbot.commonlibrary.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "lawyer-service", url = "${lawyer-service.url}")
public interface LawyerFeignClient {
    @GetMapping("/lawyers/{id}")
    UserDTO getLawyersById(@PathVariable Long id);
}
