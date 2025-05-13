package com.rods.eventostecapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rods.eventostecapi.domain.coupon.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    
}
