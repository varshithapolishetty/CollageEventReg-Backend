package com.college.eventregistration.service;

import com.college.eventregistration.dto.RegistrationRequestDto;
import com.college.eventregistration.dto.RegistrationResponseDto;
import com.college.eventregistration.exception.DuplicateRegistrationException;
import com.college.eventregistration.exception.ResourceNotFoundException;
import com.college.eventregistration.model.Event;
import com.college.eventregistration.model.Registration;
import com.college.eventregistration.repository.EventRepository;
import com.college.eventregistration.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationService(RegistrationRepository registrationRepository, EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public List<RegistrationResponseDto> getAllRegistrations(Long eventId) {
        List<Registration> list;
        if (eventId != null) {
            list = registrationRepository.findByEventIdOrderByRegistrationDateDesc(eventId);
        } else {
            list = registrationRepository.findAllByOrderByRegistrationDateDesc();
        }
        return list.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RegistrationResponseDto getRegistrationById(Long id) {
        Registration reg = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with ID: " + id));
        return mapToResponseDto(reg);
    }

    public RegistrationResponseDto registerParticipant(RegistrationRequestDto dto) {
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + dto.getEventId()));

        String email = dto.getParticipantEmail().trim().toLowerCase();

        // Check if student already registered for this specific event
        if (registrationRepository.existsByEventIdAndParticipantEmailIgnoreCase(event.getId(), email)) {
            throw new DuplicateRegistrationException(
                    "The email '" + dto.getParticipantEmail() + "' is already registered for '" + event.getName() + "'."
            );
        }

        Registration registration = new Registration();
        registration.setParticipantName(dto.getParticipantName().trim());
        registration.setParticipantEmail(email);
        registration.setParticipantPhone(dto.getParticipantPhone() != null ? dto.getParticipantPhone().trim() : null);
        registration.setParticipantDepartment(dto.getParticipantDepartment() != null ? dto.getParticipantDepartment().trim() : null);
        registration.setEvent(event);

        Registration saved = registrationRepository.save(registration);
        return mapToResponseDto(saved);
    }

    public RegistrationResponseDto updateRegistration(Long id, RegistrationRequestDto dto) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with ID: " + id));

        String email = dto.getParticipantEmail().trim().toLowerCase();

        // Check duplicate email if changed
        if (!registration.getParticipantEmail().equalsIgnoreCase(email)) {
            if (registrationRepository.existsByEventIdAndParticipantEmailIgnoreCase(registration.getEvent().getId(), email)) {
                throw new DuplicateRegistrationException("The email '" + dto.getParticipantEmail() + "' is already registered for this event.");
            }
        }

        registration.setParticipantName(dto.getParticipantName().trim());
        registration.setParticipantEmail(email);
        registration.setParticipantPhone(dto.getParticipantPhone() != null ? dto.getParticipantPhone().trim() : null);
        registration.setParticipantDepartment(dto.getParticipantDepartment() != null ? dto.getParticipantDepartment().trim() : null);

        Registration updated = registrationRepository.save(registration);
        return mapToResponseDto(updated);
    }

    public void deleteRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registration not found with ID: " + id);
        }
        registrationRepository.deleteById(id);
    }

    private RegistrationResponseDto mapToResponseDto(Registration reg) {
        return new RegistrationResponseDto(
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
        );
    }
}
