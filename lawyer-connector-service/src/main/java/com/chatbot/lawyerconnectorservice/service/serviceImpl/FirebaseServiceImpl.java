package com.chatbot.lawyerconnectorservice.service.serviceImpl;

import com.chatbot.commonlibrary.dtos.NotificationDTO;
import com.chatbot.lawyerconnectorservice.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public CompletableFuture<String> sendNotification(NotificationDTO notificationDTO) {
        try {
            Message message = Message.builder()
                    .setToken(notificationDTO.getFcmToken())
                    .setNotification(Notification.builder()
                            .setTitle(notificationDTO.getTitle())
                            .setBody(notificationDTO.getMessage())
                            .build())
                    .putData("type", notificationDTO.getType())
                    .putData("connectionId", String.valueOf(notificationDTO.getConnectionId()))
                    .putData("userId", String.valueOf(notificationDTO.getUserId()))
                    .putData("lawyerId", String.valueOf(notificationDTO.getLawyerId()))
                    .setAndroidConfig(AndroidConfig.builder()
                            .setNotification(AndroidNotification.builder()
                                    .setIcon("ic_notification")
                                    .setColor("#FF6B35")
                                    .setPriority(AndroidNotification.Priority.HIGH)
                                    .build())
                            .build())
                    .setApnsConfig(ApnsConfig.builder()
                            .setAps(Aps.builder()
                                    .setAlert(ApsAlert.builder()
                                            .setTitle(notificationDTO.getTitle())
                                            .setBody(notificationDTO.getMessage())
                                            .build())
                                    .setBadge(1)
                                    .setSound("default")
                                    .build())
                            .build())
                    .build();

            // Return the CompletableFuture so caller can handle success/failure
            return CompletableFuture.supplyAsync(() -> {
                try {
                    String response = FirebaseMessaging.getInstance().send(message);
                    log.info("Successfully sent message: {}", response);
                    return response;
                } catch (FirebaseMessagingException e) {
                    log.error("Error sending Firebase message to token: {}, Error: {}",
                            notificationDTO.getFcmToken(), e.getMessage());
                    throw new RuntimeException("Failed to send notification: " + e.getMessage(), e);
                }
            });

        } catch (Exception e) {
            log.error("Failed to create Firebase notification", e);
            // Return a failed CompletableFuture
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }

    @Override
    public CompletableFuture<BatchResponse> sendBatchNotification(List<NotificationDTO> notifications) {
        try {
            List<Message> messages = notifications.stream()
                    .map(notification -> Message.builder()
                            .setToken(notification.getFcmToken())
                            .setNotification(Notification.builder()
                                    .setTitle(notification.getTitle())
                                    .setBody(notification.getMessage())
                                    .build())
                            .putData("type", notification.getType())
                            .putData("connectionId", String.valueOf(notification.getConnectionId()))
                            .build())
                    .collect(Collectors.toList());

            return CompletableFuture.supplyAsync(() -> {
                try {
                    BatchResponse response = FirebaseMessaging.getInstance().sendAll(messages);
                    log.info("Successfully sent batch messages: {} successful, {} failed",
                            response.getSuccessCount(), response.getFailureCount());

                    // Log failed messages for debugging
                    if (response.getFailureCount() > 0) {
                        response.getResponses().stream()
                                .filter(sendResponse -> !sendResponse.isSuccessful())
                                .forEach(failedResponse ->
                                        log.error("Failed to send message: {}", failedResponse.getException().getMessage()));
                    }

                    return response;
                } catch (FirebaseMessagingException e) {
                    log.error("Error sending batch Firebase messages", e);
                    throw new RuntimeException("Failed to send batch notifications: " + e.getMessage(), e);
                }
            });

        } catch (Exception e) {
            log.error("Failed to create batch Firebase notifications", e);
            CompletableFuture<BatchResponse> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }
}