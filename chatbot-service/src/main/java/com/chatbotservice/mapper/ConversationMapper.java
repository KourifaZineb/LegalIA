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

    /**
     * mappe entity → DTO.
     * userId est copié automatiquement (mêmes noms).
     * la liste messages est gérée par MessageMapper.
     */
    @Mapping(source = "messages", target = "messages")
    ConversationsDTO toDto(Conversations entity);

    /**
     * mappe DTO → entity.
     * on ignore la collection messages pour éviter les cycles.
     */
    @InheritInverseConfiguration
    @Mapping(target = "messages", ignore = true)
    Conversations toEntity(ConversationsDTO dto);
}