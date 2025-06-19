package com.chatbot.systemmetriqueservice.services;

import com.chatbot.commonlibrary.dtos.SystemMetricDTO;
import com.chatbot.commonlibrary.enums.MetricCategory;

import java.util.List;

public interface SystemMetricService {
    SystemMetricDTO addMetric(SystemMetricDTO dto);
    List<SystemMetricDTO> getAllMetrics();
    List<SystemMetricDTO> getMetricsByAdmin(Long adminId);
    List<SystemMetricDTO> getMetricsByCategory(MetricCategory category);
}
