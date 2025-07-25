package com.example.MoneyBridge.repository;

import com.example.MoneyBridge.Models.MeetingProposal;
import com.example.MoneyBridge.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingProposalRepository extends JpaRepository<MeetingProposal, Long> {
    List<MeetingProposal> findByProposer(User user);
    void deleteAllByProposerOrReceiver(User proposer, User receiver);
}