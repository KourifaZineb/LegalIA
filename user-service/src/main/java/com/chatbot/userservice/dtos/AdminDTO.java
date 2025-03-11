package com.chatbot.userservice.dtos;

import com.chatbot.userservice.enums.Role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


public class AdminDTO {

    private Long adminId;
    private String name;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    private Set<SystemMetricsDTO> systemMetrics = new HashSet<>();

    private Set<LawyerDTO> monitoredLawyers = new HashSet<>();

    public AdminDTO(Long adminId, String name, String email, String password, Role role, LocalDateTime createdAt, LocalDateTime lastLogin, Set<SystemMetricsDTO> systemMetrics, Set<LawyerDTO> monitoredLawyers) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.systemMetrics = systemMetrics;
        this.monitoredLawyers = monitoredLawyers;
    }

    public AdminDTO() {
    }

    public Long getAdminId() {
        return adminId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public Set<SystemMetricsDTO> getSystemMetrics() {
        return systemMetrics;
    }

    public Set<LawyerDTO> getMonitoredLawyers() {
        return monitoredLawyers;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setSystemMetrics(Set<SystemMetricsDTO> systemMetrics) {
        this.systemMetrics = systemMetrics;
    }

    public void setMonitoredLawyers(Set<LawyerDTO> monitoredLawyers) {
        this.monitoredLawyers = monitoredLawyers;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                ", systemMetrics=" + systemMetrics +
                ", monitoredLawyers=" + monitoredLawyers +
                '}';
    }
}
