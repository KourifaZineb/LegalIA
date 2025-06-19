package com.chatbot.notificationservice.services;

import com.chatbot.commonlibrary.dtos.NotificationDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO send(NotificationDTO dto);
    List<NotificationDTO> getUserNotifications(Long userId);
    void markAsRead(Long id);

}
