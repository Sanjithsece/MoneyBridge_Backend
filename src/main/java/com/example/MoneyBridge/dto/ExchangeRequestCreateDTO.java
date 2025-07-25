package com.example.MoneyBridge.dto;

import com.example.MoneyBridge.Models.ExchangeRequest.MoneyType;
import java.math.BigDecimal;

public record ExchangeRequestCreateDTO(
        Long userId,
        BigDecimal amount,
        MoneyType haveType,
        MoneyType needType,
        String locationHint
) {}