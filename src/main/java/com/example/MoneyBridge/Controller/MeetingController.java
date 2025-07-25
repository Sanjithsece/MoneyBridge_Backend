package com.example.MoneyBridge.Controller;

import com.example.MoneyBridge.Services.MeetingService;
import com.example.MoneyBridge.dto.MeetingProposalCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable; // Add this import
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    @Autowired private MeetingService meetingService;

    @PostMapping("/propose")
    public ResponseEntity<?> proposeMeeting(@RequestBody MeetingProposalCreateDTO dto) {
        try {
            return new ResponseEntity<>(meetingService.createProposal(dto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{proposalId}/accept")
    public ResponseEntity<?> acceptMeeting(@PathVariable Long proposalId) {
        try {
            return ResponseEntity.ok(meetingService.acceptProposal(proposalId));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{proposalId}/reject")
    public ResponseEntity<?> rejectMeeting(@PathVariable Long proposalId) {
        try {
            return ResponseEntity.ok(meetingService.rejectProposal(proposalId));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}