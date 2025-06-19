package com.chatbot.systemmetriqueservice.config;

import com.chatbot.commonlibrary.dtos.AdminDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "admin-service",  url = "${admin-service.url}")
public interface AdminFeignClient {
    @GetMapping("/admins/{id}")
    AdminDTO getAdminById(@PathVariable Long id);
}