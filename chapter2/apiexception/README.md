# Exceptions and Aspects

### Стандартный перехват ошибок
Создали свой класс ошибок `NotEnoughMoneyException.java`<br>
Создали свой класс для передачи деталей ошибок, чтобы в reponse передавать json - `ErrorDetails.java`<br>

Перехват ошибок через try-catch
```java
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<?> makePayment() {
        try {
            PaymentDetails paymentDetails = paymentService.processPayment();
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(paymentDetails);
        } catch (NotEnoughMoneyException e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setMessage("Not enough money to make the payment.");
            return ResponseEntity
                    .badRequest()
                    .body(errorDetails); // {"message": "Not enough money to make the payment."}
        }
    }

// NotEnoughMoneyException.java
package ru.poltoranin.apiexception.exceptions;

public class NotEnoughMoneyException extends RuntimeException{}

// ErrorDetails.java
package ru.poltoranin.apiexception.model;

public class ErrorDetails {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

```
Отправьте POST-запрос, чтобы увидеть результат - `curl -v -X POST http://127.0.0.1:8080/payment -d '{"amount": 1000}' -H "Content-Type: application/json"
`
```text
*   Trying 127.0.0.1:8080...
* Connected to 127.0.0.1 (127.0.0.1) port 8080 (#0)
> POST /payment HTTP/1.1
> Host: 127.0.0.1:8080
> User-Agent: curl/7.81.0
> Accept: */*
> Content-Type: application/jsonn
> Content-Length: 16
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 400
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Tue, 30 Jan 2024 08:56:18 GMT
< Connection: close
<
* Closing connection 0
{"message":"Not enough money to make the payment."}
```
### Перехват ошибок через аспект
Идея в том, чтобы отделить логику перехвата ошибок от логики выполнения кода. Тогда все,что касается ошибок, будет в одном файле/методе и тд, а контроллеры будут содержать только бизнес-логику.<br>

В примере ниже если будет ошибка при передаче POST-запроса в эндпоинт `/aoppayment`, то выполнится код из `ExceptionControllerAdvice.java`,
иначе выполнится код из `AOPPaymentController.java`
```java
// ExceptionControllerAdvice.java
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorDetails> exceptionNotEnoughMoneyHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Not enough money to make the payment.");
        return ResponseEntity.badRequest().body(errorDetails);
    }
}
// AOPPaymentController.java
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
```
