package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.AiService;
import com.iti.aiplayground.service.AiServiceService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/services")
public class AdminServiceController {
    private final AiServiceService aiServiceService;

    public AdminServiceController(AiServiceService aiServiceService) {
        this.aiServiceService = aiServiceService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("services", aiServiceService.findAll());
        return "admin/services";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("service", new AiService());
        return "admin/service-form";
    }

    @PostMapping
    public String create(@Valid AiService service, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/service-form";
        }
        aiServiceService.save(service);
        return "redirect:/admin/services";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<AiService> service = aiServiceService.findById(id);
        if (service.isEmpty()) {
            return "redirect:/admin/services";
        }
        model.addAttribute("service", service.get());
        return "admin/service-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid AiService service, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/service-form";
        }
        Optional<AiService> existing = aiServiceService.findById(id);
        if (existing.isEmpty()) {
            return "redirect:/admin/services";
        }
        AiService stored = existing.get();
        stored.setName(service.getName());
        stored.setOwnerName(service.getOwnerName());
        stored.setCategory(service.getCategory());
        stored.setStatus(service.getStatus());
        stored.setDescription(service.getDescription());
        aiServiceService.save(stored);
        return "redirect:/admin/services";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        aiServiceService.softDelete(id);
        return "redirect:/admin/services";
    }
}
