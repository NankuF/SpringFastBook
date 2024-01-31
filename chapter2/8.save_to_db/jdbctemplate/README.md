# JdbcTemplate - запись в бд встроенными средствами + кастомное подключение DataSource + Смена H2 на MySql

Репозиторий — это класс, отвечающий за взаимодействие с базой данных.

`DataSource` или `Источник данных` - сущность отвечающая за соединение с базой данных (стр 316).<br>
Создает подключение когда необходимо, а не на каждый запрос. Использовать обязательно!. SpringBoot подключает автоматически.<br>
Используемый по умолчанию datasource - `HicariCp` [link on Habr](https://habr.com/ru/articles/269023/)<br>
`JdbcTemplate` использует `DataSource`, но не является им!


Прочитать из базы<br>
```java
curl 'http://localhost:8080/purchase'
```
Записать в базу<br>
```java
curl -X POST 'http://localhost:8080/purchase' -H 'Content-Type: application/json' -d '{"product" : "Spring Security in Action","price" : 25.2}'
```

#### Схема (только для учебных проектов! Иначе используй LiquidBase для миграций)
```sql
CREATE TABLE IF NOT EXISTS purchase (
    id int auto_increment primary key,
    product varchar(50) NOT NULL,
    price double NOT NULL
);
```
#### Репозиторий
```java
@Repository
public class PurchaseRepository {
    private final JdbcTemplate jdbc;

    public PurchaseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void storePurchase(Purchase purchase) {
        String sql = "INSERT INTO purchase VALUES (default, ?, ?)";
        jdbc.update(sql, purchase.getProduct(), purchase.getPrice());
    }

    public List<Purchase> findAllPurchases() {
        String sql = "SELECT * FROM purchase";

        RowMapper<Purchase> purchaseRowMapper = (resultSet, index) -> {
            Purchase rowObject = new Purchase();
            rowObject.setId(resultSet.getInt("id"));
            rowObject.setProduct(resultSet.getString("product"));
            rowObject.setPrice(resultSet.getBigDecimal("price"));
            return rowObject;
        };

        return jdbc.query(sql, purchaseRowMapper);
    }
}
```
#### Model
```java
package ru.poltoranin.jdbctemplate.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Purchase {
    private int id;
    private String product;
    private BigDecimal price;
    //setters and getters

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Purchase purchase = (Purchase) o;
        return id == purchase.id && Objects.equals(product, purchase.product)
                && Objects.equals(price, purchase.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, price);
    }
}
```
#### Controller
```java
@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseRepository purchaseRepository;

    public PurchaseController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @PostMapping
    public void storePurchase(@RequestBody Purchase purchase) {
        purchaseRepository.storePurchase(purchase);
    }

    @GetMapping
    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAllPurchases();
    }
}
```

## Пример подключения datasource (нет в коде)
```java
package com.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ProjectConfig {

  @Value("${custom.datasource.url}")
  private String datasourceUrl;

  @Value("${custom.datasource.username}")
  private String datasourceUsername;

  @Value("${custom.datasource.password}")
  private String datasourcePassword;

  @Bean
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(datasourceUrl);
    dataSource.setUsername(datasourceUsername);
    dataSource.setPassword(datasourcePassword);
    dataSource.setConnectionTimeout(1000);
    return dataSource;
  }
}
```
```text
// application.properties
custom.datasource.url=jdbc:mysql://localhost/spring_quickly
custom.datasource.username=root
custom.datasource.password=
```
## Использование MySql вместо H2
Достаточно изменить зависимость c `h2` на `mysql` и добавить настройки в `application.properties`. Остальной код в проекте оставить без изменений.
```xml
//pom.xml
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```
```text
//application.properties
spring.datasource.url=jdbc:mysql://localhost/spring_quickly
spring.datasource.username=root
spring.datasource.password=
spring.datasource.initialization-mode=always
```