package com.chatbot.commonlibrary.mapper;

import com.chatbot.commonlibrary.dtos.UserActivityDTO;
import com.chatbot.useractivityservice.model.UserActivity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {
    UserActivityDTO toDto(UserActivity activity);
    UserActivity toEntity(UserActivityDTO dto);
}