package com.meridian.customerservice.service;
import com.meridian.customerservice.payload.CustomerRequest;

import java.util.List;
import com.meridian.customerservice.payload.CustomerResponse;

public interface CustomerService {

        CustomerResponse createCustomer(CustomerRequest request);
        CustomerResponse getCustomerById(Long customerId);
        CustomerResponse getCustomerByCode(String customerCode);
        CustomerResponse updateCustomer(Long customerId, CustomerRequest request);
        CustomerResponse deleteCustomer(Long customerId);

        List<CustomerResponse> getAllCustomers();
}
