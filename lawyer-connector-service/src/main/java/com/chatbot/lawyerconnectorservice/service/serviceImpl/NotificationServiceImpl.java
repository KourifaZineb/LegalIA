package com.chatbot.lawyerconnectorservice.service.serviceImpl;

import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.commonlibrary.dtos.NotificationDTO;
import com.chatbot.commonlibrary.dtos.UserDTO;
import com.chatbot.lawyerconnectorservice.client.LawyerFeignClient;
import com.chatbot.lawyerconnectorservice.client.UserFeignClient;
import com.chatbot.lawyerconnectorservice.model.Connection;
import com.chatbot.lawyerconnectorservice.service.FirebaseService;
import com.chatbot.lawyerconnectorservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final FirebaseService firebaseService;
    private final UserFeignClient userFeignClient;
    private final LawyerFeignClient lawyerFeignClient;
    private final HttpServletRequest request;

    @Override
    public CompletableFuture<Boolean> notifyLawyerNewRequest(Connection connection) {
        try {
            String authorizationHeader = getAuthorizationHeader();
            UserDTO user = userFeignClient.getUserById(connection.getUserId(), authorizationHeader);
            LawyerDTO lawyer = lawyerFeignClient.getLawyerById(connection.getLawyerId());

            if (lawyer.getFcmToken() == null || lawyer.getFcmToken().isEmpty()) {
                log.warn("Lawyer {} has no FCM token, cannot send notification", lawyer.getId());
                return CompletableFuture.completedFuture(false);
            }

            NotificationDTO notification = NotificationDTO.builder()
                    .title("Nouvelle demande de connexion")
                    .message(String.format("%s %s souhaite se connecter avec vous",
                            user.getFirstName(), user.getLastName()))
                    .fcmToken(lawyer.getFcmToken())
                    .type("NEW_CONNECTION_REQUEST")
                    .connectionId(connection.getId())
                    .userId(connection.getUserId())
                    .lawyerId(connection.getLawyerId())
                    .build();

            return firebaseService.sendNotification(notification)
                    .thenApply(response -> {
                        log.info("Successfully notified lawyer {} about new request from user {}",
                                lawyer.getId(), user.getId());
                        return true;
                    })
                    .exceptionally(throwable -> {
                        log.error("Failed to notify lawyer {} about new request: {}",
                                lawyer.getId(), throwable.getMessage());
                        return false;
                    });

        } catch (Exception e) {
            log.error("Failed to send new request notification", e);
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public CompletableFuture<Boolean> notifyUserConnectionAccepted(Connection connection) {
        try {
            String authorizationHeader = getAuthorizationHeader();
            UserDTO user = userFeignClient.getUserById(connection.getUserId(), authorizationHeader);
            LawyerDTO lawyer = lawyerFeignClient.getLawyerById(connection.getLawyerId());

            if (user.getFcmToken() == null || user.getFcmToken().isEmpty()) {
                log.warn("User {} has no FCM token, cannot send notification", user.getId());
                return CompletableFuture.completedFuture(false);
            }

            NotificationDTO notification = NotificationDTO.builder()
                    .title("Demande de connexion acceptée")
                    .message(String.format("Maître %s a accepté votre demande de connexion",
                            lawyer.getName()))
                    .fcmToken(user.getFcmToken())
                    .type("CONNECTION_ACCEPTED")
                    .connectionId(connection.getId())
                    .userId(connection.getUserId())
                    .lawyerId(connection.getLawyerId())
                    .build();

            return firebaseService.sendNotification(notification)
                    .thenApply(response -> {
                        log.info("Successfully notified user {} about connection acceptance", user.getId());
                        return true;
                    })
                    .exceptionally(throwable -> {
                        log.error("Failed to notify user {} about connection acceptance: {}",
                                user.getId(), throwable.getMessage());
                        return false;
                    });

        } catch (Exception e) {
            log.error("Failed to send connection accepted notification", e);
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public CompletableFuture<Boolean> notifyUserConnectionRejected(Connection connection) {
        try {
            String authorizationHeader = getAuthorizationHeader();
            UserDTO user = userFeignClient.getUserById(connection.getUserId(), authorizationHeader);
            LawyerDTO lawyer = lawyerFeignClient.getLawyerById(connection.getLawyerId());

            if (user.getFcmToken() == null || user.getFcmToken().isEmpty()) {
                log.warn("User {} has no FCM token, cannot send notification", user.getId());
                return CompletableFuture.completedFuture(false);
            }

            NotificationDTO notification = NotificationDTO.builder()
                    .title("Demande de connexion refusée")
                    .message(String.format("Maître %s a refusé votre demande de connexion",
                            lawyer.getName()))
                    .fcmToken(user.getFcmToken())
                    .type("CONNECTION_REJECTED")
                    .connectionId(connection.getId())
                    .userId(connection.getUserId())
                    .lawyerId(connection.getLawyerId())
                    .build();

            return firebaseService.sendNotification(notification)
                    .thenApply(response -> {
                        log.info("Successfully notified user {} about connection rejection", user.getId());
                        return true;
                    })
                    .exceptionally(throwable -> {
                        log.error("Failed to notify user {} about connection rejection: {}",
                                user.getId(), throwable.getMessage());
                        return false;
                    });

        } catch (Exception e) {
            log.error("Failed to send connection rejected notification", e);
            return CompletableFuture.completedFuture(false);
        }
    }

    private String getAuthorizationHeader() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("No valid authorization token found in request");
        }
        return authorizationHeader;
    }
}