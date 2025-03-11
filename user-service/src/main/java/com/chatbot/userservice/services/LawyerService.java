package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.Lawyer;

import java.util.List;
import java.util.Optional;

public interface LawyerService {
    Lawyer createLawyer(Lawyer lawyer);
    Optional<Lawyer> getLawyerById(Integer id);
    Optional<Lawyer> getLawyerByEmail(String email);
    List<Lawyer> getAllLawyers();
    List<Lawyer> getLawyersBySpecialization(String specialization);
    List<Lawyer> getLawyersByStatus(String status);
    List<Lawyer> getLawyersByMinimumRating(Float minRating);
    Lawyer updateLawyer(Lawyer lawyer);
    void deleteLawyer(Integer id);
    boolean authenticateLawyer(String email, String password);
    void updateLawyerAvailability(Integer lawyerId, String status);
}
