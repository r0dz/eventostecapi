package com.rods.eventostecapi.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.rods.eventostecapi.domain.event.Event;
import com.rods.eventostecapi.domain.event.EventRequestDTO;
import com.rods.eventostecapi.domain.event.EventResponseDTO;
import com.rods.eventostecapi.repositories.EventRepository;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private AddressService addressService;

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventRequestDTO data) {
        String imgUrl = null;

        if (data.image() != null) {
            imgUrl = this.uploadImg(data.image());
        }

        Event event = new Event();
        event.setTitle(data.title());
        event.setDescription(data.description());
        event.setEventUrl(data.eventUrl());
        event.setDate(new Date(data.date()));
        event.setImgUrl(imgUrl);
        event.setRemote(data.remote());
        this.eventRepository.save(event);

        if(!data.remote()) {
            this.addressService.createAddress(data, event);
        } 

        return event;
    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events = this.eventRepository.findUpcomingEvents(new Date(), pageable);
        return events.map(event -> new EventResponseDTO(event.getId(), 
                            event.getTitle(), 
                            event.getDescription(), 
                            event.getDate(), 
                            event.getAddress() == null ? "" : event.getAddress().getCity(), 
                            event.getAddress() == null ? "" : event.getAddress().getUf(), 
                            event.isRemote(), 
                            event.getEventUrl(), 
                            event.getImgUrl())
                            )
                            .stream().toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String title, String city, String uf, Date startDate, Date endDate) {
        title = (title == null) ? "" : title;
        city = (city == null) ? "" : city;
        uf = (uf == null) ? "" : uf;
        startDate = (startDate == null) ? new Date(0) : startDate;
        endDate = (endDate == null) ? new Date() : endDate;

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events = this.eventRepository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);
        return events.map(event -> new EventResponseDTO(event.getId(), 
                            event.getTitle(), 
                            event.getDescription(), 
                            event.getDate(), 
                            event.getAddress() == null ? "" : event.getAddress().getCity(), 
                            event.getAddress() == null ? "" : event.getAddress().getUf(), 
                            event.isRemote(), 
                            event.getEventUrl(), 
                            event.getImgUrl())
                            )
                            .stream().toList();
    }

    private String uploadImg(MultipartFile multipartFile) {
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            File file = this.convertMultiPartToFile(multipartFile);
            s3Client.putObject(bucketName, filename, file);
            file.delete();
            return s3Client.getUrl(bucketName, filename).toString();
        } catch (Exception e) {
            System.out.println("Error uploading file to S3: " + e.getMessage());
            return null;
        }
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }
}
