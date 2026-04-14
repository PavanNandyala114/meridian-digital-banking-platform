package com.meridian.paymentservice.client;

import com.meridian.paymentservice.exception.ResourceNotFoundException;
import com.meridian.paymentservice.payload.ApiResponse;
import com.meridian.paymentservice.payload.external.ExternalCustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CustomerServiceClient {

         private final RestTemplate restTemplate;
            private static final String CUSTOMER_SERVICE_BASE_URL = "http://localhost:8081/api/customers/{customerId}";

    public ExternalCustomerResponse getCustomerById(Long customerId) {
        ResponseEntity<ApiResponse<ExternalCustomerResponse>> response = restTemplate.exchange(
                CUSTOMER_SERVICE_BASE_URL,
                    HttpMethod.GET,
                null,
                    new ParameterizedTypeReference<ApiResponse<ExternalCustomerResponse>>() {}, customerId
        );

        ApiResponse<ExternalCustomerResponse> body = response.getBody();

        if ( body == null || !body.isSuccess() || body.getData() == null) {
           throw new ResourceNotFoundException("Customer with ID " + customerId + " not found in Customer Service");
        }

        return body.getData();

    }
}
