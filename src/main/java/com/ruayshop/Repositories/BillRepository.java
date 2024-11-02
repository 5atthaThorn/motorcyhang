package com.ruayshop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruayshop.Entities.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {

}
