package com.example.MoneyBridge.Services;

import com.example.MoneyBridge.Models.Notification;
import com.example.MoneyBridge.Models.User;
import com.example.MoneyBridge.repository.NotificationRepository;
import com.example.MoneyBridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }
}