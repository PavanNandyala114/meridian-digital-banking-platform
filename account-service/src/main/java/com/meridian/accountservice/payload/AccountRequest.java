package com.meridian.accountservice.payload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Account type is required")
    private String accountType;

    @NotNull(message = "Currency code is required")
    private String currencyCode;

    @NotNull(message = "Available balance is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Available balance must be non-negative")
    private BigDecimal availableBalance;

    @NotNull(message = "Account status is required")
    private String accountStatus;

    @NotNull(message = "Branch code is required")
    private String branchCode;

    @NotNull(message = "Account holder name is required")
    private String accountHolderName;
}
