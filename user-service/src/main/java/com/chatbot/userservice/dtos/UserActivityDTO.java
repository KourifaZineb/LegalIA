package com.chatbot.userservice.dtos;

import com.chatbot.userservice.enums.ActivityType;

import java.time.LocalDateTime;


public class UserActivityDTO {
    private Long activityId;
    private ActivityType activityType;
    private LocalDateTime timestamp;
    private String details;
    private UserDTO user;

    public UserActivityDTO(Long activityId, ActivityType activityType, LocalDateTime timestamp, String details, UserDTO user) {
        this.activityId = activityId;
        this.activityType = activityType;
        this.timestamp = timestamp;
        this.details = details;
        this.user = user;
    }

    public UserActivityDTO() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "activityId=" + activityId +
                ", activityType=" + activityType +
                ", timestamp=" + timestamp +
                ", details='" + details + '\'' +
                ", user=" + user +
                '}';
    }
}
