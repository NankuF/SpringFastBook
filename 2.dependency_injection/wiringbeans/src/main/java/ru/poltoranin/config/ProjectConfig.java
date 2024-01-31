package ru.poltoranin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.poltoranin.beans.Parrot;
import ru.poltoranin.beans.Person;

@Configuration
public class ProjectConfig {
    //1
    // @Bean
    // Person person(Parrot parrot) { //wiring in params
    //     Person person = new Person();
    //     person.setName("Bob");
    //     person.setParrot(parrot);
    //     return person;
    // }

    //2
    @Bean
    Person person() {
        Person person = new Person();
        person.setName("Bob");
        person.setParrot(parrot()); //wiring
        return person;
    }

    @Bean
    Parrot parrot() {
        Parrot parrot = new Parrot();
        parrot.setName("Abu");
        return parrot;
    }
}
