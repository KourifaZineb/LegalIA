package com.chatbot.lawyerconnectorservice.config;

import com.chatbot.commonlibrary.dtos.LawyerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "lawyer-service", url = "${lawyer-service.url}")
public interface LawyerFeignClient {
    @GetMapping("/lawyers/{id}")
    LawyerDTO getLawyerById(@PathVariable Long id);
}