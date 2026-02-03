package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.model.RegistrationRequest;
import com.iti.aiplayground.service.RegistrationRequestService;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class RegistrationReviewController {
    private final RegistrationRequestService registrationRequestService;

    public RegistrationReviewController(RegistrationRequestService registrationRequestService) {
        this.registrationRequestService = registrationRequestService;
    }

    @GetMapping("/admin/registrations")
    public String adminList(Model model) {
        model.addAttribute("title", "Admin Registration Approvals");
        model.addAttribute("requests", registrationRequestService.findAll());
        model.addAttribute("basePath", "/admin/registrations");
        return "registration-review";
    }

    @GetMapping("/service-owner/registrations")
    public String ownerList(Model model) {
        model.addAttribute("title", "Service Owner Registration Queue");
        model.addAttribute("requests", registrationRequestService.findAll());
        model.addAttribute("basePath", "/service-owner/registrations");
        return "registration-review";
    }

    @PostMapping("{base}/registrations/{id}/approve")
    public String approve(@PathVariable String base,
                          @PathVariable Long id,
                          @RequestParam(required = false) String reviewerNotes) {
        updateStatus(id, ApprovalStatus.APPROVED, reviewerNotes);
        return "redirect:/" + base + "/registrations";
    }

    @PostMapping("{base}/registrations/{id}/reject")
    public String reject(@PathVariable String base,
                         @PathVariable Long id,
                         @RequestParam(required = false) String reviewerNotes) {
        updateStatus(id, ApprovalStatus.REJECTED, reviewerNotes);
        return "redirect:/" + base + "/registrations";
    }

    private void updateStatus(Long id, ApprovalStatus status, String notes) {
        Optional<RegistrationRequest> request = registrationRequestService.findById(id);
        if (request.isEmpty()) {
            return;
        }
        RegistrationRequest record = request.get();
        record.setStatus(status);
        record.setReviewerNotes(notes);
        registrationRequestService.save(record);
    }
}
