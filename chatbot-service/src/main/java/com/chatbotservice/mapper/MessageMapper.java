package com.chatbotservice.mapper;

import com.chatbot.commonlibrary.dtos.MessagesDTO;
import com.chatbotservice.model.Messages;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "conversation.conversationId", target = "conversationId")
    MessagesDTO toDto(Messages entity);

    @InheritInverseConfiguration
    @Mapping(target = "conversation", ignore = true)  // on liera la conversation ailleurs
    Messages toEntity(MessagesDTO dto);
}