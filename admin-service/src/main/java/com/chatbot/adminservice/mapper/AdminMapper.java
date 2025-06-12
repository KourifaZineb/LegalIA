package com.chatbot.commonlibrary.mapper;


import com.chatbot.adminservice.model.Admin;
import com.chatbot.commonlibrary.dtos.AdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminDTO toDto(Admin admin);
    Admin toEntity(AdminDTO dto);
}
