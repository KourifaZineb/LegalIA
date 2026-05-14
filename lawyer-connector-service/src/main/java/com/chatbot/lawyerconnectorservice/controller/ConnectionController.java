package com.chatbot.lawyerconnectorservice.controller;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.lawyerconnectorservice.model.Connection;
import com.chatbot.lawyerconnectorservice.service.ConnectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping
    public ResponseEntity<LawyerConnectionDTO> createConnection(@RequestBody LawyerConnectionDTO dto) {
        LawyerConnectionDTO created = connectionService.createConnection(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LawyerConnectionDTO> getConnectionById(@PathVariable Long id) {
        LawyerConnectionDTO connection = connectionService.getConnectionById(id);
        return ResponseEntity.ok(connection);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LawyerConnectionDTO>> getConnectionsByUser(@PathVariable String userId) {
        List<LawyerConnectionDTO> connections = connectionService.getConnectionsByUserId(userId);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/lawyer/{lawyerId}")
    public ResponseEntity<List<LawyerConnectionDTO>> getConnectionsByLawyer(@PathVariable Long lawyerId) {
        List<LawyerConnectionDTO> connections = connectionService.getConnectionsByLawyerId(lawyerId);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/lawyer/{lawyerId}/pending")
    public ResponseEntity<List<LawyerConnectionDTO>> getPendingConnectionsForLawyer(@PathVariable Long lawyerId) {
        List<LawyerConnectionDTO> connections = connectionService.getPendingConnectionsForLawyer(lawyerId);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LawyerConnectionDTO>> getConnectionsByStatus(@PathVariable ConnectionStatus status) {
        List<LawyerConnectionDTO> connections = connectionService.getConnectionsByStatus(status);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/period")
    public ResponseEntity<List<LawyerConnectionDTO>> getConnectionsInPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate) {
        List<LawyerConnectionDTO> connections = connectionService.getConnectionsInPeriod(startDate, endDate);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/lawyer/{lawyerId}/count/{status}")
    public ResponseEntity<Long> getConnectionCountByStatus(
            @PathVariable Long lawyerId,
            @PathVariable ConnectionStatus status) {
        Long count = connectionService.getConnectionCountByStatus(lawyerId, status);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LawyerConnectionDTO> updateConnection(
            @PathVariable Long id,
            @RequestBody LawyerConnectionDTO dto) {
        LawyerConnectionDTO updated = connectionService.updateConnection(id, dto);
        return ResponseEntity.ok(updated);
    }
    @PutMapping("/{id}/accept")
    public ResponseEntity<LawyerConnectionDTO> acceptConnection(@PathVariable Long id) {
        LawyerConnectionDTO accepted = connectionService.acceptConnection(id);
        return ResponseEntity.ok(accepted);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LawyerConnectionDTO> rejectConnection(@PathVariable Long id) {
        LawyerConnectionDTO rejected = connectionService.rejectConnection(id);
        return ResponseEntity.ok(rejected);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelConnection(@PathVariable Long id) {
        connectionService.cancelConnection(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Long id) {
        connectionService.deleteConnection(id);
        return ResponseEntity.noContent().build();
    }
}
