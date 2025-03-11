package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.UserActivity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserActivityService {
    UserActivity createActivity(UserActivity activity);
    Optional<UserActivity> getActivityById(Long id);
    List<UserActivity> getActivitiesByUserId(Long userId);
    List<UserActivity> getActivitiesByType(String type);
    List<UserActivity> getActivitiesByDateRange(LocalDateTime start, LocalDateTime end);
    UserActivity updateActivity(UserActivity activity);
    void deleteActivity(Long id);
    void logActivity(Long userId, String activityType, String details);
}
