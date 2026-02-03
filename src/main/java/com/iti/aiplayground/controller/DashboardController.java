package com.iti.aiplayground.controller;

import com.iti.aiplayground.service.AiServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final AiServiceService aiServiceService;

    public DashboardController(AiServiceService aiServiceService) {
        this.aiServiceService = aiServiceService;
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("title", "Admin Dashboard");
        model.addAttribute("serviceCount", aiServiceService.count());
        model.addAttribute("pendingApprovals", 12);
        model.addAttribute("activeUsers", 58);
        return "dashboard/admin";
    }

    @GetMapping("/service-owner")
    public String serviceOwner(Model model) {
        model.addAttribute("title", "Service Owner Dashboard");
        model.addAttribute("serviceCount", aiServiceService.count());
        model.addAttribute("drafts", 5);
        model.addAttribute("published", 8);
        return "dashboard/service-owner";
    }

    @GetMapping("/approver")
    public String approver(Model model) {
        model.addAttribute("title", "Approver Dashboard");
        model.addAttribute("pending", 7);
        model.addAttribute("approvedThisWeek", 14);
        model.addAttribute("rejectedThisWeek", 2);
        return "dashboard/approver";
    }
}
