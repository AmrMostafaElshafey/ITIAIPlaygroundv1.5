package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.RegistrationRequest;
import com.iti.aiplayground.model.UserRole;
import com.iti.aiplayground.service.RegistrationRequestService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final RegistrationRequestService registrationRequestService;

    public LoginController(RegistrationRequestService registrationRequestService) {
        this.registrationRequestService = registrationRequestService;
    }
    @GetMapping
    public String login(@RequestParam(required = false) String redirect, HttpSession session, Model model) {
        if (redirect != null) {
            session.setAttribute("loginRedirect", redirect);
        }
        model.addAttribute("roles", new String[]{"STUDENT", "STAFF", "ADMIN", "SERVICE_OWNER", "APPROVER"});
        return "login";
    }

    @PostMapping
    public String submit(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String role,
                         HttpSession session) {
        session.setAttribute("pendingLoginRole", role);
        session.setAttribute("loginEmail", email);
        return "redirect:/login/verify";
    }

    @GetMapping("/verify")
    public String verify(HttpSession session, Model model) {
        String role = (String) session.getAttribute("pendingLoginRole");
        String email = (String) session.getAttribute("loginEmail");
        if (role == null || email == null) {
            model.addAttribute("error", "Please submit your login details again.");
            model.addAttribute("roles", new String[]{"STUDENT", "STAFF", "ADMIN", "SERVICE_OWNER", "APPROVER"});
            return "login";
        }
        if (role.equals("STUDENT") || role.equals("STAFF")) {
            Optional<RegistrationRequest> approved = registrationRequestService.findApprovedByEmailAndRole(email,
                    UserRole.valueOf(role));
            if (approved.isEmpty()) {
                model.addAttribute("error", "Your registration is not approved yet.");
                model.addAttribute("roles", new String[]{"STUDENT", "STAFF", "ADMIN", "SERVICE_OWNER", "APPROVER"});
                return "login";
            }
        }
        session.setAttribute("loggedIn", true);
        session.setAttribute("userEmail", email);
        session.setAttribute("userRole", role);
        String redirect = (String) session.getAttribute("loginRedirect");
        session.removeAttribute("loginRedirect");
        session.removeAttribute("pendingLoginRole");
        session.removeAttribute("loginEmail");
        return "redirect:" + (redirect != null ? redirect : "/");
    }
}
