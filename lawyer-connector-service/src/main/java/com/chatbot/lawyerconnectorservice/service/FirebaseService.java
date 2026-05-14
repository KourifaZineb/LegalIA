package com.chatbot.lawyerconnectorservice.service;

import com.chatbot.commonlibrary.dtos.NotificationDTO;
import com.google.firebase.messaging.BatchResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FirebaseService {
    CompletableFuture<String> sendNotification(NotificationDTO notification);
    CompletableFuture<BatchResponse> sendBatchNotification(List<NotificationDTO> notifications);
}
