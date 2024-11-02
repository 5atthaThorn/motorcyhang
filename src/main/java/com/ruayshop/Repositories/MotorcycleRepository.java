package com.ruayshop.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruayshop.Entities.Motorcycle;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {
    List<Motorcycle> findByModelContainingIgnoreCase(String model);
}
