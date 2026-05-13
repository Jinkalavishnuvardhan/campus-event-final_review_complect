package com.example.campus.repository;

import com.example.campus.entity.EventJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventJoinRepository extends JpaRepository<EventJoin, Long> {
    List<EventJoin> findByStudentId(Long studentId);
    List<EventJoin> findByEventId(Long eventId);
    boolean existsByStudentIdAndEventId(Long studentId, Long eventId);
}
