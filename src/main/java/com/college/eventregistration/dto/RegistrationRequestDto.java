package com.college.eventregistration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegistrationRequestDto {

    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotBlank(message = "Participant name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String participantName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please provide a valid email address (e.g. name@college.edu)")
    @Size(max = 120, message = "Email cannot exceed 120 characters")
    private String participantEmail;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String participantPhone;

    @Size(max = 100, message = "Department cannot exceed 100 characters")
    private String participantDepartment;

    public RegistrationRequestDto() {
    }

    public RegistrationRequestDto(Long eventId, String participantName, String participantEmail, String participantPhone, String participantDepartment) {
        this.eventId = eventId;
        this.participantName = participantName;
        this.participantEmail = participantEmail;
        this.participantPhone = participantPhone;
        this.participantDepartment = participantDepartment;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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
}
