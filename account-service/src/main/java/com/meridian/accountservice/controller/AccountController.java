package com.meridian.accountservice.controller;

import com.meridian.accountservice.payload.AccountRequest;
import com.meridian.accountservice.payload.AccountResponse;
import com.meridian.accountservice.payload.ApiResponse;
import com.meridian.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest request) {
         ApiResponse<AccountResponse> response = ApiResponse.<AccountResponse>builder()
                .success(true)
                .message("Account created successfully")
                .data(accountService.createAccount(request))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Long accountId) {
        return ResponseEntity.ok(ApiResponse.<AccountResponse>builder()
                .success(true)
                .message("Account fetched successfully")
                .data(accountService.getAccountById(accountId))
                .build());
    }

    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(ApiResponse.<AccountResponse>builder()
                .success(true)
                .message("Account fetched successfully")
                .data(accountService.getAccountByAccountNumber(accountNumber))
                .build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAccountsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(ApiResponse.<List<AccountResponse>>builder()
                .success(true)
                .message("Accounts fetched successfully")
                .data(accountService.getAccountsByCustomerId(customerId))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        return ResponseEntity.ok(ApiResponse.<List<AccountResponse>>builder()
                .success(true)
                .message("Accounts fetched successfully")
                .data(accountService.getAllAccounts())
                .build());
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(@PathVariable Long accountId, @Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(ApiResponse.<AccountResponse>builder()
                .success(true)
                .message("Account updated successfully")
                .data(accountService.updateAccount(accountId, request))
                .build());
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("Account deleted successfully")
                .data("Deleted account id: " + accountId)
                .build());
    }


}
