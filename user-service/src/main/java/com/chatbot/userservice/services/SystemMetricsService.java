package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.SystemMetrics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SystemMetricsService {
    SystemMetrics createMetric(SystemMetrics metric);
    Optional<SystemMetrics> getMetricById(Long id);
    List<SystemMetrics> getAllMetrics();
    List<SystemMetrics> getMetricsByCategory(String category);
    List<SystemMetrics> getMetricsByDateRange(LocalDateTime start, LocalDateTime end);
    SystemMetrics updateMetric(SystemMetrics metric);
    void deleteMetric(Long id);
    void logMetric(String name, Float value, String category);
}
