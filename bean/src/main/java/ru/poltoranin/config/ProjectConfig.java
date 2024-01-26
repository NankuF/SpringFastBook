package ru.poltoranin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import ru.poltoranin.main.Parrot;

@Configuration
public class ProjectConfig {

    @Bean
    // указывает на дефолтный класс при вызове getBean(Parrot.class)
    // когда бинов класса Parrot больше 1го.
    @Primary
    Parrot parrot1() {
        var parrot = new Parrot();
        parrot.setName("Koko");
        return parrot;
    }
    @Bean(name = "miki")
    Parrot parrot2() {
        var parrot = new Parrot();
        parrot.setName("Miki");
        return parrot;
    }
    @Bean
    Parrot parrot3(){
        var parrot = new Parrot();
        parrot.setName("Riki");
        return parrot;
    }
}
