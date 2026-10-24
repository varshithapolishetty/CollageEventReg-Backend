package com.college.eventregistration.repository;

import com.college.eventregistration.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByEventIdOrderByRegistrationDateDesc(Long eventId);

    boolean existsByEventIdAndParticipantEmailIgnoreCase(Long eventId, String participantEmail);

    long countByEventId(Long eventId);

    List<Registration> findAllByOrderByRegistrationDateDesc();

    List<Registration> findByParticipantEmailIgnoreCaseOrderByRegistrationDateDesc(String participantEmail);
}
