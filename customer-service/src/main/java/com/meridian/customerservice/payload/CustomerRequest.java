package com.meridian.customerservice.payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {

    @NotBlank(message = "Customer code is required")
    private String customerCode;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address is required")
    private String kycStatus;

    @NotBlank(message = "Gender is Required")
    private String gender;

    @NotNull(message = "Date of Birth is required")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    private String address;
}
