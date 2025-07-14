package com.chatbot.userservice.mapper;

import com.chatbot.commonlibrary.dtos.UserDTO;
import com.chatbot.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //@Mapping(source = "password", target = "password")

    UserDTO toDto(User user);

    //@Mapping(source = "password", target = "password")
    //@Mapping(target = "id", ignore = true) // optionnel
    User toEntity(UserDTO dto);
}
