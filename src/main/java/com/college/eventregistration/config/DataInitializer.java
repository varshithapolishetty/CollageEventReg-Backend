package com.college.eventregistration.config;

import com.college.eventregistration.model.Event;
import com.college.eventregistration.model.Registration;
import com.college.eventregistration.repository.EventRepository;
import com.college.eventregistration.repository.RegistrationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    public DataInitializer(EventRepository eventRepository, RegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public void run(String... args) {
        if (eventRepository.count() == 0) {
            // Event 1
            Event hackathon = new Event(
                    "Annual Hackathon 2026",
                    LocalDateTime.now().plusDays(7).withHour(9).withMinute(0).withSecond(0).withNano(0),
                    "Tech Innovation Hall - Room 301",
                    "A 24-hour university-wide hackathon focusing on AI, Cloud, and Web3 solutions. Mentors, prizes, and free food provided!"
            );
            Event savedHackathon = eventRepository.save(hackathon);

            // Event 2
            Event aiSummit = new Event(
                    "AI & Robotics Symposium",
                    LocalDateTime.now().plusDays(14).withHour(10).withMinute(30).withSecond(0).withNano(0),
                    "Main Campus Auditorium",
                    "Distinguished speakers from academia and tech industries discussing next-generation autonomous systems and LLMs."
            );
            Event savedAiSummit = eventRepository.save(aiSummit);

            // Event 3
            Event careerFair = new Event(
                    "Spring Campus Career Expo",
                    LocalDateTime.now().plusDays(21).withHour(11).withMinute(0).withSecond(0).withNano(0),
                    "Student Activity Center (SAC)",
                    "Meet hiring managers and alumni from top tech, finance, and engineering companies. Bring copies of your resume!"
            );
            Event savedCareerFair = eventRepository.save(careerFair);

            // Event 4
            Event musicFest = new Event(
                    "Inter-College Cultural & Music Fest",
                    LocalDateTime.now().plusDays(28).withHour(17).withMinute(0).withSecond(0).withNano(0),
                    "Open Air Amphitheatre",
                    "An evening of live band performances, dance battles, food trucks, and artistic showcases from students across colleges."
            );
            Event savedMusicFest = eventRepository.save(musicFest);

            // Sample Registrations for Hackathon
            registrationRepository.save(new Registration(
                    "Aarav Sharma",
                    "aarav.sharma@college.edu",
                    "+91 98765 43210",
                    "Computer Science & Engineering",
                    savedHackathon
            ));
            registrationRepository.save(new Registration(
                    "Priya Patel",
                    "priya.patel@college.edu",
                    "+91 98765 43211",
                    "Information Technology",
                    savedHackathon
            ));
            registrationRepository.save(new Registration(
                    "Rohan Verma",
                    "rohan.verma@college.edu",
                    "+91 98765 43212",
                    "Electronics & Communication",
                    savedHackathon
            ));

            // Sample Registrations for AI Symposium
            registrationRepository.save(new Registration(
                    "Sneha Reddy",
                    "sneha.reddy@college.edu",
                    "+91 98765 43213",
                    "Artificial Intelligence & Data Science",
                    savedAiSummit
            ));
            registrationRepository.save(new Registration(
                    "Ananya Iyer",
                    "ananya.iyer@college.edu",
                    "+91 98765 43214",
                    "Computer Science & Engineering",
                    savedAiSummit
            ));

            // Sample Registration for Career Fair
            registrationRepository.save(new Registration(
                    "Vikram Singh",
                    "vikram.singh@college.edu",
                    "+91 98765 43215",
                    "Mechanical Engineering",
                    savedCareerFair
            ));

            System.out.println(">>> Sample College Events and Registrations successfully initialized!");
        }
    }
}
