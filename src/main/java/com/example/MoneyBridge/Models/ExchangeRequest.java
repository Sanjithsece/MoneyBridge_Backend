package com.example.MoneyBridge.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "exchange_requests")
@Data
public class ExchangeRequest {
    public enum MoneyType { CASH, UPI }
    public enum RequestStatus { OPEN, PENDING, COMPLETED, CANCELLED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MoneyType haveType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MoneyType needType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.OPEN;

    private String locationHint;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "exchangeRequest")
    @JsonManagedReference
    private List<MeetingProposal> meetingProposals;
}