package com.example.MoneyBridge.Services;

import com.example.MoneyBridge.Models.ExchangeRequest;
import com.example.MoneyBridge.Models.User;
import com.example.MoneyBridge.dto.ExchangeRequestCreateDTO;
import com.example.MoneyBridge.repository.ExchangeRequestRepository;
import com.example.MoneyBridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExchangeRequestService {
    @Autowired private ExchangeRequestRepository requestRepository;
    @Autowired private UserRepository userRepository;

    public ExchangeRequest createRequest(ExchangeRequestCreateDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExchangeRequest newRequest = new ExchangeRequest();
        newRequest.setUser(user);
        newRequest.setAmount(dto.amount());
        newRequest.setHaveType(dto.haveType());
        newRequest.setNeedType(dto.needType());
        newRequest.setLocationHint(dto.locationHint());
        newRequest.setStatus(ExchangeRequest.RequestStatus.OPEN);

        return requestRepository.save(newRequest);
    }

    public List<ExchangeRequest> getAllOpenRequests() {
        return requestRepository.findAll().stream()
                .filter(req -> req.getStatus() == ExchangeRequest.RequestStatus.OPEN)
                .toList();
    }
    public List<ExchangeRequest> getRequestsForUser(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return requestRepository.findByUser(user);
    }
}