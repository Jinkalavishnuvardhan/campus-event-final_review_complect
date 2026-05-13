package com.example.campus.service;

import com.example.campus.entity.EventJoin;
import com.example.campus.repository.EventJoinRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventJoinService {
    private final EventJoinRepository eventJoinRepository;

    public EventJoinService(EventJoinRepository eventJoinRepository) {
        this.eventJoinRepository = eventJoinRepository;
    }

    public List<EventJoin> getByStudentId(Long studentId) {
        return eventJoinRepository.findByStudentId(studentId);
    }

    public List<EventJoin> getByEventId(Long eventId) {
        return eventJoinRepository.findByEventId(eventId);
    }

    public boolean exists(Long studentId, Long eventId) {
        return eventJoinRepository.existsByStudentIdAndEventId(studentId, eventId);
    }

    public void save(EventJoin join) {
        eventJoinRepository.save(join);
    }

    public void deleteAllByEventId(Long eventId) {
        List<EventJoin> joins = eventJoinRepository.findByEventId(eventId);
        eventJoinRepository.deleteAll(joins);
    }

    public void deleteAllByStudentId(Long studentId) {
        List<EventJoin> joins = eventJoinRepository.findByStudentId(studentId);
        eventJoinRepository.deleteAll(joins);
    }

    public List<EventJoin> getAll() {
        return eventJoinRepository.findAll();
    }
}
