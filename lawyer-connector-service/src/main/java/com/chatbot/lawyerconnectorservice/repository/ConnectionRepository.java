package com.chatbot.lawyerconnectorservice.repository;

import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.lawyerconnectorservice.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByUserId(Long userId);
    List<Connection> findByLawyerId(Long lawyerId);
    List<Connection> findByStatus(ConnectionStatus status);
}