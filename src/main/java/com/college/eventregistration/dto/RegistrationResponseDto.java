package com.college.eventregistration.dto;

import java.time.LocalDateTime;

public class RegistrationResponseDto {
    private Long id;
    private String participantName;
    private String participantEmail;
    private String participantPhone;
    private String participantDepartment;
    private LocalDateTime registrationDate;

    // Detailed event fields making it crystal clear which event the participant registered for
    private Long eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private String eventVenue;

    public RegistrationResponseDto() {
    }

    public RegistrationResponseDto(Long id, String participantName, String participantEmail, String participantPhone,
                                   String participantDepartment, LocalDateTime registrationDate,
                                   Long eventId, String eventName, LocalDateTime eventDate, String eventVenue) {
        this.id = id;
        this.participantName = participantName;
        this.participantEmail = participantEmail;
        this.participantPhone = participantPhone;
        this.participantDepartment = participantDepartment;
        this.registrationDate = registrationDate;
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantEmail() {
        return participantEmail;
    }

    public void setParticipantEmail(String participantEmail) {
        this.participantEmail = participantEmail;
    }

    public String getParticipantPhone() {
        return participantPhone;
    }

    public void setParticipantPhone(String participantPhone) {
        this.participantPhone = participantPhone;
    }

    public String getParticipantDepartment() {
        return participantDepartment;
    }

    public void setParticipantDepartment(String participantDepartment) {
        this.participantDepartment = participantDepartment;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }
}
