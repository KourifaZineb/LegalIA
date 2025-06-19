package com.chatbot.useractivityservice.services;

import com.chatbot.commonlibrary.dtos.UserActivityDTO;

import java.util.List;


public interface UserActivityService {
    UserActivityDTO logActivity(UserActivityDTO dto);
    List<UserActivityDTO> getActivitiesByUserId(Long userId);
    List<UserActivityDTO> getAllActivities();
}
