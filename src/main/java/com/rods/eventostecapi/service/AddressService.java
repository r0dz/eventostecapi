package com.rods.eventostecapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rods.eventostecapi.domain.address.Address;
import com.rods.eventostecapi.domain.event.Event;
import com.rods.eventostecapi.domain.event.EventRequestDTO;
import com.rods.eventostecapi.repositories.AddressRepository;

@Service
public class AddressService {
    
    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(EventRequestDTO data, Event event) {
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.uf());
        address.setEvent(event);
        
        return addressRepository.save(address);
    }
}
