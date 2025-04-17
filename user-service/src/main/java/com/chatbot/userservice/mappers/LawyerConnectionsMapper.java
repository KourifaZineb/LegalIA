package com.chatbot.userservice.mappers;

import com.chatbot.userservice.dtos.LawyerConnectionsDTO;
import com.chatbot.userservice.entities.LawyerConnections;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LawyerConnectionsMapper {
    LawyerConnectionsDTO convertToDTO(LawyerConnections entity);
    LawyerConnections convertToEntity(LawyerConnectionsDTO dto);
}
