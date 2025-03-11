package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.LawyerConnections;

import java.util.List;
import java.util.Optional;

public interface LawyerConnectionService {
    LawyerConnections createConnection(LawyerConnections connection);
    Optional<LawyerConnections> getConnectionById(Integer id);
    List<LawyerConnections> getConnectionsByUserId(Integer userId);
    List<LawyerConnections> getConnectionsByLawyerId(Integer lawyerId);
    List<LawyerConnections> getConnectionsByStatus(String status);
    LawyerConnections updateConnection(LawyerConnections connection);
    void deleteConnection(Integer id);
    void acceptConnection(Integer connectionId);
    void rejectConnection(Integer connectionId);
}
