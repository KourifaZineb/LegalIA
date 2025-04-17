package com.chatbot.userservice.controllers;

import com.chatbot.userservice.dtos.LawyerConnectionsDTO;
import com.chatbot.userservice.entities.LawyerConnections;
import com.chatbot.userservice.services.LawyerConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lawyer-connections")
@RequiredArgsConstructor
public class LawyerConnectionController {
    private final LawyerConnectionService lawyerConnectionService;

    @PostMapping("/create")
    public ResponseEntity<LawyerConnections> createConnection(@RequestBody LawyerConnections connection) {
        LawyerConnections created = lawyerConnectionService.createConnection(connection);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LawyerConnections> getConnectionById(@PathVariable Long id) {
        return lawyerConnectionService.getConnectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LawyerConnections>> getConnectionsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(lawyerConnectionService.getConnectionsByUserId(userId));
    }

    @GetMapping("/lawyer/{lawyerId}")
    public ResponseEntity<List<LawyerConnections>> getConnectionsByLawyerId(@PathVariable Long lawyerId) {
        return ResponseEntity.ok(lawyerConnectionService.getConnectionsByLawyerId(lawyerId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<LawyerConnections>> getConnectionsByStatus(@RequestParam String status) {
        return ResponseEntity.ok(lawyerConnectionService.getConnectionsByStatus(status));
    }

    @PutMapping
    public ResponseEntity<LawyerConnections> updateConnection(@RequestBody LawyerConnections connection) {
        return ResponseEntity.ok(lawyerConnectionService.updateConnection(connection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Long id) {
        lawyerConnectionService.deleteConnection(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<LawyerConnectionsDTO> acceptConnection(@PathVariable Long id) {
        lawyerConnectionService.acceptConnection(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LawyerConnectionsDTO> rejectConnection(@PathVariable Long id) {
        lawyerConnectionService.rejectConnection(id);
        return ResponseEntity.ok().build();
    }
}
