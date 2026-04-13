package com.meridian.accountservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType;

    @Column(nullable = false, length = 5)
    private String currencyCode;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal availableBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus accountStatus;

    @Column(nullable = false, length = 20)
    private String branchCode;

    @Column(nullable = false, length = 50)
    private String accountHolderName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime openedAt;

    private LocalDateTime updatedAt;

    private LocalDateTime closedAt;

    @PrePersist
    public void prePersist() {
        this.openedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.closedAt = null;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum AccountType {
        SAVINGS,
        CURRENT,
        FIXED_DEPOSIT,
        LOAN,
        SALARY
    }
    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        CLOSED,
        BLOCKED
    }

}
