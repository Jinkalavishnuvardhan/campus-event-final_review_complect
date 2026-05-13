package com.example.campus.repository;

import com.example.campus.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findFirstByUsernameAndPassword(String username, String password);
}
