package com.chatbot.userservice.services;

import com.chatbot.userservice.dtos.AdminDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    AdminDTO createAdmin(AdminDTO adminDTO);
    Optional<AdminDTO> getAdminByEmail(String email);
    Optional<AdminDTO> getAdminById(Long id);
    List<AdminDTO> getAllAdmins();

    List<AdminDTO> searchAdminsByName(String name);
    void deleteAdmin(Long id);
    AdminDTO updateAdmin(AdminDTO admin);
    boolean authenticateAdmin(String email, String password);

}
