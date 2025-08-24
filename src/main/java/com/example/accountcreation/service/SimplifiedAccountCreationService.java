package com.example.accountcreation.service;

import com.example.accountcreation.dto.AccountRequestDto;
import com.example.accountcreation.dto.AccountResponseDto;
import com.example.accountcreation.model.AccountRequest;
import com.example.accountcreation.model.AccountStatus;
import com.example.accountcreation.repository.AccountRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SimplifiedAccountCreationService {
    
    private static final Logger logger = LoggerFactory.getLogger(SimplifiedAccountCreationService.class);
    
    @Autowired
    private AccountRequestRepository accountRequestRepository;
    
    @Autowired
    private GoRulesService goRulesService;
    
    public AccountResponseDto createAccountRequest(AccountRequestDto requestDto) {
        logger.info("Creating account request for: {}", requestDto.getName());
        
        // Create and save the account request
        AccountRequest request = new AccountRequest(
            requestDto.getName(),
            requestDto.getZipCode(),
            requestDto.getAge(),
            requestDto.getPhoneNumber()
        );
        
        request = accountRequestRepository.save(request);
        logger.info("Account request saved with ID: {}", request.getId());
        
        // Simulate BPMN process execution asynchronously
        Long requestId = request.getId();
        CompletableFuture.runAsync(() -> executeAccountCreationProcess(requestId));
        
        return convertToResponseDto(request);
    }
    
    private void executeAccountCreationProcess(Long requestId) {
        try {
            logger.info("Starting BPMN-like process for request ID: {}", requestId);
            
            // Step 1: Validate Request
            AccountRequest request = validateRequest(requestId);
            
            // Step 2: Apply GoRules
            GoRulesService.DecisionResponse decision = applyBusinessRules(request);
            
            // Step 3: Process based on decision
            processDecision(request, decision);
            
            // Step 4: Send notification
            sendNotification(request);
            
            logger.info("BPMN-like process completed for request ID: {}", requestId);
            
        } catch (Exception e) {
            logger.error("Error processing account request {}", requestId, e);
        }
    }
    
    private AccountRequest validateRequest(Long requestId) {
        logger.info("Validating request for ID: {}", requestId);
        
        AccountRequest request = accountRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Account request not found: " + requestId));
        
        // Set process instance ID to simulate BPMN process
        request.setProcessInstanceId("simplified-process-" + requestId);
        accountRequestRepository.save(request);
        
        logger.info("Request validation completed for: {} (ID: {})", request.getName(), requestId);
        return request;
    }
    
    private GoRulesService.DecisionResponse applyBusinessRules(AccountRequest request) {
        logger.info("Applying business rules for request ID: {}", request.getId());
        
        GoRulesService.DecisionResponse decision = goRulesService.evaluateAccountRequest(request);
        
        logger.info("Decision made for request {}: {} - {}", 
                   request.getId(), 
                   decision.getDecision().name(), 
                   decision.getReason());
        
        return decision;
    }
    
    private void processDecision(AccountRequest request, GoRulesService.DecisionResponse decision) {
        logger.info("Processing decision {} for request ID: {}", decision.getDecision(), request.getId());
        
        switch (decision.getDecision()) {
            case AUTO_APPROVE:
                request.setStatus(AccountStatus.AUTO_APPROVED);
                break;
            case AUTO_REJECT:
                request.setStatus(AccountStatus.AUTO_REJECTED);
                request.setRejectionReason(decision.getReason());
                break;
            case MANUAL_REVIEW:
                request.setStatus(AccountStatus.MANUAL_REVIEW);
                break;
        }
        
        accountRequestRepository.save(request);
        
        logger.info("Request {} updated to status: {}", request.getId(), request.getStatus());
    }
    
    private void sendNotification(AccountRequest request) {
        logger.info("Sending notification for request ID: {}", request.getId());
        
        // Simulate notification
        String message = generateNotificationMessage(request);
        
        logger.info("=== NOTIFICATION ===");
        logger.info("To: {} (Phone: {})", request.getName(), request.getPhoneNumber());
        logger.info("Status: {}", request.getStatus());
        logger.info("Message: {}", message);
        logger.info("===================");
        
        logger.info("Notification sent successfully for request ID: {}", request.getId());
    }
    
    private String generateNotificationMessage(AccountRequest request) {
        switch (request.getStatus()) {
            case AUTO_APPROVED:
                return "Your account has been automatically approved!";
            case AUTO_REJECTED:
                return "Your account application has been rejected. Reason: " + request.getRejectionReason();
            case MANUAL_REVIEW:
                return "Your account application is under manual review. You will be notified once a decision is made.";
            default:
                return "Your account application status has been updated.";
        }
    }
    
    public AccountResponseDto getAccountRequest(Long id) {
        AccountRequest request = accountRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Account request not found: " + id));
        return convertToResponseDto(request);
    }
    
    public List<AccountResponseDto> getAllAccountRequests() {
        return accountRequestRepository.findAll().stream()
            .map(this::convertToResponseDto)
            .collect(Collectors.toList());
    }
    
    public List<AccountResponseDto> getAccountRequestsByStatus(AccountStatus status) {
        return accountRequestRepository.findByStatus(status).stream()
            .map(this::convertToResponseDto)
            .collect(Collectors.toList());
    }
    
    public List<AccountResponseDto> getPendingManualReviews() {
        return accountRequestRepository.findByStatus(AccountStatus.MANUAL_REVIEW).stream()
            .map(this::convertToResponseDto)
            .collect(Collectors.toList());
    }
    
    private AccountResponseDto convertToResponseDto(AccountRequest request) {
        AccountResponseDto dto = new AccountResponseDto();
        dto.setId(request.getId());
        dto.setName(request.getName());
        dto.setZipCode(request.getZipCode());
        dto.setAge(request.getAge());
        dto.setPhoneNumber(request.getPhoneNumber());
        dto.setStatus(request.getStatus());
        dto.setProcessInstanceId(request.getProcessInstanceId());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        dto.setRejectionReason(request.getRejectionReason());
        return dto;
    }
}