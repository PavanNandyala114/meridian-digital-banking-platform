package com.meridian.paymentservice.payload;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long transactionId;
    private String transactionReference;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Long initiatedByCustomerId;
    private BigDecimal amount;
    private String currencyCode;
    private String paymentType;
    private String paymentStatus;
    private String transactionType;
    private String remarks;
    private LocalDateTime initiatedAt;
    private LocalDateTime completedAt;
    private LocalDateTime updatedAt;

}
