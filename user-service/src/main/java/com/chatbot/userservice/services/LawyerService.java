package com.chatbot.userservice.services;

import com.chatbot.userservice.dtos.LawyerDTO;
import com.chatbot.userservice.entities.enums.Speciality;
import com.chatbot.userservice.entities.enums.lawyerStatus;

import java.util.List;
import java.util.Optional;

public interface LawyerService {
    LawyerDTO createLawyer(LawyerDTO lawyerDTO);
    Optional<LawyerDTO> getLawyerById(Long id);
    Optional<LawyerDTO> getLawyerByEmail(String email);
    List<LawyerDTO> getAllLawyers();
    List<LawyerDTO> getLawyersBySpecialization(Speciality specialization);
    List<LawyerDTO> getLawyersByStatus(lawyerStatus status);
    List<LawyerDTO> getLawyersByMinimumRating(Float minRating);
    LawyerDTO updateLawyer(Long id, LawyerDTO lawyerDTO);
    void deleteLawyer(Long id);
    boolean authenticateLawyer(String email, String password);
    void updateLawyerAvailability(Long lawyerId, String status);
}
