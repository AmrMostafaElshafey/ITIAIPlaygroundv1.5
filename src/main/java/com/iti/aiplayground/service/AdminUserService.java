package com.iti.aiplayground.service;

import com.iti.aiplayground.model.AdminUser;
import com.iti.aiplayground.repository.AdminUserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {
    private final AdminUserRepository repository;

    public AdminUserService(AdminUserRepository repository) {
        this.repository = repository;
    }

    public AdminUser save(AdminUser user) {
        return repository.save(user);
    }

    public List<AdminUser> findAll() {
        return repository.findAll();
    }

    public Optional<AdminUser> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<AdminUser> findById(Long id) {
        return repository.findById(id);
    }
}
