package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.HomePageConfig;
import com.iti.aiplayground.service.HomePageConfigService;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final HomePageConfigService homePageConfigService;

    public HomeController(HomePageConfigService homePageConfigService) {
        this.homePageConfigService = homePageConfigService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Optional<HomePageConfig> config = homePageConfigService.getLatestConfig();
        model.addAttribute("config", config.orElseGet(HomePageConfig::new));
        model.addAttribute("hasConfig", config.isPresent());
        return "home";
    }
}
