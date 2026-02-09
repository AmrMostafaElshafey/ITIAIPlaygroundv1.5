package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.AdminUser;
import com.iti.aiplayground.model.AiService;
import com.iti.aiplayground.model.LearningDepartment;
import com.iti.aiplayground.model.Policy;
import com.iti.aiplayground.model.PromptLibraryItem;
import com.iti.aiplayground.model.ServiceType;
import com.iti.aiplayground.model.SystemRole;
import com.iti.aiplayground.service.AdminUserService;
import com.iti.aiplayground.service.AiServiceService;
import com.iti.aiplayground.service.LearningDepartmentService;
import com.iti.aiplayground.service.PolicyService;
import com.iti.aiplayground.service.PromptLibraryService;
import com.iti.aiplayground.service.ServiceTypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/setup/install")
public class InstallWizardController {
    private final AdminUserService adminUserService;
    private final ServiceTypeService serviceTypeService;
    private final AiServiceService aiServiceService;
    private final PolicyService policyService;
    private final LearningDepartmentService learningDepartmentService;
    private final PromptLibraryService promptLibraryService;

    public InstallWizardController(AdminUserService adminUserService,
                                   ServiceTypeService serviceTypeService,
                                   AiServiceService aiServiceService,
                                   PolicyService policyService,
                                   LearningDepartmentService learningDepartmentService,
                                   PromptLibraryService promptLibraryService) {
        this.adminUserService = adminUserService;
        this.serviceTypeService = serviceTypeService;
        this.aiServiceService = aiServiceService;
        this.policyService = policyService;
        this.learningDepartmentService = learningDepartmentService;
        this.promptLibraryService = promptLibraryService;
    }

    @GetMapping
    public String wizard(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("adminUser", new AdminUser());
        model.addAttribute("serviceType", new ServiceType());
        model.addAttribute("service", new AiService());
        model.addAttribute("policy", policyService.getLatest().orElseGet(Policy::new));
        model.addAttribute("department", new LearningDepartment());
        model.addAttribute("prompt", new PromptLibraryItem());
        model.addAttribute("roles", SystemRole.values());
        model.addAttribute("users", adminUserService.findAll());
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        model.addAttribute("services", aiServiceService.findAll());
        model.addAttribute("departments", learningDepartmentService.findAll());
        model.addAttribute("prompts", promptLibraryService.findAll());
        return "setup-install";
    }

    @PostMapping("/users")
    public String addUser(AdminUser adminUser, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        adminUserService.save(adminUser);
        return "redirect:/setup/install";
    }

    @PostMapping("/service-types")
    public String addServiceType(ServiceType serviceType, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        serviceTypeService.save(serviceType);
        return "redirect:/setup/install";
    }

    @PostMapping("/services")
    public String addService(AiService service, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        aiServiceService.save(service);
        return "redirect:/setup/install";
    }

    @PostMapping("/policy")
    public String savePolicy(Policy policy, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        policyService.save(policy);
        return "redirect:/setup/install";
    }

    @PostMapping("/departments")
    public String addDepartment(LearningDepartment department, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        learningDepartmentService.save(department);
        return "redirect:/setup/install";
    }

    @PostMapping("/prompts")
    public String addPrompt(@RequestParam Long departmentId, PromptLibraryItem prompt, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        learningDepartmentService.findById(departmentId).ifPresent(prompt::setDepartment);
        promptLibraryService.save(prompt);
        return "redirect:/setup/install";
    }
}
