package com.chatbotservice.mapper;

import com.chatbot.commonlibrary.dtos.ConversationsDTO;
import com.chatbotservice.model.Conversations;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = MessageMapper.class
)
public interface ConversationMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "messages",        target = "messages")
    ConversationsDTO toDto(Conversations entity);

    @InheritInverseConfiguration
    @Mapping(target = "user",     ignore = true)  // on injectera l’utilisateur via le service
    @Mapping(target = "messages", ignore = true)  // on gère l’ajout des messages séparément
    Conversations toEntity(ConversationsDTO dto);
}