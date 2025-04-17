package com.chatbot.userservice.dtos;

import com.chatbot.userservice.entities.enums.MetricCategory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


public class SystemMetricsDTO {
    private Long metricId;
    private String metricName;
    private Float value;
    private LocalDateTime timestamp;
    private MetricCategory category;
    private AdminDTO admin;
    private Set<UserDTO> users = new HashSet<>();

    public SystemMetricsDTO(Long metricId, String metricName, Float value, LocalDateTime timestamp, MetricCategory category, AdminDTO admin, Set<UserDTO> users) {
        this.metricId = metricId;
        this.metricName = metricName;
        this.value = value;
        this.timestamp = timestamp;
        this.category = category;
        this.admin = admin;
        this.users = users;
    }

    public SystemMetricsDTO() {
    }

    public Long getMetricId() {
        return metricId;
    }

    public void setMetricId(Long metricId) {
        this.metricId = metricId;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public MetricCategory getCategory() {
        return category;
    }

    public void setCategory(MetricCategory category) {
        this.category = category;
    }

    public AdminDTO getAdmin() {
        return admin;
    }

    public void setAdmin(AdminDTO admin) {
        this.admin = admin;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "SystemMetrics{" +
                "metricId=" + metricId +
                ", metricName='" + metricName + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", category=" + category +
                ", admin=" + admin +
                ", users=" + users +
                '}';
    }
}
