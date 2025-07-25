package com.example.MoneyBridge.Services;

import com.example.MoneyBridge.Models.ExchangeRequest;
import com.example.MoneyBridge.Models.MeetingProposal;
import com.example.MoneyBridge.dto.MeetingProposalCreateDTO;
import com.example.MoneyBridge.repository.CampusLocationRepository;
import com.example.MoneyBridge.repository.ExchangeRequestRepository;
import com.example.MoneyBridge.repository.MeetingProposalRepository;
import com.example.MoneyBridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Use Spring's Transactional

@Service
public class MeetingService {
    @Autowired private MeetingProposalRepository meetingRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ExchangeRequestRepository requestRepository;
    @Autowired private CampusLocationRepository locationRepository;
    @Autowired private NotificationService notificationService;

    public MeetingProposal createProposal(MeetingProposalCreateDTO dto) {
        var request = requestRepository.findById(dto.requestId())
                .orElseThrow(() -> new RuntimeException("Exchange Request not found"));
        var proposer = userRepository.findById(dto.proposerId())
                .orElseThrow(() -> new RuntimeException("Proposer not found"));
        var receiver = userRepository.findById(dto.receiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        var location = locationRepository.findById(dto.locationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        MeetingProposal proposal = new MeetingProposal();
        proposal.setExchangeRequest(request);
        proposal.setProposer(proposer);
        proposal.setReceiver(receiver);
        proposal.setLocation(location);
        proposal.setMeetingTime(dto.meetingTime());
        proposal.setStatus(MeetingProposal.ProposalStatus.PROPOSED);

        MeetingProposal savedProposal = meetingRepository.save(proposal);

        String message = String.format("%s has proposed a meeting for your request of ₹%s.",
                savedProposal.getProposer().getFullName(), savedProposal.getExchangeRequest().getAmount());
        notificationService.createNotification(savedProposal.getReceiver(), message);

        return savedProposal;
    }

    @Transactional
    public MeetingProposal acceptProposal(Long proposalId) {
        MeetingProposal proposal = meetingRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        proposal.setStatus(MeetingProposal.ProposalStatus.ACCEPTED);

        ExchangeRequest request = proposal.getExchangeRequest();
        request.setStatus(ExchangeRequest.RequestStatus.PENDING);

        String message = String.format("%s has accepted your proposal for the request of ₹%s.",
                proposal.getReceiver().getFullName(), proposal.getExchangeRequest().getAmount());
        notificationService.createNotification(proposal.getProposer(), message);

        return proposal;
    }

    public MeetingProposal rejectProposal(Long proposalId) {
        MeetingProposal proposal = meetingRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        proposal.setStatus(MeetingProposal.ProposalStatus.DECLINED);

        return meetingRepository.save(proposal);
    }
}