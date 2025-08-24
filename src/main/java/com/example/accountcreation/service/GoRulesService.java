package com.example.accountcreation.service;

import com.example.accountcreation.model.AccountRequest;
import org.springframework.stereotype.Service;

@Service
public class GoRulesService {
    
    public enum DecisionResult {
        AUTO_APPROVE,
        AUTO_REJECT,
        MANUAL_REVIEW
    }
    
    public static class DecisionResponse {
        private final DecisionResult decision;
        private final String reason;
        
        public DecisionResponse(DecisionResult decision, String reason) {
            this.decision = decision;
            this.reason = reason;
        }
        
        public DecisionResult getDecision() {
            return decision;
        }
        
        public String getReason() {
            return reason;
        }
    }
    
    /**
     * Evaluates account creation request based on business rules
     * This simulates integration with GoRules decision engine
     */
    public DecisionResponse evaluateAccountRequest(AccountRequest request) {
        // Rule 1: Age-based auto rejection
        if (request.getAge() < 18) {
            return new DecisionResponse(DecisionResult.AUTO_REJECT, "Age below minimum requirement");
        }
        
        // Rule 2: Age-based auto approval for seniors
        if (request.getAge() >= 65) {
            return new DecisionResponse(DecisionResult.AUTO_APPROVE, "Senior citizen auto-approval");
        }
        
        // Rule 3: Zip code based rules (high-risk zip codes)
        if (isHighRiskZipCode(request.getZipCode())) {
            return new DecisionResponse(DecisionResult.AUTO_REJECT, "High-risk zip code");
        }
        
        // Rule 4: Premium zip codes get auto approval
        if (isPremiumZipCode(request.getZipCode())) {
            return new DecisionResponse(DecisionResult.AUTO_APPROVE, "Premium zip code area");
        }
        
        // Rule 5: Phone number validation patterns
        if (hasInvalidPhonePattern(request.getPhoneNumber())) {
            return new DecisionResponse(DecisionResult.AUTO_REJECT, "Invalid phone number pattern");
        }
        
        // Rule 6: Name-based suspicious patterns
        if (hasSuspiciousNamePattern(request.getName())) {
            return new DecisionResponse(DecisionResult.MANUAL_REVIEW, "Suspicious name pattern requires review");
        }
        
        // Rule 7: Young adults (18-25) require manual review
        if (request.getAge() >= 18 && request.getAge() <= 25) {
            return new DecisionResponse(DecisionResult.MANUAL_REVIEW, "Young adult application requires manual review");
        }
        
        // Rule 8: Middle-aged applicants in normal zip codes get auto approval
        if (request.getAge() > 25 && request.getAge() < 65) {
            return new DecisionResponse(DecisionResult.AUTO_APPROVE, "Standard approval criteria met");
        }
        
        // Default: Manual review for edge cases
        return new DecisionResponse(DecisionResult.MANUAL_REVIEW, "Default manual review");
    }
    
    private boolean isHighRiskZipCode(String zipCode) {
        // Simulate high-risk zip codes (would be configurable in real system)
        String[] highRiskZipCodes = {"90210", "10001", "60601"};
        for (String riskCode : highRiskZipCodes) {
            if (zipCode.startsWith(riskCode)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isPremiumZipCode(String zipCode) {
        // Simulate premium zip codes (would be configurable in real system)
        String[] premiumZipCodes = {"94102", "90210", "10021"};
        for (String premiumCode : premiumZipCodes) {
            if (zipCode.startsWith(premiumCode)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasInvalidPhonePattern(String phoneNumber) {
        // Check for obvious fake numbers
        return phoneNumber.matches("(\\d)\\1{9}") || // All same digits
               phoneNumber.equals("1234567890") ||     // Sequential
               phoneNumber.equals("0000000000");       // All zeros
    }
    
    private boolean hasSuspiciousNamePattern(String name) {
        // Simple suspicious name detection
        return name.toLowerCase().contains("test") ||
               name.toLowerCase().contains("fake") ||
               name.length() < 2 ||
               !name.matches(".*[a-zA-Z].*"); // Must contain at least one letter
    }
}