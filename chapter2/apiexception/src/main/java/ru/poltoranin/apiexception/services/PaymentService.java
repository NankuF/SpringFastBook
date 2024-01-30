package ru.poltoranin.apiexception.services;

import org.springframework.stereotype.Service;
import ru.poltoranin.apiexception.exceptions.NotEnoughMoneyException;
import ru.poltoranin.apiexception.model.PaymentDetails;

@Service
public class PaymentService {

    public PaymentDetails processPayment(){
        throw new NotEnoughMoneyException();
    }
}
