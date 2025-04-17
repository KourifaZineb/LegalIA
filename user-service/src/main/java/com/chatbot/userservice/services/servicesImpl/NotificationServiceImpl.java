package com.chatbot.userservice.services.servicesImpl;

import com.chatbot.userservice.services.NotificationService;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public Notification createNotification(Notification notification) {
        return null;
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Notification> getNotificationsByLawyerId(Long lawyerId) {
        return null;
    }

    @Override
    public List<Notification> getNotificationsByType(String type) {
        return null;
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return null;
    }

    @Override
    public Notification updateNotification(Notification notification) {
        return null;
    }

    @Override
    public void deleteNotification(Long id) {

    }

    @Override
    public void markAsRead(Long notificationId) {

    }
}
