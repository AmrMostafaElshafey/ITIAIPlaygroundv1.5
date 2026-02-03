package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.HomePageConfig;
import com.iti.aiplayground.service.AiServiceService;
import com.iti.aiplayground.service.HomePageConfigService;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final HomePageConfigService homePageConfigService;
    private final AiServiceService aiServiceService;

    public HomeController(HomePageConfigService homePageConfigService, AiServiceService aiServiceService) {
        this.homePageConfigService = homePageConfigService;
        this.aiServiceService = aiServiceService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Optional<HomePageConfig> config = homePageConfigService.getLatestConfig();
        model.addAttribute("config", config.orElseGet(HomePageConfig::new));
        model.addAttribute("hasConfig", config.isPresent());
        model.addAttribute("services", aiServiceService.findAll());
        return "home";
    }
}
