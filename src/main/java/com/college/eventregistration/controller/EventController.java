package com.college.eventregistration.controller;

import com.college.eventregistration.dto.EventRequestDto;
import com.college.eventregistration.dto.EventResponseDto;
import com.college.eventregistration.dto.EventStatsDto;
import com.college.eventregistration.dto.RegistrationResponseDto;
import com.college.eventregistration.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*") // Allows React frontend development server
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Display the list of available events (with registration count for each event).
     */
    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    /**
     * Get specific event details by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    /**
     * Add a new event with name, date, venue, and a short description.
     */
    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto requestDto) {
        EventResponseDto created = eventService.createEvent(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Update an existing event.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDto requestDto) {
        return ResponseEntity.ok(eventService.updateEvent(id, requestDto));
    }

    /**
     * Delete an event by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Display the participants registered for a specific event.
     */
    @GetMapping("/{id}/registrations")
    public ResponseEntity<List<RegistrationResponseDto>> getEventRegistrations(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getRegistrationsForEvent(id));
    }

    /**
     * Summary statistics for the organizer dashboard.
     */
    @GetMapping("/stats")
    public ResponseEntity<EventStatsDto> getStats() {
        return ResponseEntity.ok(eventService.getStats());
    }
}
