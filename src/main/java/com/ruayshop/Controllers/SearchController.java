package com.ruayshop.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruayshop.Entities.Motorcycle;
import com.ruayshop.Repositories.CustomerRepository;
import com.ruayshop.Repositories.MotorcycleRepository;
import com.ruayshop.Entities.Customer;



@RestController
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    // Endpoint for searching customers by first and last name
    @GetMapping("/search/customers")
    public List<Customer> searchCustomers(@RequestParam("query") String query) {
        return customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
    }

    // Endpoint for searching motorcycles by model
    @GetMapping("/search/motorcycles")
    public List<Motorcycle> searchMotorcycles(@RequestParam("query") String query) {
        return motorcycleRepository.findByModelContainingIgnoreCase(query);
    }
}

