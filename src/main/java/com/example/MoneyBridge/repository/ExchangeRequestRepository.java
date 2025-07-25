package com.example.MoneyBridge.repository;
import com.example.MoneyBridge.Models.ExchangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.MoneyBridge.Models.User; // Add this import
import java.util.List; // And this one

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Long> {
    List<ExchangeRequest> findByStatus(ExchangeRequest.RequestStatus status);

    List<ExchangeRequest> findByUser(User user);
    void deleteAllByUser(User user);
}