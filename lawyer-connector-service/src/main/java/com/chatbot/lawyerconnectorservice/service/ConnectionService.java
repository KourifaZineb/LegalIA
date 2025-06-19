package com.chatbot.lawyerconnectorservice.service;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.lawyerconnectorservice.model.Connection;

import java.util.List;

public interface ConnectionService {
    LawyerConnectionDTO createConnection(LawyerConnectionDTO connectionDto);
    LawyerConnectionDTO getConnectionById(Long id);
    List<LawyerConnectionDTO> getConnectionsByUserId(Long userId);
    List<LawyerConnectionDTO> getConnectionsByLawyerId(Long lawyerId);
    List<LawyerConnectionDTO> getConnectionsByStatus(ConnectionStatus status);
    LawyerConnectionDTO updateConnection(Long id, LawyerConnectionDTO updatedDto);
    void deleteConnection(Long id);
    void cancel(Long id);
}
