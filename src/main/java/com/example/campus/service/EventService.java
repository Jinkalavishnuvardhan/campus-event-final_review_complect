package com.example.campus.service;

import com.example.campus.entity.Event;
import com.example.campus.repository.EventRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
    private static final String DEFAULT_LOCATION = "Vel Tech Rangarajan Dr. Sagunthala R&D Institute of Science and Technology42, Avadi-Vel Tech Road, Vel Nagar,Avadi, Chennai - 600 062,Tamil Nadu, India.";
    private final EventRepository eventRepository;
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void saveEvent(Event event) {
        // Ensure every event has the campus address if not provided
        if (event.getLocationAddress() == null || event.getLocationAddress().trim().isEmpty()) {
            event.setLocationAddress(DEFAULT_LOCATION);
        }
        eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
