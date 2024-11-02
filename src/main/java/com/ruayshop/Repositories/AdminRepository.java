package com.ruayshop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruayshop.Entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByUsername(String username);
}
