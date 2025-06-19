package com.chatbot.lawyerconnectorservice.config;

import com.chatbot.commonlibrary.dtos.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-service", url = "${notification-service.url}")
public interface NotificationFeignClient {
    @PostMapping("/api/notifications")
    NotificationDTO send(NotificationDTO dto);
}