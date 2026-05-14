package com.chatbot.lawyerconnectorservice.service;

import com.chatbot.lawyerconnectorservice.model.Connection;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {
    CompletableFuture notifyLawyerNewRequest(Connection connection);
    CompletableFuture notifyUserConnectionAccepted(Connection connection);
    CompletableFuture notifyUserConnectionRejected(Connection connection);
}