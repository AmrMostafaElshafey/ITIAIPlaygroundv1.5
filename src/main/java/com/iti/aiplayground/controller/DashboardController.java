package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.service.AiServiceService;
import com.iti.aiplayground.service.ServiceRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final AiServiceService aiServiceService;
    private final ServiceRequestService serviceRequestService;

    public DashboardController(AiServiceService aiServiceService, ServiceRequestService serviceRequestService) {
        this.aiServiceService = aiServiceService;
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Admin Dashboard");
        model.addAttribute("serviceCount", aiServiceService.count());
        model.addAttribute("pendingApprovals", serviceRequestService.countByStatus(ApprovalStatus.PENDING));
        model.addAttribute("approvedRequests", serviceRequestService.countByStatus(ApprovalStatus.APPROVED));
        model.addAttribute("rejectedRequests", serviceRequestService.countByStatus(ApprovalStatus.REJECTED));
        model.addAttribute("totalRequests", serviceRequestService.findAll().size());
        model.addAttribute("serviceUsageCount", serviceRequestService.countByStatus(ApprovalStatus.APPROVED));
        return "dashboard/admin";
    }

    @GetMapping("/service-owner")
    public String serviceOwner(Model model, HttpSession session) {
        if (!"SERVICE_OWNER".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Service Owner Dashboard");
        model.addAttribute("serviceCount", aiServiceService.count());
        model.addAttribute("drafts", 5);
        model.addAttribute("published", 8);
        return "dashboard/service-owner";
    }

    @GetMapping("/approver")
    public String approver(Model model, HttpSession session) {
        if (!"APPROVER".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Approver Dashboard");
        model.addAttribute("pending", 7);
        model.addAttribute("approvedThisWeek", 14);
        model.addAttribute("rejectedThisWeek", 2);
        return "dashboard/approver";
    }
}
