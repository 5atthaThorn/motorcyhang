package com.ruayshop.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruayshop.Entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
