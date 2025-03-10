package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

    Optional<Lawyer> findByEmail(String email);
    List<Lawyer> findBySpecialization(String specialization);
    List<Lawyer> findByStatus(String status);
    List<Lawyer> findByRatingGreaterThanEqual(Float minRating);

}
