package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.HomePageConfig;
import com.iti.aiplayground.model.Policy;
import com.iti.aiplayground.service.AiServiceService;
import com.iti.aiplayground.service.HomePageConfigService;
import com.iti.aiplayground.service.PolicyService;
import com.iti.aiplayground.service.PromptLibraryService;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final HomePageConfigService homePageConfigService;
    private final AiServiceService aiServiceService;
    private final PolicyService policyService;
    private final PromptLibraryService promptLibraryService;

    public HomeController(HomePageConfigService homePageConfigService,
                          AiServiceService aiServiceService,
                          PolicyService policyService,
                          PromptLibraryService promptLibraryService) {
        this.homePageConfigService = homePageConfigService;
        this.aiServiceService = aiServiceService;
        this.policyService = policyService;
        this.promptLibraryService = promptLibraryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Optional<HomePageConfig> config = homePageConfigService.getLatestConfig();
        model.addAttribute("config", config.orElseGet(HomePageConfig::new));
        model.addAttribute("hasConfig", config.isPresent());
        model.addAttribute("services", aiServiceService.findPublished());
        model.addAttribute("policy", policyService.getLatest().orElseGet(Policy::new));
        model.addAttribute("prompts", promptLibraryService.findFeatured());
        return "home";
    }
}
