package com.meridian.paymentservice.controller;

import com.meridian.paymentservice.payload.ApiResponse;
import com.meridian.paymentservice.payload.PaymentRequest;
import com.meridian.paymentservice.payload.PaymentResponse;
import com.meridian.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@Valid @RequestBody PaymentRequest request) {
        ApiResponse<PaymentResponse> response = ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment created successfully")
                .data(paymentService.createPayment(request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment retrieved successfully")
                .data(paymentService.getPaymentById(id))
                .build());
    }

    @GetMapping("/reference/{transactionReference}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByTransactionReference(@PathVariable String transactionReference){
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment retrieved successfully")
                .data(paymentService.getPaymentByTransactionReference(transactionReference))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments(){
        return ResponseEntity.ok(ApiResponse.<List<PaymentResponse>>builder()
                .success(true)
                .message("Payments retrieved successfully")
                .data(paymentService.getAllPayments())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentRequest request){
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment updated successfully")
                .data(paymentService.updatePayment(id, request))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> deletePayment(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment deleted successfully")
                .data(paymentService.deletePayment(id))
                .build());
    }


}
