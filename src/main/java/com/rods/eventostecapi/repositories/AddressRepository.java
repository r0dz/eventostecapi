package com.rods.eventostecapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rods.eventostecapi.domain.address.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    
}
