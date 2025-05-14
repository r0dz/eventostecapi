package com.rods.eventostecapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rods.eventostecapi.domain.event.Event;
import com.rods.eventostecapi.domain.event.EventRequestDTO;
import com.rods.eventostecapi.service.EventService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/event")
public class EventController {
    
    @Autowired
    private EventService eventService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(@RequestParam("image") MultipartFile file,
                                        @RequestParam("title") String title,
                                        @RequestParam("description") String description,
                                        @RequestParam("eventUrl") String eventUrl,
                                        @RequestParam("date") Long date,
                                        @RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("remote") Boolean remote) {
        EventRequestDTO eventRequestDTO = new EventRequestDTO(
            title,
            description,
            date,
            city,
            state,
            remote,
            eventUrl,
            file
        );
        Event event = this.eventService.createEvent(eventRequestDTO);
        return ResponseEntity.ok(event);
    }
    
}
