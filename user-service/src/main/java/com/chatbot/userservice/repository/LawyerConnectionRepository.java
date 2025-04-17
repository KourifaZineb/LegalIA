package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.LawyerConnections;
import com.chatbot.userservice.entities.enums.ConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerConnectionRepository extends JpaRepository<LawyerConnections, Long> {

    List<LawyerConnections> findByUser_UserId(Long userId);
    List<LawyerConnections> findByLawyer_LawyerId(Long lawyerId);
    List<LawyerConnections> findByStatus(ConnectionStatus status);

}

