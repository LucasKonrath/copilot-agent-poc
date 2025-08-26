package com.example.accountcreation;

import com.example.accountcreation.dto.AccountRequestDto;
import com.example.accountcreation.model.AccountRequest;
import com.example.accountcreation.service.GoRulesService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountCreationSystemApplicationTests {

    @Test
    void testAccountRequestDto() {
        AccountRequestDto requestDto = new AccountRequestDto("John Doe", "12345", 30, "5551234567");
        
        assertNotNull(requestDto.getName());
        assertEquals("John Doe", requestDto.getName());
        assertEquals("12345", requestDto.getZipCode());
        assertEquals(30, requestDto.getAge());
        assertEquals("5551234567", requestDto.getPhoneNumber());
    }

    @Test
    void testGoRulesServiceDecisions() {
        GoRulesService goRulesService = new GoRulesService();
        
        // Test auto approval for senior citizen
        AccountRequest seniorRequest = new AccountRequest("Senior Citizen", "12345", 70, "5551234567");
        GoRulesService.DecisionResponse seniorResponse = goRulesService.evaluateAccountRequest(seniorRequest);
        assertEquals(GoRulesService.DecisionResult.AUTO_APPROVE, seniorResponse.getDecision());
        
        // Test auto rejection for underage
        AccountRequest underageRequest = new AccountRequest("Young Person", "12345", 16, "5551234567");
        GoRulesService.DecisionResponse underageResponse = goRulesService.evaluateAccountRequest(underageRequest);
        assertEquals(GoRulesService.DecisionResult.AUTO_REJECT, underageResponse.getDecision());
        
        // Test manual review for young adult (but use a name that doesn't trigger suspicious pattern)
        AccountRequest youngAdultRequest = new AccountRequest("John Smith", "12345", 22, "5551234567");
        GoRulesService.DecisionResponse youngAdultResponse = goRulesService.evaluateAccountRequest(youngAdultRequest);
        assertEquals(GoRulesService.DecisionResult.MANUAL_REVIEW, youngAdultResponse.getDecision());
    }

    @Test
    void testGoRulesServiceHighRiskZipCode() {
        GoRulesService goRulesService = new GoRulesService();
        
        // Test auto rejection for high-risk zip code
        AccountRequest highRiskRequest = new AccountRequest("Good Person", "90210", 30, "5551234567");
        GoRulesService.DecisionResponse response = goRulesService.evaluateAccountRequest(highRiskRequest);
        assertEquals(GoRulesService.DecisionResult.AUTO_REJECT, response.getDecision());
        assertTrue(response.getReason().contains("High-risk zip code"));
    }

    @Test
    void testGoRulesServiceSuspiciousName() {
        GoRulesService goRulesService = new GoRulesService();
        
        // Test manual review for suspicious name containing "test"
        AccountRequest suspiciousRequest = new AccountRequest("Test Person", "12345", 30, "5551234567");
        GoRulesService.DecisionResponse response = goRulesService.evaluateAccountRequest(suspiciousRequest);
        assertEquals(GoRulesService.DecisionResult.MANUAL_REVIEW, response.getDecision());
        assertTrue(response.getReason().contains("Suspicious name pattern"));
    }

    @Test
    void testGoRulesServiceInvalidPhone() {
        GoRulesService goRulesService = new GoRulesService();
        
        // Test auto rejection for invalid phone number pattern
        AccountRequest invalidPhoneRequest = new AccountRequest("Good Person", "12345", 30, "1234567890");
        GoRulesService.DecisionResponse response = goRulesService.evaluateAccountRequest(invalidPhoneRequest);
        assertEquals(GoRulesService.DecisionResult.AUTO_REJECT, response.getDecision());
        assertTrue(response.getReason().contains("Invalid phone number pattern"));
    }
}