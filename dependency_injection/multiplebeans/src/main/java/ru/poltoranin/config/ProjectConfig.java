package ru.poltoranin.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import ru.poltoranin.main.Parrot;
import ru.poltoranin.main.Person;

@Configuration
@ComponentScan(basePackages = "ru.poltoranin")
public class ProjectConfig {


    @Bean
    Parrot parrot1() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot1");
        return parrot;
    }
    // @Primary
    @Bean
    Parrot parrot2() {
        Parrot parrot = new Parrot();
        parrot.setName("parrot2");
        return parrot;
    }

    // @Bean
    // Person person(@Qualifier("parrot2") Parrot parrot) {
    //     Person person = new Person();
    //     person.setName("Mark");
    //     person.setParrot(parrot);
    //     return person;
    // }
}
