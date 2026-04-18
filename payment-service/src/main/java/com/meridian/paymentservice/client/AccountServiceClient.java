package com.meridian.paymentservice.client;

import com.meridian.paymentservice.payload.ApiResponse;
import com.meridian.paymentservice.payload.external.ExternalAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AccountServiceClient {

    private final RestTemplate restTemplate;

        private static final String ACCOUNT_SERVICE_BASE_URL = "http://localhost:8082/api/accounts/{accountId}";

        public ExternalAccountResponse getAccountById(Long accountId) {
            ResponseEntity<ApiResponse<ExternalAccountResponse>> response = restTemplate.exchange(
                    ACCOUNT_SERVICE_BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<ExternalAccountResponse>>() {}, accountId

                );

            ApiResponse<ExternalAccountResponse> body = response.getBody();

            if ( body == null || !body.isSuccess() || body.getData() == null) {
                throw new RuntimeException("Account with ID " + accountId + " not found in Account Service");
            }

            return body.getData();

        }

        public ExternalAccountResponse debitAccount(Long accountId, BigDecimal amount) {
            ResponseEntity<ApiResponse<ExternalAccountResponse>> response = restTemplate.exchange(
                    ACCOUNT_SERVICE_BASE_URL + "/debit?amount={amount}",
                    HttpMethod.PUT,
                    null,
                    new ParameterizedTypeReference<ApiResponse<ExternalAccountResponse>>() {}, accountId, amount
            );

            ApiResponse<ExternalAccountResponse> body = response.getBody();

            if ( body == null || !body.isSuccess() || body.getData() == null) {
                throw new RuntimeException("Failed to debit account with ID " + accountId);
            }

            return body.getData();
        }

            public ExternalAccountResponse creditAccount(Long accountId, BigDecimal amount) {
                ResponseEntity<ApiResponse<ExternalAccountResponse>> response = restTemplate.exchange(
                        ACCOUNT_SERVICE_BASE_URL + "/credit?amount={amount}",
                        HttpMethod.PUT,
                        null,
                        new ParameterizedTypeReference<ApiResponse<ExternalAccountResponse>>() {
                        }, accountId, amount

                );

                ApiResponse<ExternalAccountResponse> body = response.getBody();

                if (body == null || !body.isSuccess() || body.getData() == null) {
                    throw new RuntimeException("Failed to credit account with ID " + accountId);
                }

                return body.getData();

            }

}
