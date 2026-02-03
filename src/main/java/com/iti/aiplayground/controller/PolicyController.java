package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.Policy;
import com.iti.aiplayground.service.PolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PolicyController {
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/policy")
    public String policy(Model model) {
        model.addAttribute("policy", policyService.getLatest().orElseGet(Policy::new));
        return "policy";
    }
}
