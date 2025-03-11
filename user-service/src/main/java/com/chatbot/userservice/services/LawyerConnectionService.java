package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.LawyerConnections;

import java.util.List;
import java.util.Optional;

public interface LawyerConnectionService {
    LawyerConnections createConnection(LawyerConnections connection);
    Optional<LawyerConnections> getConnectionById(Long id);
    List<LawyerConnections> getConnectionsByUserId(Long userId);
    List<LawyerConnections> getConnectionsByLawyerId(Long lawyerId);
    List<LawyerConnections> getConnectionsByStatus(String status);
    LawyerConnections updateConnection(LawyerConnections connection);
    void deleteConnection(Long id);
    void acceptConnection(Long connectionId);
    void rejectConnection(Long connectionId);
}
