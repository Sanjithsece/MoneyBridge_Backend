package com.example.MoneyBridge.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    private String profilePictureUrl;

    private String upiQrCodeUrl;

    @Column(precision = 2, scale = 1)
    private BigDecimal averageRating = BigDecimal.valueOf(5.0);

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Prevents infinite loops in API responses
    private List<ExchangeRequest> exchangeRequests;

    @OneToMany(mappedBy = "proposer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MeetingProposal> proposalsMade;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MeetingProposal> proposalsReceived;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Notification> notifications;
}