# SpringData with jdbc
В этом примере используется зависимость springdata-jdbc - абстракция в виде интерфейсов над jdbc. Это позволяет лишь определять запросы, остальное сделает фреймворк `Spring Data`.

```java
//AccountRepository.java
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("SELECT * FROM account WHERE name = :name")
    List<Account> findAccountsByName(String name);

    @Modifying
    @Query("UPDATE account SET amount = :amount WHERE id = :id")
    void changeAmount(long id, BigDecimal amount);

}
// Account.java
public class Account {

    @Id  // необходимо указать поле, которое станет первичным ключом.
    private long id;
    private String name;
    private BigDecimal amount;

```
SpringData позволяет по названию метода составлять sql запрос.
Но это невсегда хорошая практика.<br>
Иной способ - использовать аннотацию `@Query`.<br>
Она позволяет явно указать sql запрос, в этом случае название метода не имеет значения.

`@Modifying` - ставится над запросами, вносящими изменение в бд = UPDATE, DELETE, INSERT.

Для аспекта используемого в транзакциях создадим класс исключения, чтобы вывод ошибки был более понятным.
```java
public class AccountNotFoundException extends RuntimeException{}

// применим это исключение в TransferService.java
@Service
public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional // под капотом аспект, перехватывать исключения НЕЛЬЗЯ!
    public void transferMoney(long idSender, long idReceiver, BigDecimal amount) {
        Account sender = accountRepository.findById(idSender)
                .orElseThrow(() -> new AccountNotFoundException()); // здесь

        Account receiver = accountRepository.findById(idReceiver)
                .orElseThrow(() -> new AccountNotFoundException()); // здесь

        BigDecimal senderNewAmount = sender.getAmount().subtract(amount);
        BigDecimal receiverNewAmount = receiver.getAmount().add(amount);

        accountRepository.changeAmount(idSender, senderNewAmount);
        accountRepository.changeAmount(idReceiver, receiverNewAmount);
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> findAccountsByName(String name) {
        return accountRepository.findAccountsByName(name);
    }
}
```
Перевести деньги<br>
```bash
curl -XPOST -H "content-type:application/json" -d '{"senderAccountId":1, "receiverAccountId":2, "amount":100}' http://localhost:8080/transfer
```
Узнать баланс на аккаунтах<br>
```bash
curl http://localhost:8080/accounts
```
Узнать баланс одного аккаунта<br>
```bash
curl http://localhost:8080/accounts?name=Helen+Down
```