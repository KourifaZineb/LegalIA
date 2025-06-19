package com.chatbot.adminservice.service;

import com.chatbot.adminservice.model.Admin;
import com.chatbot.commonlibrary.dtos.AdminDTO;

import java.util.List;

public interface AdminService {
    AdminDTO createAdmin(AdminDTO admin);
    AdminDTO getAdminById(Long id);
    List<AdminDTO> getAllAdmins();
    AdminDTO updateAdmin(Long id, AdminDTO updatedAdmin);
    void deleteAdmin(Long id);
}
