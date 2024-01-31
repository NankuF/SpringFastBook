package ru.poltoranin.resttemplateclient.proxy;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.poltoranin.resttemplateclient.model.Payment;

@Component
public class PaymentsProxy {

    private final RestTemplate restTemplate;

    @Value("${name.service.url}")
    private String paymentServiceUrl;

    public PaymentsProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Payment createPayment(Payment payment) {
        String uri = paymentServiceUrl + "/payment";

        HttpHeaders headers = new HttpHeaders();
        headers.add("requestId", UUID.randomUUID().toString());

        HttpEntity<Payment> httpEntity = new HttpEntity<>(payment, headers);

        ResponseEntity<Payment> response =
                restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Payment.class);

        return response.getBody();
    }
}
