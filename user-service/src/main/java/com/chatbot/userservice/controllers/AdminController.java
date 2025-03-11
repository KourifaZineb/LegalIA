package com.chatbot.userservice.controllers;

import com.chatbot.userservice.dtos.AdminDTO;
import com.chatbot.userservice.enums.Role;
import com.chatbot.userservice.services.AdminService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@Validated @RequestBody AdminDTO adminDTO) {
        AdminDTO createdAdmin = adminService.createAdmin(adminDTO);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(admin -> new ResponseEntity<>(admin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<AdminDTO> getAdminByEmail(@PathVariable String email) {
        return adminService.getAdminByEmail(email)
                .map(admin -> new ResponseEntity<>(admin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }
    @GetMapping("/role/{role}")
    public ResponseEntity<List<AdminDTO>> getAdminsByRole(@PathVariable String role) {
        List<AdminDTO> admins = adminService.getAdminsByRole(role);
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<AdminDTO>> searchAdminsByName(@RequestParam String name) {
        List<AdminDTO> admins = adminService.searchAdminsByName(name);
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @Validated @RequestBody AdminDTO adminDTO) {
        adminDTO.setAdminId(id); // Assurer que l'ID est correctement défini
        AdminDTO updatedAdmin = adminService.updateAdmin(adminDTO);

        if (updatedAdmin != null) {
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}/role")
    public ResponseEntity<AdminDTO> changeAdminRole(@PathVariable Long id, @RequestParam Role role) {
        AdminDTO updatedAdmin = adminService.changeAdminRole(id, role);

        if (updatedAdmin != null) {
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/login")
    public ResponseEntity<String> authenticateAdmin(@RequestParam @Email(message = "Invalid email format") String email,
            @RequestParam @NotBlank(message = "Password is required") String password) {

        boolean authenticated = adminService.authenticateAdmin(email, password);

        if (authenticated) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
