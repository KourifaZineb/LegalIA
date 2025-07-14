package com.chatbot.userservice.controllers;

import com.chatbot.commonlibrary.dtos.UserDTO;
import com.chatbot.userservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) {
        return service.createUser(dto);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable UUID id) {
        return service.getUserById(id);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable UUID id, @RequestBody UserDTO dto) {
        return service.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteUser(id);
    }
}
