package com.example.MoneyBridge.dto;

import java.time.LocalDateTime;

public record MeetingProposalCreateDTO(
        Long requestId,
        Long proposerId,
        Long receiverId,
        Integer locationId,
        LocalDateTime meetingTime
) {}