package com.chatbot.useractivityservice.controllers;

import com.chatbot.commonlibrary.dtos.UserActivityDTO;
import com.chatbot.useractivityservice.services.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userActivity")
@RequiredArgsConstructor
public class UserActivityController {
    private final UserActivityService service;

    @PostMapping
    public UserActivityDTO log(@RequestBody UserActivityDTO dto) {
        return service.logActivity(dto);
    }

    @GetMapping("/user/{userId}")
    public List<UserActivityDTO> getByUser(@PathVariable Long userId) {
        return service.getActivitiesByUserId(userId);
    }

    @GetMapping
    public List<UserActivityDTO> getAll() {
        return service.getAllActivities();
    }
}
