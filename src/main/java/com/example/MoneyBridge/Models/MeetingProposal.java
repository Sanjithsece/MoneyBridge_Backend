package com.example.MoneyBridge.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_proposals")
@Data
public class MeetingProposal {
    public enum ProposalStatus { PROPOSED, ACCEPTED, DECLINED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    @JsonBackReference
    private ExchangeRequest exchangeRequest;

    @ManyToOne
    @JoinColumn(name = "proposer_id", nullable = false)
    private User proposer;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private CampusLocation location;

    @Column(nullable = false)
    private LocalDateTime meetingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status = ProposalStatus.PROPOSED;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;
}