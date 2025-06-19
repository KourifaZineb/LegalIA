package com.chatbot.adminservice.controller;


import com.chatbot.adminservice.service.AdminService;
import com.chatbot.commonlibrary.dtos.AdminDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public AdminDTO createAdmin(@RequestBody AdminDTO dto) {
        return adminService.createAdmin(dto);
    }
    @GetMapping("/{id}")
    public AdminDTO getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    @GetMapping
    public List<AdminDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PutMapping("/{id}")
    public AdminDTO updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDto) {
        return adminService.updateAdmin(id, adminDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }
}
