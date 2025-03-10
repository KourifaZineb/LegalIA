package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.LawyerConnections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerConnectionRepository extends JpaRepository<LawyerConnections, Long> {

    List<LawyerConnections> findByUserId(Integer userId);
    List<LawyerConnections> findByLawyerId(Integer lawyerId);
    List<LawyerConnections> findByStatus(String status);

}
