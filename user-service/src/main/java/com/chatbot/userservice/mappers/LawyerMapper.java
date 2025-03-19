package com.chatbot.userservice.mappers;

import com.chatbot.userservice.dtos.LawyerDTO;
import com.chatbot.userservice.dtos.UserDTO;
import com.chatbot.userservice.entities.Lawyer;
import com.chatbot.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LawyerMapper {
    @Mapping(target = "password", ignore = true)
    public LawyerDTO convertToDTO(Lawyer lawyer);
    public Lawyer convertToEntity(LawyerDTO dto);
    public void updateEntityFromDTO(LawyerDTO dto, @MappingTarget Lawyer lawyer);
}
