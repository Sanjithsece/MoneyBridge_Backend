package com.example.MoneyBridge.Controller;

import com.example.MoneyBridge.Services.ExchangeRequestService;
import com.example.MoneyBridge.dto.ExchangeRequestCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/requests")
public class ExchangeRequestController {
    @Autowired private ExchangeRequestService requestService;

    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody ExchangeRequestCreateDTO dto) {
        try {
            return new ResponseEntity<>(requestService.createRequest(dto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/open")
    public ResponseEntity<?> getOpenRequests() {
        return ResponseEntity.ok(requestService.getAllOpenRequests());
    }
    @GetMapping("/my-requests")
    public ResponseEntity<?> getMyRequests() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(requestService.getRequestsForUser(phoneNumber));
    }
}