package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.AiService;
import com.iti.aiplayground.service.AdminUserService;
import com.iti.aiplayground.service.AiServiceService;
import com.iti.aiplayground.service.ServiceTypeService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/services")
public class AdminServiceController {
    private final AiServiceService aiServiceService;
    private final AdminUserService adminUserService;
    private final ServiceTypeService serviceTypeService;

    public AdminServiceController(AiServiceService aiServiceService,
                                  AdminUserService adminUserService,
                                  ServiceTypeService serviceTypeService) {
        this.aiServiceService = aiServiceService;
        this.adminUserService = adminUserService;
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("services", aiServiceService.findAll());
        return "admin/services";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("service", new AiService());
        model.addAttribute("owners", adminUserService.findAll());
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        return "admin/service-form";
    }

    @PostMapping
    public String createWithLists(@Valid AiService service,
                                  BindingResult result,
                                  @RequestParam(required = false) Long ownerId,
                                  @RequestParam(required = false) Long serviceTypeId,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("owners", adminUserService.findAll());
            model.addAttribute("serviceTypes", serviceTypeService.findAll());
            return "admin/service-form";
        }
        if (ownerId == null || serviceTypeId == null) {
            model.addAttribute("owners", adminUserService.findAll());
            model.addAttribute("serviceTypes", serviceTypeService.findAll());
            return "admin/service-form";
        }
        service.setOwner(adminUserService.findById(ownerId).orElse(null));
        service.setServiceType(serviceTypeService.findById(serviceTypeId).orElse(null));
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
        model.addAttribute("owners", adminUserService.findAll());
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        return "admin/service-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid AiService service,
                         BindingResult result,
                         @RequestParam(required = false) Long ownerId,
                         @RequestParam(required = false) Long serviceTypeId,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("owners", adminUserService.findAll());
            model.addAttribute("serviceTypes", serviceTypeService.findAll());
            return "admin/service-form";
        }
        if (ownerId == null || serviceTypeId == null) {
            model.addAttribute("owners", adminUserService.findAll());
            model.addAttribute("serviceTypes", serviceTypeService.findAll());
            return "admin/service-form";
        }
        Optional<AiService> existing = aiServiceService.findById(id);
        if (existing.isEmpty()) {
            return "redirect:/admin/services";
        }
        AiService stored = existing.get();
        stored.setName(service.getName());
        stored.setOwner(adminUserService.findById(ownerId).orElse(null));
        stored.setServiceType(serviceTypeService.findById(serviceTypeId).orElse(null));
        stored.setStatus(service.getStatus());
        stored.setDescription(service.getDescription());
        stored.setEligibility(service.getEligibility());
        stored.setPublishOnHome(service.isPublishOnHome());
        aiServiceService.save(stored);
        return "redirect:/admin/services";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        aiServiceService.softDelete(id);
        return "redirect:/admin/services";
    }
}
