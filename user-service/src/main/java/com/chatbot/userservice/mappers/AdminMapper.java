package com.chatbot.userservice.mappers;

import com.chatbot.userservice.dtos.AdminDTO;
import com.chatbot.userservice.entities.Admin;

public interface AdminMapper {
    public AdminDTO convertToDTO(Admin admin);
    public Admin convertToEntity(AdminDTO dto);
    public void updateEntityFromDTO(AdminDTO dto, Admin admin);
}
