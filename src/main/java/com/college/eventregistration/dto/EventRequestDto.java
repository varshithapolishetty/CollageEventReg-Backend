package com.college.eventregistration.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class EventRequestDto {

    @NotBlank(message = "Event name is required")
    @Size(min = 2, max = 150, message = "Event name must be between 2 and 150 characters")
    private String name;

    @NotNull(message = "Event date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @NotBlank(message = "Venue is required")
    @Size(min = 2, max = 150, message = "Venue must be between 2 and 150 characters")
    private String venue;

    @NotBlank(message = "Short description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    public EventRequestDto() {
    }

    public EventRequestDto(String name, LocalDateTime date, String venue, String description) {
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
