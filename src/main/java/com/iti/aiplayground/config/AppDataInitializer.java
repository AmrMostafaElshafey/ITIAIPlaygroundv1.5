package com.iti.aiplayground.config;

import com.iti.aiplayground.model.AdminUser;
import com.iti.aiplayground.model.SystemRole;
import com.iti.aiplayground.service.AdminUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppDataInitializer implements CommandLineRunner {
    private static final String ADMIN_EMAIL = "admin@iti";
    private static final String ADMIN_PASSWORD = "Admin@ITI@2026";

    private final AdminUserService adminUserService;

    public AppDataInitializer(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @Override
    public void run(String... args) {
        if (adminUserService.findByEmail(ADMIN_EMAIL).isPresent()) {
            return;
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setFullName("System Admin");
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setPassword(ADMIN_PASSWORD);
        adminUser.setRole(SystemRole.ADMIN);
        adminUserService.save(adminUser);
    }
}
