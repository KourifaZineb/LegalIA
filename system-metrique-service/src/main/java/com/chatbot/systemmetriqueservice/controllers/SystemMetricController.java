package com.chatbot.systemmetriqueservice.controllers;

import com.chatbot.commonlibrary.dtos.SystemMetricDTO;
import com.chatbot.commonlibrary.enums.MetricCategory;
import com.chatbot.systemmetriqueservice.services.SystemMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/system-metrics")

public class SystemMetricController {

    private final SystemMetricService service;

    public SystemMetricController(SystemMetricService service) {
        this.service = service;
    }

    @PostMapping
    public SystemMetricDTO create(@RequestBody SystemMetricDTO dto) {
        return service.addMetric(dto);
    }

    @GetMapping
    public List<SystemMetricDTO> getAll() {
        return service.getAllMetrics();
    }

    @GetMapping("/admin/{adminId}")
    public List<SystemMetricDTO> getByAdmin(@PathVariable Long adminId) {
        return service.getMetricsByAdmin(adminId);
    }

    /*@GetMapping("/category/{category}")
    public List<SystemMetricDTO> getByCategory(@PathVariable String category) {
        return service.getMetricsByCategory(category);
    }*/

    @GetMapping("/category/{category}")
    public List<SystemMetricDTO> getByCategory(@PathVariable MetricCategory category) {
        return service.getMetricsByCategory(category);
    }


}
