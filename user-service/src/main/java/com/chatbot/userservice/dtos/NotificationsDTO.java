package com.chatbot.userservice.dtos;

import com.chatbot.userservice.enums.NotificationType;

import java.time.LocalDateTime;


public class NotificationsDTO {

    private Long notificationId;
    private String content;
    private LocalDateTime timestamp;
    private Boolean isRead;
    private NotificationType notificationType;
    private UserDTO user;
    private LawyerDTO lawyer;

    public NotificationsDTO(Long notificationId, String content, LocalDateTime timestamp, Boolean isRead, NotificationType notificationType, UserDTO user, LawyerDTO lawyer) {
        this.notificationId = notificationId;
        this.content = content;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.notificationType = notificationType;
        this.user = user;
        this.lawyer = lawyer;
    }

    public NotificationsDTO() {
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LawyerDTO getLawyer() {
        return lawyer;
    }

    public void setLawyer(LawyerDTO lawyer) {
        this.lawyer = lawyer;
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "notificationId=" + notificationId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                ", notificationType=" + notificationType +
                ", user=" + user +
                ", lawyer=" + lawyer +
                '}';
    }
}
