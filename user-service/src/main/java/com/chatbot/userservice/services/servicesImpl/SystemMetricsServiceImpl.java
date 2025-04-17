package com.chatbot.userservice.services.servicesImpl;

import com.chatbot.userservice.entities.SystemMetrics;
import com.chatbot.userservice.services.SystemMetricsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SystemMetricsServiceImpl implements SystemMetricsService {
    @Override
    public SystemMetrics createMetric(SystemMetrics metric) {
        return null;
    }

    @Override
    public Optional<SystemMetrics> getMetricById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SystemMetrics> getAllMetrics() {
        return null;
    }

    @Override
    public List<SystemMetrics> getMetricsByCategory(String category) {
        return null;
    }

    @Override
    public List<SystemMetrics> getMetricsByDateRange(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public SystemMetrics updateMetric(SystemMetrics metric) {
        return null;
    }

    @Override
    public void deleteMetric(Long id) {

    }

    @Override
    public void logMetric(String name, Float value, String category) {

    }
}
