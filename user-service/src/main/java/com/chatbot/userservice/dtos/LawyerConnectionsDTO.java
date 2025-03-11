package com.chatbot.userservice.dtos;

import com.chatbot.userservice.enums.ConnectionStatus;

import java.time.LocalDateTime;


public class LawyerConnectionsDTO {

    private Long connectionId;
    private LocalDateTime requestDate;
    private ConnectionStatus status;
    private String caseDescription;
    private UserDTO user;
    private LawyerDTO lawyer;

    public LawyerConnectionsDTO(Long connectionId, LocalDateTime requestDate, ConnectionStatus status, String caseDescription, UserDTO user, LawyerDTO lawyer) {
        this.connectionId = connectionId;
        this.requestDate = requestDate;
        this.status = status;
        this.caseDescription = caseDescription;
        this.user = user;
        this.lawyer = lawyer;
    }

    public LawyerConnectionsDTO() {
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
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
        return "LawyerConnections{" +
                "connectionId=" + connectionId +
                ", requestDate=" + requestDate +
                ", status=" + status +
                ", caseDescription='" + caseDescription + '\'' +
                ", user=" + user +
                ", lawyer=" + lawyer +
                '}';
    }
}
