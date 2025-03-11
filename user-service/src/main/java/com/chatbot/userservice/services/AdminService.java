package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin createAdmin(Admin admin);
    Optional<Admin> getAdminById(Integer id);
    Optional<Admin> getAdminByEmail(String email);
    List<Admin> getAllAdmins();
    List<Admin> getAdminsByRole(String role);
    Admin updateAdmin(Admin admin);
    void deleteAdmin(Integer id);
    boolean authenticateAdmin(String email, String password);
}
