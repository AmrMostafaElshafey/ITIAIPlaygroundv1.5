package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.AdminUser;
import com.iti.aiplayground.model.AiService;
import com.iti.aiplayground.model.LearningDepartment;
import com.iti.aiplayground.model.Policy;
import com.iti.aiplayground.model.PromptLibraryItem;
import com.iti.aiplayground.model.PromptLibraryOverview;
import com.iti.aiplayground.model.ServiceType;
import com.iti.aiplayground.model.SystemRole;
import com.iti.aiplayground.service.AdminUserService;
import com.iti.aiplayground.service.AiServiceService;
import com.iti.aiplayground.service.LearningDepartmentService;
import com.iti.aiplayground.service.PolicyService;
import com.iti.aiplayground.service.PromptLibraryOverviewService;
import com.iti.aiplayground.service.PromptLibraryService;
import com.iti.aiplayground.service.ServiceTypeService;
import com.iti.aiplayground.service.UploadStorageService;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/setup/install")
public class InstallWizardController {
    private final AdminUserService adminUserService;
    private final ServiceTypeService serviceTypeService;
    private final AiServiceService aiServiceService;
    private final PolicyService policyService;
    private final LearningDepartmentService learningDepartmentService;
    private final PromptLibraryService promptLibraryService;
    private final PromptLibraryOverviewService promptLibraryOverviewService;
    private final UploadStorageService uploadStorageService;

    public InstallWizardController(AdminUserService adminUserService,
                                   ServiceTypeService serviceTypeService,
                                   AiServiceService aiServiceService,
                                   PolicyService policyService,
                                   LearningDepartmentService learningDepartmentService,
                                   PromptLibraryService promptLibraryService,
                                   PromptLibraryOverviewService promptLibraryOverviewService,
                                   UploadStorageService uploadStorageService) {
        this.adminUserService = adminUserService;
        this.serviceTypeService = serviceTypeService;
        this.aiServiceService = aiServiceService;
        this.policyService = policyService;
        this.learningDepartmentService = learningDepartmentService;
        this.promptLibraryService = promptLibraryService;
        this.promptLibraryOverviewService = promptLibraryOverviewService;
        this.uploadStorageService = uploadStorageService;
    }

    @GetMapping("/users")
    public String users(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("adminUser", new AdminUser());
        model.addAttribute("roles", SystemRole.values());
        model.addAttribute("users", adminUserService.findAll());
        return "setup-users";
    }

    @PostMapping("/users")
    public String addUser(AdminUser adminUser, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        adminUserService.save(adminUser);
        return "redirect:/setup/install/users";
    }

    @GetMapping("/policy")
    public String policy(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("policy", policyService.getLatest().orElseGet(Policy::new));
        return "setup-policy";
    }

    @GetMapping("/service-types")
    public String serviceTypes(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("serviceType", new ServiceType());
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        return "setup-service-types";
    }

    @PostMapping("/service-types")
    public String addServiceType(ServiceType serviceType, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        serviceTypeService.save(serviceType);
        return "redirect:/setup/install/service-types";
    }

    @PostMapping("/policy")
    public String savePolicy(Policy policy,
                             @RequestParam(required = false) MultipartFile policyFile,
                             HttpSession session) throws IOException {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        if (policyFile != null && !policyFile.isEmpty()) {
            policy.setFilePath(uploadStorageService.store(policyFile));
        }
        policyService.save(policy);
        return "redirect:/setup/install/service-types";
    }

    @GetMapping("/services")
    public String services(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("service", new AiService());
        model.addAttribute("services", aiServiceService.findAll());
        model.addAttribute("owners", adminUserService.findAll());
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        return "setup-services-step";
    }

    @PostMapping("/services")
    public String addService(AiService service,
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
            return "setup-services-step";
        }
        service.setOwner(adminUserService.findById(ownerId).orElse(null));
        service.setServiceType(serviceTypeService.findById(serviceTypeId).orElse(null));
        aiServiceService.save(service);
        return "redirect:/setup/install/services";
    }

    @GetMapping("/prompts")
    public String prompts(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("department", new LearningDepartment());
        model.addAttribute("prompt", new PromptLibraryItem());
        model.addAttribute("promptOverview", promptLibraryOverviewService.getLatest().orElseGet(PromptLibraryOverview::new));
        model.addAttribute("departments", learningDepartmentService.findAll());
        model.addAttribute("prompts", promptLibraryService.findAll());
        return "setup-prompts";
    }

    @PostMapping("/departments")
    public String addDepartment(LearningDepartment department, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        learningDepartmentService.save(department);
        return "redirect:/setup/install/prompts";
    }

    @PostMapping("/prompts")
    public String addPrompt(@RequestParam Long departmentId, PromptLibraryItem prompt, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        learningDepartmentService.findById(departmentId).ifPresent(prompt::setDepartment);
        promptLibraryService.save(prompt);
        return "redirect:/setup/install/prompts";
    }

    @PostMapping("/prompt-overview")
    public String savePromptOverview(PromptLibraryOverview overview, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        promptLibraryOverviewService.save(overview);
        return "redirect:/setup/install/prompts";
    }
}
