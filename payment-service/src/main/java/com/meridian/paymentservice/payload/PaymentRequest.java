package com.meridian.paymentservice.payload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    @NotBlank(message = "Transaction reference is required")
    private String transactionReference;

    @NotNull(message = "Source account ID is required")
    private Long sourceAccountId;

    @NotNull(message = "Destination account ID is required")
    private Long destinationAccountId;

    @NotNull(message = "Initiated by customer ID is required")
    private Long initiatedByCustomerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Currency code is required")
    private String currencyCode;

    @NotNull(message = "Payment type is required")
    private String paymentType;

    @NotNull(message = "Payment status is required")
    private String paymentStatus;

    @NotNull(message = "Transaction type is required")
    private String transactionType;

    private String remarks;
}
