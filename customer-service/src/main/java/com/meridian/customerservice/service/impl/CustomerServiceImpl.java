package com.meridian.customerservice.service.impl;

import com.meridian.customerservice.exception.DuplicateResourceException;
import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.meridian.customerservice.entity.Customer;
import com.meridian.customerservice.exception.ResourceNotFoundException;
import com.meridian.customerservice.payload.CustomerRequest;
import com.meridian.customerservice.payload.CustomerResponse;
import com.meridian.customerservice.repository.CustomerRepository;
import com.meridian.customerservice.service.CustomerService;
import com.meridian.customerservice.utility.CustomerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

   // private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        log.info("Creating new customer with code: " + request.getCustomerCode() + " and email: " + request.getEmail() + " and mobile number: " + request.getMobileNumber() + "");

        validateDuplicateCustomer(request);

        Customer customer = Customer.builder()
                .customerCode(request.getCustomerCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mobileNumber(request.getMobileNumber())
                .email(request.getEmail())
                .kycStatus(Customer.KycStatus.valueOf(request.getKycStatus().toUpperCase()))
                .gender(Customer.Gender.valueOf(request.getGender().toUpperCase()))
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .customerType(CustomerUtil.determineCustomerType(request.getDateOfBirth()))
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created with ID: " + savedCustomer.getCustomerId());
        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        return mapToResponse(customer);

    }

    @Override
    public CustomerResponse getCustomerByCode(String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with code: " + customerCode));
        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        if(!customer.getCustomerCode().equals(request.getCustomerCode())
                && customerRepository.existsByCustomerCode(request.getCustomerCode())) {
            throw new DuplicateResourceException("Customer code already exists");
        }
        if(!customer.getMobileNumber().equals(request.getMobileNumber())
                && customerRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }
        if(request.getEmail() != null
                && !request.getEmail().equals(customer.getEmail())
                && customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

            customer.setCustomerCode(request.getCustomerCode());
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setMobileNumber(request.getMobileNumber());
            customer.setEmail(request.getEmail());
            customer.setKycStatus(Customer.KycStatus.valueOf(request.getKycStatus().toUpperCase()));
            customer.setGender(Customer.Gender.valueOf(request.getGender().toUpperCase()));
            customer.setDateOfBirth(request.getDateOfBirth());
            customer.setAddress(request.getAddress());
            customer.setCustomerType(CustomerUtil.determineCustomerType(request.getDateOfBirth()));
//            customer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer updated successfully with ID: " + updatedCustomer.getCustomerId() + " and code: " + updatedCustomer.getCustomerCode() + "");
        return mapToResponse(updatedCustomer);

        }

    @Override
    public CustomerResponse deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        CustomerResponse response = mapToResponse(customer);
        customerRepository.delete(customer);
        log.info("Customer deleted successfully with ID: " + customerId + "");
        return response;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validateDuplicateCustomer(CustomerRequest request) {
        if(customerRepository.existsByCustomerCode(request.getCustomerCode())) {
            throw new DuplicateResourceException("Customer code already exists");
        }
        if(customerRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }
        if(request.getEmail() != null && customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerCode(customer.getCustomerCode())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .mobileNumber(customer.getMobileNumber())
                .email(customer.getEmail())
                .kycStatus(customer.getKycStatus().name())
                .gender(customer.getGender().name())
                .dateOfBirth(customer.getDateOfBirth())
                .age(CustomerUtil.calculateAge(customer.getDateOfBirth()))
                .address(customer.getAddress())
                .customerType(customer.getCustomerType())
//                .createdAt(customer.getCreatedAt())
//                .updatedAt(customer.getUpdatedAt())
                .build();
    }



}
