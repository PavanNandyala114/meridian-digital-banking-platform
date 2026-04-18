package com.meridian.paymentservice.service;

import com.meridian.paymentservice.payload.PaymentRequest;
import com.meridian.paymentservice.payload.PaymentResponse;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

        PaymentResponse createPayment(PaymentRequest request);

        PaymentResponse getPaymentById(Long transactionId);

        PaymentResponse getPaymentByTransactionReference(String transactionReference);

        List<PaymentResponse> getAllPayments();

        PaymentResponse updatePayment(Long transactionId, PaymentRequest request);

        PaymentResponse deletePayment(Long transactionId);





}
