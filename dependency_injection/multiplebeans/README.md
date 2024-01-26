# Выбор из нескольких бинов в контексте Spring

Если в контексте Spring есть несколько бинов одного типа, фреймворк
не может взять из них один для внедрения самостоятельно. Поэтому нужно
указать Spring, какой из экземпляров нужно выбрать:
1. @Qualifier
2. Прямая передача бина в @Bean
3. По названию параметра в зависимом бине
4. @Primary


#### Вариант 1.0 - @Qualifier (лучший вариант)
```java
@Configuration
public class ProjectConfig {

    @Bean
    Parrot parrot1() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot1");
        return parrot;
    }

    @Bean
    Parrot parrot2() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot2");
        return parrot;
    }

    @Bean
    Person person(@Qualifier("parrot2") Parrot cat) { //в аннотации указываю какой бин требуется, название параметра не имеет значения. "parrot2" - это название метода parrot2() (соглашение, для бинов не использовать глаголы)
        Person person = new Person();
        person.setName("Mark");
        person.setParrot(cat);
        return person;
    }
}
```
Аналогично с @Component
```java
@Component
public class Person {
    private String name;
    // @Qualifier("parrot2")  //Вариант 2
    // @Autowired
    private Parrot parrot;

    @Autowired
    public Person(@Qualifier("parrot2")Parrot parrot) { //Вариант 1, оптимальный
        this.parrot = parrot;
    }
}
```

#### Вариант 1.1 - прямой монтаж нужного бина
В примере 2 бина класса Parrot.
Монтируя нужный бин parrot напрямую в бин person, я избегаю проблемы выбора из нескольких бинов, а именно
ошибки вида `NoUniqueBeanDefinitionException: No qualifying bean of type 'ru.poltoranin.main.Parrot' available: expected single matching bean but found 2: parrot1,parrot2`
```java
@Configuration
public class ProjectConfig {

    @Bean
    Parrot parrot1() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot1");
        return parrot;
    }

    @Bean
    Parrot parrot2() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot2");
        return parrot;
    }

    @Bean
    Person person() {
        Person person = new Person();
        person.setName("Mark");
        person.setParrot(parrot2()); //явно указываю нужный бин - parrot2
        return person;
    }
}
```
#### Вариант 1.2 - прямой монтаж нужного бина через параметр
Указывая название параметра, идентичное названию бина, Spring понимает какой бин требуется.
```java
@Configuration
public class ProjectConfig {

    @Bean
    Parrot parrot1() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot1");
        return parrot;
    }

    @Bean
    Parrot parrot2() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot2");
        return parrot;
    }

    @Bean
    Person person(Parrot parrot1) { // указываю название бина в названии параметра - parrot1
        Person person = new Person();
        person.setName("Mark");
        person.setParrot(parrot1);
        return person;
    }
}
```
Аналогично с @Component
```java
@Component
public class Person {
    private String name;
    private Parrot parrot;

    @Autowired
    public Person(Parrot parrot1) { // будет передан бин parrot1 из ProjectConfig
        this.parrot = parrot1;
    }
}
```

#### Вариант 1.3 - @Primary
Если есть несколько бинов - возьмет тот, что указан по дефолту, т.е @Primary

```java
@Configuration
public class ProjectConfig {


    @Bean
    Parrot parrot1() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot1");
        return parrot;
    }
    @Primary
    @Bean
    Parrot parrot2() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot2");
        return parrot;
    }

    @Bean
    Person person(Parrot parrot) { // Spring передаст parrot2
        Person person = new Person();
        person.setName("Mark");
        person.setParrot(parrot);
        return person;
    }
}
```
Для @Component сработает аналогично, вне зависимости от названия поля - подставит parrot2 из ProjectConfig
```java
@Component
public class Person {
    private String name;
    @Autowired
    private Parrot parrot;
}
```