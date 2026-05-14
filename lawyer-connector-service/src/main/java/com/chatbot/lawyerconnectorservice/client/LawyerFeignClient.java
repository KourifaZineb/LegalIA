package com.chatbot.lawyerconnectorservice.client;

import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.commonlibrary.enums.LawyerStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient (name = "lawyer-service", url = "${lawyer-service.url}")
public interface LawyerFeignClient {

    @GetMapping("/api/lawyers/{id}")
    LawyerDTO getLawyerById(@PathVariable Long id);

    @GetMapping("/api/lawyers/status/{status}")
    List<LawyerDTO> getLawyersByStatus(@PathVariable LawyerStatus status);
}
