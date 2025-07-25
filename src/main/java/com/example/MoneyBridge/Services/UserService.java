package com.example.MoneyBridge.Services;

import com.example.MoneyBridge.Models.User;
import com.example.MoneyBridge.repository.ExchangeRequestRepository;
import com.example.MoneyBridge.repository.MeetingProposalRepository;
import com.example.MoneyBridge.repository.NotificationRepository;
import com.example.MoneyBridge.repository.UserRepository;
import com.example.MoneyBridge.dto.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ExchangeRequestRepository exchangeRequestRepository;
    @Autowired private MeetingProposalRepository meetingProposalRepository;
    @Autowired private NotificationRepository notificationRepository;

    public User registerUser(UserRegistrationRequest request) {
        userRepository.findByPhoneNumber(request.phoneNumber()).ifPresent(u -> {
            throw new IllegalStateException("Phone number already registered.");
        });
        User newUser = new User();
        newUser.setFullName(request.fullName());
        newUser.setPhoneNumber(request.phoneNumber());
        newUser.setPasswordHash(passwordEncoder.encode(request.password()));
        return userRepository.save(newUser);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public User updateProfilePicture(String phoneNumber, String fileUrl) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfilePictureUrl(fileUrl);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        meetingProposalRepository.deleteAllByProposerOrReceiver(user, user);

        exchangeRequestRepository.deleteAllByUser(user);

        notificationRepository.deleteAllByUser(user);

        userRepository.delete(user);
    }
}