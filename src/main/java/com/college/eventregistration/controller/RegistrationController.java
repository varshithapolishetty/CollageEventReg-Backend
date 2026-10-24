package com.college.eventregistration.controller;

import com.college.eventregistration.dto.RegistrationRequestDto;
import com.college.eventregistration.dto.RegistrationResponseDto;
import com.college.eventregistration.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*") // Allows React frontend development server
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Get all registrations (optionally filtered by eventId).
     */
    @GetMapping
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrations(
            @RequestParam(required = false) Long eventId) {
        return ResponseEntity.ok(registrationService.getAllRegistrations(eventId));
    }

    /**
     * Get registration by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationResponseDto> getRegistrationById(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.getRegistrationById(id));
    }

    /**
     * Register a participant using name and email address for a selected event.
     */
    @PostMapping
    public ResponseEntity<RegistrationResponseDto> registerParticipant(
            @Valid @RequestBody RegistrationRequestDto requestDto) {
        RegistrationResponseDto created = registrationService.registerParticipant(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Update participant registration details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationResponseDto> updateRegistration(
            @PathVariable Long id,
            @Valid @RequestBody RegistrationRequestDto requestDto) {
        return ResponseEntity.ok(registrationService.updateRegistration(id, requestDto));
    }

    /**
     * Cancel / Delete a registration.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
