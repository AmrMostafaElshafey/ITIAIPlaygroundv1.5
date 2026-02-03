package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.AiService;
import com.iti.aiplayground.service.AiServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setup/services")
public class SetupServicesController {
    private final AiServiceService aiServiceService;

    public SetupServicesController(AiServiceService aiServiceService) {
        this.aiServiceService = aiServiceService;
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute("service", new AiService());
        model.addAttribute("services", aiServiceService.findAll());
        return "setup-services";
    }

    @PostMapping
    public String add(AiService service) {
        aiServiceService.save(service);
        return "redirect:/setup/services";
    }
}
