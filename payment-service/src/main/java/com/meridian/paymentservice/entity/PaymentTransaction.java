package com.meridian.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false, unique = true, length = 50)
    private String transactionReference;

    @Column(nullable = false)
    private Long sourceAccountId;

    @Column(nullable = false)
    private Long destinationAccountId;

    @Column(nullable = false)
    private Long initiatedByCustomerId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 5)
    private String currencyCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType transactionType;

    @Column(length = 255)
    private String remarks;

    @Column(nullable = false, updatable = false)
    private LocalDateTime initiatedAt;

    private LocalDateTime completedAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.initiatedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum PaymentStatus {
        INITIATED,
        PENDING,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    public enum PaymentType {
        INTRA_BANK_TRANSFER,
        INTERBANK_OUTGOING_TRANSFER,
        INTERBANK_INCOMING_TRANSFER
    }

    public enum TransactionType {

        TRANSFER,
        PAYMENT,
        DEPOSIT,
        WITHDRAWAL
    }

}
