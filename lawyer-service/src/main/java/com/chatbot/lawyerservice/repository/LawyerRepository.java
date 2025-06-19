package com.chatbot.lawyerservice.repository;

import com.chatbot.commonlibrary.enums.LawyerStatus;
import com.chatbot.lawyerservice.model.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

    Optional<Lawyer> findByEmail(String email);
    List<Lawyer> findByStatus(LawyerStatus status);
    //Lawyer findByLawyerId(Long id);

}
