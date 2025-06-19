package com.chatbot.systemmetriqueservice.repository;

import com.chatbot.commonlibrary.enums.MetricCategory;
import com.chatbot.systemmetriqueservice.model.SystemMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface SystemMetricRepository extends JpaRepository<SystemMetric, Long> {
    List<SystemMetric> findByAdminId(Long adminId);

    List<SystemMetric> findByCategory(MetricCategory category);
}
