package com.chatbot.lawyerservice.mapper;

import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.lawyerservice.model.Lawyer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LawyerMapper {

    LawyerDTO toDto(Lawyer lawyer);
    @Mapping(target = "password", ignore = true)
    Lawyer toEntity(LawyerDTO dto);
}
