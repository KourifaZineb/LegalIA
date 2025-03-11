package com.chatbot.userservice.services;

import com.chatbot.userservice.dtos.AdminDTO;
import com.chatbot.userservice.entities.Admin;
import com.chatbot.userservice.enums.Role;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    AdminDTO createAdmin(AdminDTO adminDTO);
    Optional<AdminDTO> getAdminById(Long id);
    Optional<AdminDTO> getAdminByEmail(String email);
    List<AdminDTO> getAllAdmins();
    List<AdminDTO> getAdminsByRole(String role);
    AdminDTO updateAdmin(AdminDTO admin);
    void deleteAdmin(Long id);
    boolean authenticateAdmin(String email, String password);
    List<AdminDTO> searchAdminsByName(String name);
    AdminDTO changeAdminRole(Long id, Role newRole);
}
