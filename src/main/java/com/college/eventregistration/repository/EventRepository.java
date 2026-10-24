package com.college.eventregistration.repository;

import com.college.eventregistration.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByOrderByDateAsc();

    long countByDateAfter(LocalDateTime date);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.registrations WHERE e.id = :id")
    Event findByIdWithRegistrations(Long id);
}
