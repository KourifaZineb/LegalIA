package com.chatbot.userservice.dtos;

import com.chatbot.userservice.entities.enums.Language;
import com.chatbot.userservice.entities.enums.userStatus;

import java.time.LocalDateTime;


public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private userStatus status;
    private Language preferredLanguage;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    public UserDTO(Long userId, String name, String email, String password, String phoneNumber, userStatus status, Language preferredLanguage, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.preferredLanguage = preferredLanguage;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public UserDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public userStatus getStatus() {
        return status;
    }

    public void setStatus(userStatus status) {
        this.status = status;
    }

    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(Language preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                ", preferredLanguage=" + preferredLanguage +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
