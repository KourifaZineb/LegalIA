package com.chatbot.notificationservice.controllers;

import com.chatbot.commonlibrary.dtos.NotificationDTO;
import com.chatbot.notificationservice.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public NotificationDTO send(@RequestBody NotificationDTO dto) {
        return service.send(dto);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationDTO> getByUser(@PathVariable Long userId) {
        return service.getUserNotifications(userId);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
        return ResponseEntity.noContent().build();
    }


}