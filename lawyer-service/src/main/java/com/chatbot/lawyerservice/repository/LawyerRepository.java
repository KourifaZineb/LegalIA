package com.ged.lawyerservice.repository;

import com.ged.lawyerservice.entities.Lawyer;
import com.ged.lawyerservice.entities.enums.LawyerStatus;
import com.ged.lawyerservice.entities.enums.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

    Optional<Lawyer> findByEmail(String email);
    List<Lawyer> findBySpecialization(Speciality specialization);
    List<Lawyer> findByRatingGreaterThanEqual(Float minRating);
    List<Lawyer> findByStatus(LawyerStatus status);

}
