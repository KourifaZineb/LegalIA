package com.chatbot.userservice.services;

import javax.management.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Notification createNotification(Notification notification);
    Optional<Notification> getNotificationById(Integer id);
    List<Notification> getNotificationsByUserId(Integer userId);
    List<Notification> getNotificationsByLawyerId(Integer lawyerId);
    List<Notification> getNotificationsByType(String type);
    List<Notification> getUnreadNotifications(Integer userId);
    Notification updateNotification(Notification notification);
    void deleteNotification(Integer id);
    void markAsRead(Integer notificationId);
}
