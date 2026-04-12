package com.meridian.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, unique = true, length = 30)
    private String customerCode;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 20)
    private String mobileNumber;

    @Column(unique = true, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private KycStatus kycStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 20)
    private String customerType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public enum KycStatus {
        PENDING,
        VERIFIED,
        REJECTED
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

}
