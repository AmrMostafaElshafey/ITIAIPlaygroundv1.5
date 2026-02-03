package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.HomePageConfig;
import com.iti.aiplayground.service.HomePageConfigService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setup")
public class SetupWizardController {
    private final HomePageConfigService homePageConfigService;

    public SetupWizardController(HomePageConfigService homePageConfigService) {
        this.homePageConfigService = homePageConfigService;
    }

    @GetMapping
    public String showWizard(Model model) {
        HomePageConfig config = homePageConfigService.getLatestConfig().orElseGet(HomePageConfig::new);
        model.addAttribute("config", config);
        return "setup";
    }

    @PostMapping
    public String saveWizard(@Valid @ModelAttribute("config") HomePageConfig config, BindingResult result) {
        if (result.hasErrors()) {
            return "setup";
        }
        homePageConfigService.save(config);
        return "redirect:/";
    }
}
