package com.college.eventregistration.dto;

public class EventStatsDto {
    private long totalEvents;
    private long totalRegistrations;
    private long upcomingEvents;
    private String mostPopularEventName;
    private int mostPopularEventRegistrations;

    public EventStatsDto() {
    }

    public EventStatsDto(long totalEvents, long totalRegistrations, long upcomingEvents, String mostPopularEventName, int mostPopularEventRegistrations) {
        this.totalEvents = totalEvents;
        this.totalRegistrations = totalRegistrations;
        this.upcomingEvents = upcomingEvents;
        this.mostPopularEventName = mostPopularEventName;
        this.mostPopularEventRegistrations = mostPopularEventRegistrations;
    }

    public long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public long getTotalRegistrations() {
        return totalRegistrations;
    }

    public void setTotalRegistrations(long totalRegistrations) {
        this.totalRegistrations = totalRegistrations;
    }

    public long getUpcomingEvents() {
        return upcomingEvents;
    }

    public void setUpcomingEvents(long upcomingEvents) {
        this.upcomingEvents = upcomingEvents;
    }

    public String getMostPopularEventName() {
        return mostPopularEventName;
    }

    public void setMostPopularEventName(String mostPopularEventName) {
        this.mostPopularEventName = mostPopularEventName;
    }

    public int getMostPopularEventRegistrations() {
        return mostPopularEventRegistrations;
    }

    public void setMostPopularEventRegistrations(int mostPopularEventRegistrations) {
        this.mostPopularEventRegistrations = mostPopularEventRegistrations;
    }
}
