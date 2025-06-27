package com.chatbotservice.mapper;

import com.chatbot.commonlibrary.dtos.MessagesDTO;
import com.chatbotservice.model.Messages;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "conversation.conversationId", target = "conversationId")
    @Mapping(source = "entities", target = "entities", qualifiedByName = "stringToList")
    MessagesDTO toDto(Messages entity);

    @Mapping(target = "conversation", ignore = true)
    @Mapping(source = "entities", target = "entities", qualifiedByName = "listToString")
    Messages toEntity(MessagesDTO dto);

    /** Convertit la chaîne (csv, JSON, etc.) en liste */
    @Named("stringToList")
    default List<String> stringToList(String value) {
        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }
        // exemple simplifié : on considère une CSV sans échappement
        return Arrays.asList(value.split(","));
    }

    /** Convertit la liste en chaîne (csv) pour stocker en base */
    @Named("listToString")
    default String listToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return String.join(",", list);
    }
}