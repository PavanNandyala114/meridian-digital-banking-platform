package com.meridian.customerservice.controller;

import com.meridian.customerservice.payload.ApiResponse;
import com.meridian.customerservice.payload.CustomerRequest;
import com.meridian.customerservice.payload.CustomerResponse;
import com.meridian.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ApiResponse.<CustomerResponse>builder()
                .success(true)
                .message("Customer created successfully")
                .data(response)
                .build();
    }


    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();

    }
    @GetMapping("/{Id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Long Id){
        return ApiResponse.<CustomerResponse>builder()
                .success(true)
                .message("Customer retrieved successfully")
                .data(customerService.getCustomerById(Id))
                .build();
        }

    @GetMapping("/code/{customerCode}")
    public ApiResponse<CustomerResponse> getCustomerByCode(@PathVariable String customerCode){
        return ApiResponse.<CustomerResponse>builder()
                .success(true)
                .message("Customer retrieved successfully")
                .data(customerService.getCustomerByCode(customerCode))
                .build();
    }

    @PutMapping("/{Id}")
    public ApiResponse<CustomerResponse> updateCustomer(@PathVariable Long Id, @Valid @RequestBody CustomerRequest request){
        return ApiResponse.<CustomerResponse>builder()
                .success(true)
                .message("Customer updated successfully")
                .data(customerService.updateCustomer(Id, request))
                .build();
    }

    @DeleteMapping("/{Id}")
    public ApiResponse<CustomerResponse> deleteCustomer(@PathVariable Long Id){
        CustomerResponse deleteCustomer = customerService.deleteCustomer(Id);
        return ApiResponse.<CustomerResponse>builder()
                .success(true)
                .message("Customer deleted successfully")
                .data(deleteCustomer)
                .build();
    }

}
