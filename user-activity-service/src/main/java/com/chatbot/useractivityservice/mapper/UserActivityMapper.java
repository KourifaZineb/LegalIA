package com.chatbot.useractivityservice.mapper;

import com.chatbot.commonlibrary.dtos.UserActivityDTO;
import com.chatbot.useractivityservice.model.UserActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {

    @Mapping(target = "userDTO", ignore = true) // ✅ on ignore ce champ ici
    UserActivityDTO toDto(UserActivity activity);
    @Mapping(source = "userId", target = "id")
    UserActivity toEntity(UserActivityDTO dto);
}
