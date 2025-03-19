package com.chatbot.userservice.mappers;

import com.chatbot.userservice.dtos.AdminDTO;
import com.chatbot.userservice.dtos.UserDTO;
import com.chatbot.userservice.entities.Admin;
import com.chatbot.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    public UserDTO convertToDTO(User user);
    public User convertToEntity(UserDTO dto);
    public void updateEntityFromDTO(UserDTO dto, @MappingTarget User user);
}
