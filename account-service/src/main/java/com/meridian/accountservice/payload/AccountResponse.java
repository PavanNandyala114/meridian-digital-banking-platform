package com.meridian.accountservice.payload;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountResponse {

       private Long accountId;
       private Long customerId;
       private String accountNumber;
       private String accountType;
       private String currencyCode;
       private String availableBalance;
       private String accountStatus;
       private String branchCode;
       private String accountHolderName;
       private LocalDateTime openedAt;
       private LocalDateTime updatedAt;
       private LocalDateTime closedAt;


}
