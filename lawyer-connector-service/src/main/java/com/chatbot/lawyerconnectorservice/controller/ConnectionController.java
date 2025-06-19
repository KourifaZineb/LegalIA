package com.chatbot.lawyerconnectorservice.controller;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.lawyerconnectorservice.model.Connection;
import com.chatbot.lawyerconnectorservice.service.ConnectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connections")
public class ConnectionController {
    private ConnectionService service;
    public ConnectionController(ConnectionService service) {
        this.service = service;
    }

    @PostMapping
    public LawyerConnectionDTO create(@RequestBody LawyerConnectionDTO dto) {
        return service.createConnection(dto);
    }

    @GetMapping("/{id}")
    public LawyerConnectionDTO getById(@PathVariable Long id) {
        return service.getConnectionById(id);
    }

    @GetMapping("/user/{userId}")
    public List<LawyerConnectionDTO> getByUser(@PathVariable Long userId) {
        return service.getConnectionsByUserId(userId);
    }

    @GetMapping("/lawyer/{lawyerId}")
    public List<LawyerConnectionDTO> getByLawyer(@PathVariable Long lawyerId) {
        return service.getConnectionsByLawyerId(lawyerId);
    }

    @GetMapping("/status/{status}")
    public List<LawyerConnectionDTO> getByStatus(@PathVariable ConnectionStatus status) {
        return service.getConnectionsByStatus(status);
    }

    @PutMapping("/{id}")
    public LawyerConnectionDTO update(@PathVariable Long id, @RequestBody LawyerConnectionDTO dto) {
        return service.updateConnection(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteConnection(id);
    }

    @PutMapping("/{id}/cancel")
    public void cancelConnection(@PathVariable Long id) {
        service.cancel(id);
    }
}
