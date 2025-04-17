package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.Lawyer;
import com.chatbot.userservice.entities.enums.Speciality;
import com.chatbot.userservice.entities.enums.lawyerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

    Optional<Lawyer> findByEmail(String email);
    List<Lawyer> findBySpecialization(Speciality specialization);
    List<Lawyer> findByRatingGreaterThanEqual(Float minRating);
    List<Lawyer> findByStatus(lawyerStatus status);

}
