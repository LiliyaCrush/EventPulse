package com.liliya.eventpulse.repository;

import com.liliya.eventpulse.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByTitleContainingIgnoreCase(String title);

}