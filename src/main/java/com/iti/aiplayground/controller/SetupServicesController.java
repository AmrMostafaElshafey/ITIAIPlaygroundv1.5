package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.AiService;
import com.iti.aiplayground.service.AdminUserService;
import com.iti.aiplayground.service.AiServiceService;
import com.iti.aiplayground.service.ServiceTypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/setup/services")
public class SetupServicesController {
    private final AiServiceService aiServiceService;
    private final AdminUserService adminUserService;
    private final ServiceTypeService serviceTypeService;

    public SetupServicesController(AiServiceService aiServiceService,
                                   AdminUserService adminUserService,
                                   ServiceTypeService serviceTypeService) {
        this.aiServiceService = aiServiceService;
        this.adminUserService = adminUserService;
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping
    public String show(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("service", new AiService());
        model.addAttribute("services", aiServiceService.findAll());
        model.addAttribute("owners", adminUserService.findAll());
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        return "setup-services";
    }

    @PostMapping
    public String add(AiService service,
                      @RequestParam(required = false) Long ownerId,
                      @RequestParam(required = false) Long serviceTypeId,
                      HttpSession session,
                      Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        if (ownerId == null || serviceTypeId == null) {
            model.addAttribute("service", service);
            model.addAttribute("services", aiServiceService.findAll());
            model.addAttribute("owners", adminUserService.findAll());
            model.addAttribute("serviceTypes", serviceTypeService.findAll());
            return "setup-services";
        }
        service.setOwner(adminUserService.findById(ownerId).orElse(null));
        service.setServiceType(serviceTypeService.findById(serviceTypeId).orElse(null));
        aiServiceService.save(service);
        return "redirect:/setup/services";
    }
}
