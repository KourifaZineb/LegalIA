package com.chatbot.userservice.dtos;

import com.chatbot.userservice.enums.Language;
import com.chatbot.userservice.enums.Speciality;
import com.chatbot.userservice.enums.lawyerStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


public class LawyerDTO {

    private Long lawyerId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Language languages;
    private lawyerStatus status;
    private Speciality specialization;
    private double hourlyRate;
    private double rating;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

 /*   private AdminDTO admin;

    private Set<LawyerConnectionsDTO> connections = new HashSet<>();

    private Set<NotificationsDTO> notifications = new HashSet<>();*/

    public LawyerDTO(Long lawyerId, String name, String email, String password, String phoneNumber, Language languages, lawyerStatus status, Speciality specialization, double hourlyRate, double rating, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.lawyerId = lawyerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.languages = languages;
        this.status = status;
        this.specialization = specialization;
        this.hourlyRate = hourlyRate;
        this.rating = rating;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public LawyerDTO() {
    }

    public Long getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(Long lawyerId) {
        this.lawyerId = lawyerId;
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

    public Language getLanguages() {
        return languages;
    }

    public void setLanguages(Language languages) {
        this.languages = languages;
    }

    public lawyerStatus getStatus() {
        return status;
    }

    public void setStatus(lawyerStatus status) {
        this.status = status;
    }

    public Speciality getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Speciality specialization) {
        this.specialization = specialization;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
        return "LawyerDTO{" +
                "lawyerId=" + lawyerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", languages=" + languages +
                ", status=" + status +
                ", specialization=" + specialization +
                ", hourlyRate=" + hourlyRate +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}

