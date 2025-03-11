package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.SystemMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SystemMetricsRepository extends JpaRepository<SystemMetrics, Long> {
    List<SystemMetrics> findByCategory(String category);
    List<SystemMetrics> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<SystemMetrics> findByMetricName(String metricName);
}
