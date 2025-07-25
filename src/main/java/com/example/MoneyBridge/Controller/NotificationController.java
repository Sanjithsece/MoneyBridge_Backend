package com.example.MoneyBridge.Controller;

import com.example.MoneyBridge.Models.Notification;
import com.example.MoneyBridge.repository.NotificationRepository;
import com.example.MoneyBridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications() {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByPhoneNumber(phone)
                .map(user -> ResponseEntity.ok(notificationRepository.findByUserOrderByCreatedAtDesc(user)))
                .orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        long count = userRepository.findByPhoneNumber(phone)
                .map(user -> notificationRepository.countByUserAndIsReadFalse(user))
                .orElse(0L);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PostMapping("/mark-as-read")
    public ResponseEntity<?> markNotificationsAsRead() {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.findByPhoneNumber(phone).ifPresent(notificationRepository::markAllAsReadForUser);
        return ResponseEntity.ok().build();
    }
}