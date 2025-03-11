package com.chatbot.userservice.services;

import javax.management.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Notification createNotification(Notification notification);
    Optional<Notification> getNotificationById(Long id);
    List<Notification> getNotificationsByUserId(Long userId);
    List<Notification> getNotificationsByLawyerId(Long lawyerId);
    List<Notification> getNotificationsByType(String type);
    List<Notification> getUnreadNotifications(Long userId);
    Notification updateNotification(Notification notification);
    void deleteNotification(Long id);
    void markAsRead(Long notificationId);
}
