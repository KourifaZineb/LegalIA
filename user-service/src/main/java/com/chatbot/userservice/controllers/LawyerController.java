package com.chatbot.userservice.controllers;

import com.chatbot.userservice.dtos.LawyerDTO;
import com.chatbot.userservice.enums.Speciality;
import com.chatbot.userservice.enums.lawyerStatus;
import com.chatbot.userservice.response.DefaultResponse;
import com.chatbot.userservice.services.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/lawyers")
public class LawyerController {
    private final LawyerService lawyerService;
    @Autowired
    public LawyerController(LawyerService lawyerService) {
        this.lawyerService = lawyerService;
    }

    @PostMapping
    public ResponseEntity<LawyerDTO> createLawyer(@Validated @RequestBody LawyerDTO lawyerDTO) {
        LawyerDTO createLawyer = lawyerService.createLawyer(lawyerDTO);
        return new ResponseEntity<>(createLawyer, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LawyerDTO> getLawyerById(@PathVariable Long id) {
        Optional<LawyerDTO> lawyerOpt = lawyerService.getLawyerById(id);
        return lawyerOpt.map(lawyer -> new ResponseEntity<>(lawyer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<LawyerDTO> getLawyerByEmail(@PathVariable String email) {
        return lawyerService.getLawyerByEmail(email)
                .map(lawyerDTO -> new ResponseEntity<>(lawyerDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<LawyerDTO>> getAllLawyers() {
        List<LawyerDTO> lawyers = lawyerService.getAllLawyers();
        return new ResponseEntity<>(lawyers, HttpStatus.OK);
    }

    @GetMapping("/speciality/{specialization}")
    public ResponseEntity<List<LawyerDTO>> getLawyersBySpecialization(@PathVariable Speciality specialization) {
        List<LawyerDTO> lawyers = lawyerService.getLawyersBySpecialization(specialization);
        return new ResponseEntity<>(lawyers, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LawyerDTO>> getLawyersByStatus(@PathVariable lawyerStatus status) {
        List<LawyerDTO> lawyers = lawyerService.getLawyersByStatus(status);
        return new ResponseEntity<>(lawyers, HttpStatus.OK);
    }

    @GetMapping("/by-min-rating")
    public ResponseEntity<List<LawyerDTO>> getLawyersByMinimumRating(@RequestParam Float minRating) {
        List<LawyerDTO> result = lawyerService.getLawyersByMinimumRating(minRating);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LawyerDTO> updateLawyer(@PathVariable Long id, @RequestBody LawyerDTO lawyerDTO) {
        return ResponseEntity.ok(lawyerService.updateLawyer(id, lawyerDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> deleteLawyer(@PathVariable Long id) {
        lawyerService.deleteLawyer(id);
        return new ResponseEntity<>(DefaultResponse.builder().returnCode("000").returnMessage("Success").build(), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateLawyer(@RequestParam String email,
                                                      @RequestParam String password) {
        boolean isAuthenticated = lawyerService.authenticateLawyer(email, password);
        return ResponseEntity.ok(isAuthenticated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DefaultResponse> updateLawyerAvailability(@PathVariable Long id,
                                                                    @RequestBody Map<String, String> body) {
        String status = body.get("status");
        lawyerService.updateLawyerAvailability(id, status);
        return new ResponseEntity<>(DefaultResponse.builder().returnCode("000").returnMessage("Success").build(), HttpStatus.OK);
    }


}
