package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.model.RegistrationRequest;
import com.iti.aiplayground.model.ServiceRequest;
import com.iti.aiplayground.service.RegistrationRequestService;
import com.iti.aiplayground.service.ServiceRequestService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
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
    public String newRequest(@RequestParam(required = false) String serviceName, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("loggedIn"))) {
            String redirect = "/service-requests/new" + (serviceName != null ? "?serviceName=" + serviceName : "");
            return "redirect:/login?redirect=" + redirect;
        }
        model.addAttribute("request", new ServiceRequest());
        model.addAttribute("approvedRegistrations", registrationRequestService.findApproved());
        model.addAttribute("serviceName", serviceName);
        return "service-request-form";
    }

    @PostMapping("/service-requests")
    public String submitRequest(@RequestParam Long registrationId,
                                @RequestParam String serviceName,
                                @RequestParam String requestNotes,
                                @RequestParam String accessLimits,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                Model model,
                                HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("loggedIn"))) {
            String redirect = "/service-requests/new" + (serviceName != null ? "?serviceName=" + serviceName : "");
            return "redirect:/login?redirect=" + redirect;
        }
        Optional<RegistrationRequest> registrationRequest = registrationRequestService.findById(registrationId);
        if (registrationRequest.isEmpty()) {
            model.addAttribute("error", "Registration record not found.");
            return newRequest(null, model, session);
        }
        ServiceRequest request = new ServiceRequest();
        request.setRegistrationRequest(registrationRequest.get());
        request.setServiceName(serviceName);
        request.setRequestNotes(requestNotes);
        request.setAccessLimits(accessLimits);
        request.setStartDate(LocalDate.parse(startDate));
        request.setEndDate(LocalDate.parse(endDate));
        request.setRequestDate(LocalDate.now());
        serviceRequestService.save(request);
        model.addAttribute("submitted", true);
        return newRequest(serviceName, model, session);
    }

    @GetMapping("/admin/service-requests")
    public String adminQueue(Model model, HttpSession session) {
        if (!isRole(session, "ADMIN")) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Admin Service Requests");
        model.addAttribute("requests", serviceRequestService.findAllOrdered());
        model.addAttribute("basePath", "/admin/service-requests");
        return "service-request-review";
    }

    @GetMapping("/approver/service-requests")
    public String approverQueue(Model model, HttpSession session) {
        if (!isRole(session, "APPROVER")) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Approver Service Requests");
        model.addAttribute("requests", serviceRequestService.findAllOrdered());
        model.addAttribute("basePath", "/approver/service-requests");
        return "service-request-review";
    }

    @PostMapping("{base}/service-requests/{id}/approve")
    public String approve(@PathVariable String base, @PathVariable Long id, @RequestParam(required = false) String reviewerNotes) {
        updateStatus(id, ApprovalStatus.APPROVED, reviewerNotes);
        return "redirect:/" + base + "/service-requests";
    }

    @PostMapping("{base}/service-requests/{id}/reject")
    public String reject(@PathVariable String base, @PathVariable Long id, @RequestParam(required = false) String reviewerNotes) {
        updateStatus(id, ApprovalStatus.REJECTED, reviewerNotes);
        return "redirect:/" + base + "/service-requests";
    }

    private void updateStatus(Long id, ApprovalStatus status, String notes) {
        serviceRequestService.findById(id).ifPresent(request -> {
            request.setStatus(status);
            request.setReviewerNotes(notes);
            serviceRequestService.save(request);
        });
    }

    private boolean isRole(HttpSession session, String role) {
        return role.equals(session.getAttribute("userRole"));
    }
}
