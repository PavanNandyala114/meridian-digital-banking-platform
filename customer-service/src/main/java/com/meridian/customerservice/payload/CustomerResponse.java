package com.meridian.customerservice.payload;

import com.meridian.customerservice.entity.Customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CustomerResponse {

    private Long customerId;
    private String customerCode;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String kycStatus;
    private String gender;
    private LocalDate dateOfBirth;
    private int age;
    private String address;
    private String customerType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
