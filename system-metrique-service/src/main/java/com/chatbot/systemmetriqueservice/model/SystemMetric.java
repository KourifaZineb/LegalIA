package com.chatbot.systemmetriqueservice.model;


import com.chatbot.adminservice.model.Admin;
import com.chatbot.commonlibrary.enums.MetricCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "system_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    private Long id;

    @Column(nullable = false)
    private Long adminId;

    @Column(name = "metric_name", nullable = false)
    private String metricName;

    @Column(name = "metric_value", nullable = false)
    private Double metricValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private MetricCategory category;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();
}
