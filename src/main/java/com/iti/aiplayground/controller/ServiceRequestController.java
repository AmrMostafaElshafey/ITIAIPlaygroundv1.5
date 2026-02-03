package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.model.RegistrationRequest;
import com.iti.aiplayground.model.ServiceRequest;
import com.iti.aiplayground.service.RegistrationRequestService;
import com.iti.aiplayground.service.ServiceRequestService;
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
public class ServiceRequestController {
    private final ServiceRequestService serviceRequestService;
    private final RegistrationRequestService registrationRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService,
                                    RegistrationRequestService registrationRequestService) {
        this.serviceRequestService = serviceRequestService;
        this.registrationRequestService = registrationRequestService;
    }

    @GetMapping("/service-requests/new")
    public String newRequest(Model model) {
        model.addAttribute("request", new ServiceRequest());
        model.addAttribute("approvedRegistrations", registrationRequestService.findApproved());
        return "service-request-form";
    }

    @PostMapping("/service-requests")
    public String submitRequest(@RequestParam Long registrationId,
                                @RequestParam String serviceName,
                                @RequestParam(required = false) String requestNotes,
                                Model model) {
        Optional<RegistrationRequest> registrationRequest = registrationRequestService.findById(registrationId);
        if (registrationRequest.isEmpty()) {
            model.addAttribute("error", "Registration record not found.");
            return newRequest(model);
        }
        ServiceRequest request = new ServiceRequest();
        request.setRegistrationRequest(registrationRequest.get());
        request.setServiceName(serviceName);
        request.setRequestNotes(requestNotes);
        serviceRequestService.save(request);
        model.addAttribute("submitted", true);
        return newRequest(model);
    }

    @GetMapping("/service-owner/service-requests")
    public String reviewQueue(Model model) {
        model.addAttribute("requests", serviceRequestService.findAll());
        return "service-request-review";
    }

    @PostMapping("/service-owner/service-requests/{id}/approve")
    public String approve(@PathVariable Long id, @RequestParam(required = false) String reviewerNotes) {
        updateStatus(id, ApprovalStatus.APPROVED, reviewerNotes);
        return "redirect:/service-owner/service-requests";
    }

    @PostMapping("/service-owner/service-requests/{id}/reject")
    public String reject(@PathVariable Long id, @RequestParam(required = false) String reviewerNotes) {
        updateStatus(id, ApprovalStatus.REJECTED, reviewerNotes);
        return "redirect:/service-owner/service-requests";
    }

    private void updateStatus(Long id, ApprovalStatus status, String notes) {
        serviceRequestService.findById(id).ifPresent(request -> {
            request.setStatus(status);
            request.setReviewerNotes(notes);
            serviceRequestService.save(request);
        });
    }
}
