package com.rods.eventostecapi.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rods.eventostecapi.domain.coupon.Coupon;
import com.rods.eventostecapi.domain.coupon.CouponDTO;
import com.rods.eventostecapi.service.CouponService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    
    @Autowired
    private CouponService couponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Coupon> addCouponToEvent(@PathVariable UUID eventId, @RequestBody CouponDTO data) {
        Coupon coupon = this.couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupon);
    }
}
