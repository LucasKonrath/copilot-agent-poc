package com.example.accountcreation.controller;

import com.example.accountcreation.dto.AccountRequestDto;
import com.example.accountcreation.dto.AccountResponseDto;
import com.example.accountcreation.model.AccountStatus;
import com.example.accountcreation.service.SimplifiedAccountCreationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {
    
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    private SimplifiedAccountCreationService accountCreationService;
    
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto requestDto) {
        try {
            logger.info("Received account creation request for: {}", requestDto.getName());
            AccountResponseDto response = accountCreationService.createAccountRequest(requestDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating account request", e);
            throw new RuntimeException("Failed to create account request: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long id) {
        try {
            AccountResponseDto response = accountCreationService.getAccountRequest(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error retrieving account request: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        try {
            List<AccountResponseDto> responses = accountCreationService.getAllAccountRequests();
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            logger.error("Error retrieving all account requests", e);
            throw new RuntimeException("Failed to retrieve account requests: " + e.getMessage());
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AccountResponseDto>> getAccountsByStatus(@PathVariable AccountStatus status) {
        try {
            List<AccountResponseDto> responses = accountCreationService.getAccountRequestsByStatus(status);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            logger.error("Error retrieving account requests by status: {}", status, e);
            throw new RuntimeException("Failed to retrieve account requests by status: " + e.getMessage());
        }
    }
    
    @GetMapping("/pending-reviews")
    public ResponseEntity<List<AccountResponseDto>> getPendingManualReviews() {
        try {
            List<AccountResponseDto> responses = accountCreationService.getPendingManualReviews();
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            logger.error("Error retrieving pending manual reviews", e);
            throw new RuntimeException("Failed to retrieve pending manual reviews: " + e.getMessage());
        }
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        logger.error("Runtime exception occurred", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}