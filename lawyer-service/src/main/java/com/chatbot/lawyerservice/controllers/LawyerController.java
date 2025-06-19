package com.chatbot.lawyerservice.controllers;

import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.commonlibrary.enums.LawyerStatus;
import com.chatbot.lawyerservice.services.LawyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lawyers")
@RequiredArgsConstructor
public class LawyerController {
    private final LawyerService service;

    @PostMapping
    public LawyerDTO create(@RequestBody LawyerDTO dto) {
        return service.createLawyer(dto);
    }

    @GetMapping("/{id}")
    public LawyerDTO getById(@PathVariable Long id) {
        return service.getLawyerById(id);
    }

    @GetMapping
    public List<LawyerDTO> getAll() {
        return service.getAllLawyers();
    }

    @GetMapping("/status/{status}")
    public List<LawyerDTO> getByStatus(@PathVariable LawyerStatus status) {
        return service.getLawyersByStatus(status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LawyerDTO> updateLawyer(@PathVariable Long id, @RequestBody LawyerDTO dto) {
        return ResponseEntity.ok(service.updateLawyer(id, dto));
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteLawyer(id);
    }
}