package com.chatbot.commonlibrary.mapper;

import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.lawyerservice.model.Lawyer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LawyerMapper {
    LawyerDTO toDto(Lawyer lawyer);
    Lawyer toEntity(LawyerDTO dto);
}
