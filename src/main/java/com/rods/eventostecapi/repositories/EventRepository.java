package com.rods.eventostecapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rods.eventostecapi.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {
    
}
