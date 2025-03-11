package com.chatbot.userservice.servicesImpl;

import com.chatbot.userservice.dtos.AdminDTO;
import com.chatbot.userservice.entities.Admin;
import com.chatbot.userservice.enums.Role;
import com.chatbot.userservice.mappers.AdminMapper;
import com.chatbot.userservice.repository.AdminRepository;
import com.chatbot.userservice.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminServiceImpl implements AdminService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;
    private AdminMapper adminMapper;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminMapper = adminMapper;
    }

    @Override
    @Transactional
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        // Conversion du DTO en entité
        Admin admin = adminMapper.convertToEntity(adminDTO);

        // Encodage du mot de passe
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        // Définir un rôle par défaut si nécessaire
        if (admin.getRole() == null) {
            admin.setRole(Role.SUPER_ADMIN);
        }

        // Sauvegarder l'entité
        Admin savedAdmin = adminRepository.save(admin);

        // Conversion de l'entité sauvegardée en DTO
        return adminMapper.convertToDTO(savedAdmin);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdminDTO> getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(adminMapper::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdminDTO> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(adminMapper::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDTO> getAdminsByRole(String role) {
        return adminRepository.findByRole(role).stream()
                .map(adminMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminDTO updateAdmin(AdminDTO adminDTO) {
        // Vérifier que l'admin existe
        Optional<Admin> existingAdminOpt = adminRepository.findById(adminDTO.getAdminId());

        if (existingAdminOpt.isPresent()) {
            Admin existingAdmin = existingAdminOpt.get();

            // Mise à jour partielle de l'entité existante
            adminMapper.updateEntityFromDTO(adminDTO, existingAdmin);

            // Gestion spéciale du mot de passe
            if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
                existingAdmin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
            }

            // Sauvegarder les modifications
            Admin updatedAdmin = adminRepository.save(existingAdmin);

            return adminMapper.convertToDTO(updatedAdmin);
        }

        return null; // ou lancer une exception si l'admin n'existe pas
    }

    @Override
    @Transactional
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean authenticateAdmin(String email, String password) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            boolean matches = passwordEncoder.matches(password, admin.getPassword());

            if (matches) {
                // Mise à jour de la date de dernière connexion
                admin.setLastLogin(LocalDateTime.now());
                adminRepository.save(admin);
                return true;
            }
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDTO> searchAdminsByName(String name) {
        return adminRepository.findByName(name).stream()
                .map(adminMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminDTO changeAdminRole(Long id, Role newRole) {
        Optional<Admin> adminOpt = adminRepository.findById(id);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            admin.setRole(newRole);
            Admin updatedAdmin = adminRepository.save(admin);
            return adminMapper.convertToDTO(updatedAdmin);
        }

        return null; // ou lancer une exception
    }
}
