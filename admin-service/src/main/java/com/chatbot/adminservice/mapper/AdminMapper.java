package com.chatbot.adminservice.mapper;


import com.chatbot.adminservice.model.Admin;
import com.chatbot.commonlibrary.dtos.AdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(source = "adminId", target = "id")
    AdminDTO toDto(Admin admin);

    @Mapping(source = "id", target = "adminId")
    @Mapping(target = "password", ignore = true)
    Admin toEntity(AdminDTO dto);
}
