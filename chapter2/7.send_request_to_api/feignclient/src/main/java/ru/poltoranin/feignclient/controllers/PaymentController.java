package ru.poltoranin.feignclient.controllers;

import org.springframework.web.bind.annotation.RestController;
import ru.poltoranin.feignclient.model.Payment;
import ru.poltoranin.feignclient.proxy.PaymentProxy;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class PaymentController {
    private final PaymentProxy paymentProxy;

    public PaymentController(PaymentProxy paymentProxy) {
        this.paymentProxy = paymentProxy;
    }

    @PostMapping("/payment")
    public Payment createPayment(@RequestBody Payment payment) {
        String requestId = UUID.randomUUID().toString();
        return paymentProxy.createPayment(requestId, payment);
    }


}
