package com.meridian.paymentservice.payload.external;

import lombok.Data;

@Data
public class ExternalCustomerResponse {

        private Long customerId;
        private String customerCode;
        private String firstName;
        private String lastName;
        private String mobileNumber;
        private String email;
        private String kycStatus;
        private String gender;
        private String dateOfBirth;
        private String address;
        private String customerType;
        private String createdAt;
        private String updatedAt;


}
