package com.iti.aiplayground.controller;

import com.iti.aiplayground.model.RegistrationRequest;
import com.iti.aiplayground.model.UserRole;
import com.iti.aiplayground.service.RegistrationRequestService;
import com.iti.aiplayground.service.UploadStorageService;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationRequestService registrationRequestService;
    private final UploadStorageService uploadStorageService;

    public RegistrationController(RegistrationRequestService registrationRequestService,
                                  UploadStorageService uploadStorageService) {
        this.registrationRequestService = registrationRequestService;
        this.uploadStorageService = uploadStorageService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("request", new RegistrationRequest());
        model.addAttribute("roles", UserRole.values());
        return "register";
    }

    @PostMapping
    public String submit(RegistrationRequest request,
                         @RequestParam("photoFile") MultipartFile photoFile,
                         @RequestParam("workIdFile") MultipartFile workIdFile,
                         Model model) throws IOException {
        request.setPhotoPath(uploadStorageService.store(photoFile));
        request.setWorkIdPath(uploadStorageService.store(workIdFile));
        registrationRequestService.save(request);
        model.addAttribute("submitted", true);
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("request", new RegistrationRequest());
        return "register";
    }
}
