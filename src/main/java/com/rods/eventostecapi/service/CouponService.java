package com.rods.eventostecapi.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rods.eventostecapi.domain.coupon.Coupon;
import com.rods.eventostecapi.domain.coupon.CouponDTO;
import com.rods.eventostecapi.domain.event.Event;
import com.rods.eventostecapi.repositories.CouponRepository;
import com.rods.eventostecapi.repositories.EventRepository;

@Service
public class CouponService {
    
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;

    public Coupon addCouponToEvent(UUID eventId, CouponDTO couponDTO) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Coupon coupon = new Coupon();

        coupon.setCode(couponDTO.code());
        coupon.setDiscount(couponDTO.discount());
        coupon.setValid(new Date(couponDTO.valid()));
        coupon.setEvent(event);

        return couponRepository.save(coupon);
    }
}
