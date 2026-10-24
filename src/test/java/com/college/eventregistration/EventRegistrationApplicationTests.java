package com.college.eventregistration;

import com.college.eventregistration.dto.EventRequestDto;
import com.college.eventregistration.dto.RegistrationRequestDto;
import com.college.eventregistration.model.Event;
import com.college.eventregistration.repository.EventRepository;
import com.college.eventregistration.repository.RegistrationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"
})
class EventRegistrationApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @BeforeEach
    void setUp() {
        registrationRepository.deleteAll();
        eventRepository.deleteAll();
    }

    @Test
    void testCreateEventSuccess() throws Exception {
        EventRequestDto eventDto = new EventRequestDto(
                "Web3 & Blockchain Seminar",
                LocalDateTime.now().plusDays(5),
                "Auditorium 2",
                "Introduction to decentralized apps and smart contracts"
        );

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Web3 & Blockchain Seminar"))
                .andExpect(jsonPath("$.venue").value("Auditorium 2"))
                .andExpect(jsonPath("$.registrationCount").value(0));
    }

    @Test
    void testCreateEventValidationError() throws Exception {
        // Missing name and venue
        EventRequestDto invalidDto = new EventRequestDto(
                "",
                LocalDateTime.now().plusDays(5),
                "",
                ""
        );

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.venue").exists())
                .andExpect(jsonPath("$.validationErrors.description").exists());
    }

    @Test
    void testRegisterParticipantSuccess() throws Exception {
        Event event = new Event(
                "Cyber Security Bootcamp",
                LocalDateTime.now().plusDays(10),
                "Lab 402",
                "Hands-on ethical hacking and threat detection"
        );
        Event savedEvent = eventRepository.save(event);

        RegistrationRequestDto regDto = new RegistrationRequestDto(
                savedEvent.getId(),
                "Rahul Sharma",
                "rahul@college.edu",
                "+91 9998887776",
                "Computer Science"
        );

        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.participantName").value("Rahul Sharma"))
                .andExpect(jsonPath("$.participantEmail").value("rahul@college.edu"))
                .andExpect(jsonPath("$.eventId").value(savedEvent.getId()))
                .andExpect(jsonPath("$.eventName").value("Cyber Security Bootcamp"));
    }

    @Test
    void testRegisterParticipantInvalidEmail() throws Exception {
        Event event = new Event(
                "Robotics Workshop",
                LocalDateTime.now().plusDays(10),
                "Lab 101",
                "Build your own line follower robot"
        );
        Event savedEvent = eventRepository.save(event);

        RegistrationRequestDto invalidEmailDto = new RegistrationRequestDto(
                savedEvent.getId(),
                "Rahul Sharma",
                "not-a-valid-email",
                "+91 9998887776",
                "Computer Science"
        );

        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.participantEmail").exists());
    }

    @Test
    void testDuplicateRegistrationConflict() throws Exception {
        Event event = new Event(
                "Data Science Meetup",
                LocalDateTime.now().plusDays(8),
                "Seminar Hall B",
                "Exploring deep learning architectures"
        );
        Event savedEvent = eventRepository.save(event);

        RegistrationRequestDto regDto = new RegistrationRequestDto(
                savedEvent.getId(),
                "Sneha Kapoor",
                "sneha@college.edu",
                "+91 9876543210",
                "Data Science"
        );

        // First registration succeeds
        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regDto)))
                .andExpect(status().isCreated());

        // Second registration with same email for the same event should return 409 Conflict
        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Duplicate Registration"));
    }

    @Test
    void testGetEventsShowsRegistrationCount() throws Exception {
        Event event = new Event(
                "Design Thinking Workshop",
                LocalDateTime.now().plusDays(12),
                "Innovation Studio",
                "Human-centered UI/UX design processes"
        );
        Event savedEvent = eventRepository.save(event);

        // Register 2 students
        RegistrationRequestDto r1 = new RegistrationRequestDto(savedEvent.getId(), "User 1", "u1@college.edu", null, null);
        RegistrationRequestDto r2 = new RegistrationRequestDto(savedEvent.getId(), "User 2", "u2@college.edu", null, null);

        mockMvc.perform(post("/api/registrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(r1))).andExpect(status().isCreated());

        mockMvc.perform(post("/api/registrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(r2))).andExpect(status().isCreated());

        // Get all events and check count
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Design Thinking Workshop"))
                .andExpect(jsonPath("$[0].registrationCount").value(2));
    }
}
