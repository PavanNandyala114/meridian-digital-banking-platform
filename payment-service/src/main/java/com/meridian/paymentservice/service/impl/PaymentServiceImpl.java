package com.meridian.paymentservice.service.impl;

import com.meridian.paymentservice.client.AccountServiceClient;
import com.meridian.paymentservice.client.CustomerServiceClient;
import com.meridian.paymentservice.entity.PaymentTransaction;
import com.meridian.paymentservice.exception.DuplicateResourceException;
import com.meridian.paymentservice.exception.ResourceNotFoundException;
import com.meridian.paymentservice.payload.PaymentRequest;
import com.meridian.paymentservice.payload.PaymentResponse;
import com.meridian.paymentservice.payload.external.ExternalAccountResponse;
import com.meridian.paymentservice.payload.external.ExternalCustomerResponse;
import com.meridian.paymentservice.repository.PaymentRepository;
import com.meridian.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerServiceClient customerServiceClient;
    private final AccountServiceClient accountServiceClient;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        log.info("Creating payment with reference: {}", request.getTransactionReference());

        if (paymentRepository.existsByTransactionReference(request.getTransactionReference())) {
            log.warn("Payment with reference {} already exists", request.getTransactionReference());
            throw new DuplicateResourceException("Payment with this transaction reference already exists");

        }

        validatePaymentRequest(request);

        PaymentTransaction payment = PaymentTransaction.builder()
                .transactionReference(request.getTransactionReference())
                .sourceAccountId(request.getSourceAccountId())
                .destinationAccountId(request.getDestinationAccountId())
                .initiatedByCustomerId(request.getInitiatedByCustomerId())
                .amount(request.getAmount())
                .currencyCode(request.getCurrencyCode().toUpperCase())
                .paymentType(PaymentTransaction.PaymentType.valueOf(request.getPaymentType().toUpperCase()))
                .paymentStatus(PaymentTransaction.PaymentStatus.valueOf(request.getPaymentStatus().toUpperCase()))
                .transactionType(PaymentTransaction.TransactionType.valueOf(request.getTransactionType().toUpperCase()))
                .remarks(request.getRemarks())
                .build();

        if (payment.getPaymentStatus() == PaymentTransaction.PaymentStatus.COMPLETED) {
            payment.setCompletedAt(java.time.LocalDateTime.now());

        }
        PaymentTransaction savedPayment = paymentRepository.save(payment);
        log.info("Payment created with ID: {}", savedPayment.getTransactionId());

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Long transactionId) {
        log.info("Retrieving payment with ID: {}", transactionId);
        PaymentTransaction payment = paymentRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + transactionId));

        return mapToResponse(payment);
    }

    public PaymentResponse getPaymentByTransactionReference(String transactionReference) {
        log.info("Retrieving payment with reference: {}", transactionReference);
        PaymentTransaction payment = paymentRepository.findByTransactionReference(transactionReference)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with reference: " + transactionReference));

        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        log.info("Retrieving all payments");
        List<PaymentTransaction> payments = paymentRepository.findAll();
        return payments
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PaymentResponse updatePayment(Long transactionId, PaymentRequest request) {
        log.info("Updating payment with ID: {}", transactionId);
        PaymentTransaction payment = paymentRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + transactionId));

        if (!payment.getTransactionReference().equals(request.getTransactionReference()) &&
                paymentRepository.existsByTransactionReference(request.getTransactionReference())) {
            log.warn("Payment with reference {} already exists", request.getTransactionReference());
            throw new DuplicateResourceException("Payment with this transaction reference already exists");
        }

        validatePaymentRequest(request);

        payment.setTransactionReference(request.getTransactionReference());
        payment.setSourceAccountId(request.getSourceAccountId());
        payment.setDestinationAccountId(request.getDestinationAccountId());
        payment.setInitiatedByCustomerId(request.getInitiatedByCustomerId());
        payment.setAmount(request.getAmount());
        payment.setCurrencyCode(request.getCurrencyCode().toUpperCase());
        payment.setPaymentType(PaymentTransaction.PaymentType.valueOf(request.getPaymentType().toUpperCase()));
        payment.setPaymentStatus(PaymentTransaction.PaymentStatus.valueOf(request.getPaymentStatus().toUpperCase()));
        payment.setTransactionType(PaymentTransaction.TransactionType.valueOf(request.getTransactionType().toUpperCase()));
        payment.setRemarks(request.getRemarks());

        if (payment.getPaymentStatus() == PaymentTransaction.PaymentStatus.COMPLETED && payment.getCompletedAt() == null) {
            payment.setCompletedAt(LocalDateTime.now());
        } else if (payment.getPaymentStatus() != PaymentTransaction.PaymentStatus.COMPLETED) {
            payment.setCompletedAt(null);
        }

        PaymentTransaction updatedPayment = paymentRepository.save(payment);
        log.info("Payment updated with ID: {}", updatedPayment.getTransactionId());

        return mapToResponse(updatedPayment);
    }
    @Override
    public PaymentResponse deletePayment(Long transactionId) {
        log.info("Deleting payment with ID: {}", transactionId);
        PaymentTransaction payment = paymentRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + transactionId));

        paymentRepository.delete(payment);
        log.info("Payment deleted with successfully ID: {}", transactionId);
        return mapToResponse(payment);
    }

    private void validatePaymentRequest(PaymentRequest request) {
        if (request.getSourceAccountId().equals(request.getDestinationAccountId())) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same");
        }

        ExternalCustomerResponse customer = customerServiceClient.getCustomerById(request.getInitiatedByCustomerId());
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with ID " + request.getInitiatedByCustomerId() + " not found");
        }

        ExternalAccountResponse sourceAccount = accountServiceClient.getAccountById(request.getSourceAccountId());
        if (sourceAccount == null) {
            throw new ResourceNotFoundException("Source account with ID " + request.getSourceAccountId() + " not found");
        }

        ExternalAccountResponse destinationAccount = accountServiceClient.getAccountById(request.getDestinationAccountId());
        if (destinationAccount == null) {
            throw new ResourceNotFoundException("Destination account with ID " + request.getDestinationAccountId() + " not found");
        }
    }

    private PaymentResponse mapToResponse(PaymentTransaction payment) {
        return PaymentResponse.builder()
                .transactionId(payment.getTransactionId())
                .transactionReference(payment.getTransactionReference())
                .sourceAccountId(payment.getSourceAccountId())
                .destinationAccountId(payment.getDestinationAccountId())
                .initiatedByCustomerId(payment.getInitiatedByCustomerId())
                .amount(payment.getAmount())
                .currencyCode(payment.getCurrencyCode())
                .paymentType(payment.getPaymentType().name())
                .paymentStatus(payment.getPaymentStatus().name())
                .transactionType(payment.getTransactionType().name())
                .remarks(payment.getRemarks())
                .initiatedAt(payment.getInitiatedAt())
                .completedAt(payment.getCompletedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}


