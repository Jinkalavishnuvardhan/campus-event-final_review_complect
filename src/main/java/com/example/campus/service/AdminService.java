package com.example.campus.service;

import com.example.campus.entity.Admin;
import com.example.campus.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin login(String username, String password) {
        return adminRepository.findFirstByUsernameAndPassword(username, password);
    }

    public void register(Admin admin) {
        adminRepository.save(admin);
    }
}
