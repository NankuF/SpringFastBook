package ru.poltoranin.apiexception.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.poltoranin.apiexception.model.PaymentDetails;
import ru.poltoranin.apiexception.services.PaymentService;

@RestController
public class AOPPaymentController {
    private final PaymentService paymentService;

    public AOPPaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/aoppayment")
    public ResponseEntity<PaymentDetails> makePayment() {
        PaymentDetails paymentDetails = paymentService.processPayment();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentDetails);
    }
}
