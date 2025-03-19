package com.chatbot.userservice.entities;

import com.chatbot.userservice.enums.MetricCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_metrics")
public class SystemMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;
    private String metricName;
    @Column(name = "metric_value")
    private Float value;
    private LocalDateTime timestamp;
    private MetricCategory category;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToMany
    @JoinTable(
            name = "user_metrics",
            joinColumns = @JoinColumn(name = "metric_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();
}
