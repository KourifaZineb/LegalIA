package com.chatbot.documentservice.mapper;

import com.chatbot.commonlibrary.dtos.DocumentDto;
import com.chatbot.documentservice.model.ConversationDocument;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "conversation.conversationId", target = "conversationId")
    DocumentDto toDto(ConversationDocument entity);

    @InheritInverseConfiguration
    @Mapping(target = "conversation", ignore = true)      // on lie la conversation manuellement
    @Mapping(target = "fileName", source = "dto.fileName")// si vous l’utilisez à l’inverse
    @Mapping(target = "fileType", source = "dto.fileType")
    @Mapping(target = "filePath", source = "dto.filePath")
    @Mapping(target = "uploadDate", source = "dto.uploadDate")
    @Mapping(target = "extractedText", source = "dto.extractedText")
    ConversationDocument toEntity(DocumentDto dto);
}
