package com.chatbot.userservice.mappers;

import com.chatbot.userservice.dtos.UserActivityDTO;
import com.chatbot.userservice.entities.UserActivity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {
    UserActivityDTO convertToDto(UserActivity activity);
    UserActivity convertToEntity(UserActivityDTO activityDTO);
}
