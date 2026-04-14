package com.meridian.paymentservice.payload.external;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExternalAccountResponse {

    private Long accountId;
    private String accountNumber;
    private String accountType;
    private String currencyCode;
    private BigDecimal availableBalance;
    private String accountStatus;
    private Long customerId;
    private String branchCode;
    private String accountHolderName;
    private String openedAt;
    private String updatedAt;
    private String closedAt;

}
