package com.chatbot.notificationservice.mapper;

import com.chatbot.commonlibrary.dtos.NotificationDTO;
import com.chatbot.notificationservice.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface NotificationMapper {


    NotificationDTO toDto(Notification notification);
    Notification toEntity(NotificationDTO dto);
}

