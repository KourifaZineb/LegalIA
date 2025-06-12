package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.UserActivity;
import com.chatbot.userservice.dtos.request.LogActivityRequest;
import com.chatbot.userservice.entities.enums.ActivityType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserActivityService {
    UserActivity createActivity(UserActivity activity);
    Optional<UserActivity> getActivityById(Long id);
    List<UserActivity> getActivitiesByUserId(Long userId);
    List<UserActivity> getActivitiesByType(ActivityType type);
    List<UserActivity> getActivitiesByDateRange(LocalDateTime start, LocalDateTime end);
    UserActivity updateActivity(UserActivity activity);
    void deleteActivity(Long id);
}
