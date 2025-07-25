package com.example.MoneyBridge.Controller;

import com.example.MoneyBridge.Models.MeetingProposal;
import com.example.MoneyBridge.Models.User;
import com.example.MoneyBridge.repository.MeetingProposalRepository;
import com.example.MoneyBridge.repository.UserRepository;
import com.example.MoneyBridge.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingProposalRepository meetingProposalRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<MeetingProposal>> getAllTransactions() {
        return ResponseEntity.ok(meetingProposalRepository.findAll());
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        long userCount = userRepository.count();
        long transactionCount = meetingProposalRepository.count();
        return ResponseEntity.ok(Map.of("totalUsers", userCount, "totalTransactions", transactionCount));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        List<MeetingProposal> proposals = meetingProposalRepository.findByProposer(user);

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("proposals", proposals);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Could not delete user. " + e.getMessage()));
        }
    }
}