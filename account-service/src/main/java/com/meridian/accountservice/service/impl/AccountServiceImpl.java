package com.meridian.accountservice.service.impl;

import com.meridian.accountservice.entity.Account;
import com.meridian.accountservice.exception.ResourceNotFoundException;
import com.meridian.accountservice.payload.AccountRequest;
import com.meridian.accountservice.payload.AccountResponse;
import com.meridian.accountservice.repository.AccountRepository;
import com.meridian.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        log.info("Creating account for customerId: {}", request.getCustomerId());

        if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            log.warn("Account number {} already exists", request.getAccountNumber());
            throw new IllegalArgumentException("Account number already exists");
        }
        Account account = Account.builder()
                .customerId(request.getCustomerId())
                .accountNumber(request.getAccountNumber())
                .accountType(Account.AccountType.valueOf(request.getAccountType().toUpperCase()))
                .currencyCode(request.getCurrencyCode())
                .availableBalance(request.getAvailableBalance())
                .accountStatus(Account.AccountStatus.valueOf(request.getAccountStatus().toUpperCase()))
                .branchCode(request.getBranchCode())
                .accountHolderName(request.getAccountHolderName())
                .build();
        Account savedAccount = accountRepository.save(account);
        log.info("Account created successfully with accountId: {}", savedAccount.getAccountId());
        return mapToResponse(savedAccount);
    }

    @Override
    public AccountResponse getAccountById(Long accountId) {
        log.info("Fetching account with accountId: {}", accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.warn("Account with accountId {} not found", accountId);
                    return new ResourceNotFoundException("Account not found with id: " + accountId);
                });
        log.info("Account fetched successfully for accountId: {}", accountId);
        return mapToResponse(account);
    }

    @Override
    public AccountResponse getAccountByAccountNumber(String accountNumber) {
        log.info("Fetching account with accountNumber: {}", accountNumber);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    log.warn("Account with accountNumber {} not found", accountNumber);
                    return new ResourceNotFoundException("Account not found with account number: " + accountNumber);
                });
        log.info("Account fetched successfully for accountNumber: {}", accountNumber);
        return mapToResponse(account);
    }

    @Override
    public AccountResponse updateAccount(Long accountId, AccountRequest request) {
        log.info("Updating account with accountId: {}", accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.warn("Account with accountId {} not found for update", accountId);
                    return new ResourceNotFoundException("Account not found with id: " + accountId);
                });
        account.setCustomerId(request.getCustomerId());
        account.setAccountType(Account.AccountType.valueOf(request.getAccountType().toUpperCase()));
        account.setCurrencyCode(request.getCurrencyCode());
        account.setAvailableBalance(request.getAvailableBalance());
        account.setAccountStatus(Account.AccountStatus.valueOf(request.getAccountStatus().toUpperCase()));
        account.setBranchCode(request.getBranchCode());
        account.setAccountHolderName(request.getAccountHolderName());

        Account updatedAccount = accountRepository.save(account);
        log.info("Account updated successfully for accountId: {}", accountId);
        return mapToResponse(updatedAccount);
    }

    @Override
    public void deleteAccount(Long accountId) {
        log.info("Deleting account with accountId: {}", accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.warn("Account with accountId {} not found for deletion", accountId);
                    return new ResourceNotFoundException("Account not found with id: " + accountId);
                });
        accountRepository.delete(account);
        log.info("Account deleted successfully for accountId: {}", accountId);
    }

    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .customerId(account.getCustomerId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType().name())
                .currencyCode(account.getCurrencyCode())
                .availableBalance(account.getAvailableBalance().toString())
                .accountStatus(account.getAccountStatus().name())
                .branchCode(account.getBranchCode())
                .accountHolderName(account.getAccountHolderName())
                .openedAt(account.getOpenedAt())
                .updatedAt(account.getUpdatedAt())
                .closedAt(account.getClosedAt())
                .build();
    }

    @Override
    public List<AccountResponse> getAccountsByCustomerId(Long customerId) {
        log.info("Fetching accounts for customerId: {}", customerId);
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        log.info("Fetched {} accounts for customerId: {}", accounts.size(), customerId);
        return accounts
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        log.info("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        log.info("Fetched {} accounts", accounts.size());
        return accounts
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}
