package com.chatbot.lawyerconnectorservice.service;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.lawyerconnectorservice.model.Connection;
import java.time.Instant;
import java.util.List;

public interface ConnectionService {
    LawyerConnectionDTO createConnection(LawyerConnectionDTO dto);

    LawyerConnectionDTO getConnectionById(Long id);

    // Changed from Long userId to String userId
    List<LawyerConnectionDTO> getConnectionsByUserId(String userId);

    List<LawyerConnectionDTO> getConnectionsByLawyerId(Long lawyerId);

    List<LawyerConnectionDTO> getConnectionsByStatus(ConnectionStatus status);

    LawyerConnectionDTO updateConnection(Long id, LawyerConnectionDTO dto);

    void deleteConnection(Long id);

    LawyerConnectionDTO acceptConnection(Long connectionId);

    LawyerConnectionDTO rejectConnection(Long connectionId);

    void cancelConnection(Long id);

    List<LawyerConnectionDTO> getPendingConnectionsForLawyer(Long lawyerId);

    List<LawyerConnectionDTO> getConnectionsInPeriod(Instant startDate, Instant endDate);

    Long getConnectionCountByStatus(Long lawyerId, ConnectionStatus status);

    // Add method for counting user connections by status
    Long getConnectionCountByUserStatus(String userId, ConnectionStatus status);
}