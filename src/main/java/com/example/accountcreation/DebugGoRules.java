package com.example.accountcreation;

import com.example.accountcreation.model.AccountRequest;
import com.example.accountcreation.service.GoRulesService;

public class DebugGoRules {
    public static void main(String[] args) {
        GoRulesService goRulesService = new GoRulesService();
        
        AccountRequest request = new AccountRequest("Test User", "12345", 30, "1234567890");
        GoRulesService.DecisionResponse response = goRulesService.evaluateAccountRequest(request);
        
        System.out.println("Decision: " + response.getDecision());
        System.out.println("Reason: " + response.getReason());
    }
}