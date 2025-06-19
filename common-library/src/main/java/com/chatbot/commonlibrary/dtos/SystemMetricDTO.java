package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.MetricCategory;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SystemMetricDTO {
    private Long id;
    private String metricName;
    private Long adminId;
    private Double metricValue;
    private MetricCategory category;
    private Instant timestamp;

    private AdminDTO adminDTO;
}
