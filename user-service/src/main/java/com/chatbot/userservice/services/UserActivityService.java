package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.UserActivity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserActivityService {
    UserActivity createActivity(UserActivity activity);
    Optional<UserActivity> getActivityById(Integer id);
    List<UserActivity> getActivitiesByUserId(Integer userId);
    List<UserActivity> getActivitiesByType(String type);
    List<UserActivity> getActivitiesByDateRange(LocalDateTime start, LocalDateTime end);
    UserActivity updateActivity(UserActivity activity);
    void deleteActivity(Integer id);
    void logActivity(Integer userId, String activityType, String details);
}
