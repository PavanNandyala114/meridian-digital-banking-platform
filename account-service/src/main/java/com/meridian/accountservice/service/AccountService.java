package com.meridian.accountservice.service;

import com.meridian.accountservice.payload.AccountRequest;
import com.meridian.accountservice.payload.AccountResponse;

import java.util.List;

public interface AccountService {

        AccountResponse createAccount(AccountRequest request);
        AccountResponse getAccountById(Long accountId);

        AccountResponse getAccountByAccountNumber(String accountNumber);
        List<AccountResponse> getAccountsByCustomerId(Long customerId);
        List<AccountResponse> getAllAccounts();
        AccountResponse updateAccount(Long accountId, AccountRequest request);
        void deleteAccount(Long accountId);
}
