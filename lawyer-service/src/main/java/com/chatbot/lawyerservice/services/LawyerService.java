package com.chatbot.lawyerservice.services;

import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.commonlibrary.enums.LawyerStatus;

import java.util.List;
import java.util.Optional;

public interface LawyerService {
    LawyerDTO createLawyer(LawyerDTO dto);
    LawyerDTO getLawyerById(Long id);
    List<LawyerDTO> getAllLawyers();
    List<LawyerDTO> getLawyersByStatus(LawyerStatus status);
    LawyerDTO updateLawyer(Long id, LawyerDTO dto);
    void deleteLawyer(Long id);

}
