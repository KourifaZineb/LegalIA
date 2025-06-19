package com.chatbot.lawyerconnectorservice.mapper;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.lawyerconnectorservice.model.Connection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LawyerConnectionMapper {
    @Mapping(source = "id", target = "connectionId")
    @Mapping(target = "user", ignore = true)   // on les remplit ensuite via Feign
    @Mapping(target = "lawyer", ignore = true)
    LawyerConnectionDTO toDto(Connection connection);
    @Mapping(source = "connectionId", target = "id")
    Connection toEntity(LawyerConnectionDTO dto);
}