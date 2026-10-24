package com.college.eventregistration.service;

import com.college.eventregistration.dto.EventRequestDto;
import com.college.eventregistration.dto.EventResponseDto;
import com.college.eventregistration.dto.EventStatsDto;
import com.college.eventregistration.dto.RegistrationResponseDto;
import com.college.eventregistration.exception.ResourceNotFoundException;
import com.college.eventregistration.model.Event;
import com.college.eventregistration.repository.EventRepository;
import com.college.eventregistration.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    public EventService(EventRepository eventRepository, RegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    @Transactional(readOnly = true)
    public List<EventResponseDto> getAllEvents() {
        return eventRepository.findAllByOrderByDateAsc()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventResponseDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
        return mapToResponseDto(event);
    }

    public EventResponseDto createEvent(EventRequestDto dto) {
        Event event = new Event();
        event.setName(dto.getName().trim());
        event.setDate(dto.getDate());
        event.setVenue(dto.getVenue().trim());
        event.setDescription(dto.getDescription().trim());

        Event saved = eventRepository.save(event);
        return mapToResponseDto(saved);
    }

    public EventResponseDto updateEvent(Long id, EventRequestDto dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));

        event.setName(dto.getName().trim());
        event.setDate(dto.getDate());
        event.setVenue(dto.getVenue().trim());
        event.setDescription(dto.getDescription().trim());

        Event updated = eventRepository.save(event);
        return mapToResponseDto(updated);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RegistrationResponseDto> getRegistrationsForEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with ID: " + eventId);
        }
        return registrationRepository.findByEventIdOrderByRegistrationDateDesc(eventId)
                .stream()
                .map(reg -> new RegistrationResponseDto(
                        reg.getId(),
                        reg.getParticipantName(),
                        reg.getParticipantEmail(),
                        reg.getParticipantPhone(),
                        reg.getParticipantDepartment(),
                        reg.getRegistrationDate(),
                        reg.getEvent().getId(),
                        reg.getEvent().getName(),
                        reg.getEvent().getDate(),
                        reg.getEvent().getVenue()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventStatsDto getStats() {
        List<Event> events = eventRepository.findAll();
        long totalEvents = events.size();
        long totalRegistrations = registrationRepository.count();
        long upcomingEvents = events.stream()
                .filter(e -> e.getDate() != null && e.getDate().isAfter(LocalDateTime.now()))
                .count();

        String mostPopularEventName = "None";
        int mostPopularCount = 0;

        if (!events.isEmpty()) {
            Event mostPopular = events.stream()
                    .max(Comparator.comparingInt(Event::getRegistrationCount))
                    .orElse(null);
            if (mostPopular != null && mostPopular.getRegistrationCount() > 0) {
                mostPopularEventName = mostPopular.getName();
                mostPopularCount = mostPopular.getRegistrationCount();
            }
        }

        return new EventStatsDto(totalEvents, totalRegistrations, upcomingEvents, mostPopularEventName, mostPopularCount);
    }

    private EventResponseDto mapToResponseDto(Event event) {
        int regCount = event.getRegistrations() != null ? event.getRegistrations().size() : 0;
        return new EventResponseDto(
                event.getId(),
                event.getName(),
                event.getDate(),
                event.getVenue(),
                event.getDescription(),
                regCount,
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}
