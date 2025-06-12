package com.chatbot.commonlibrary.mapper;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.lawyerconnectorservice.model.Connection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LawyerConnectionMapper {
    LawyerConnectionDTO toDto(Connection connection);
    Connection toEntity(LawyerConnectionDTO dto);
}
