package com.meridian.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meridian.customerservice.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerCode(String customerCode);

    boolean existsByCustomerCode(String customerCode);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);


}

