package ru.poltoranin.beans;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Parrot {
    private String name;
    @Autowired
    private ParrotEat parrotEat;

    @PostConstruct
    public void init() {
        this.name = "Abu";
    }

    public ParrotEat getParrotEat() {
        return parrotEat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parrot name: " + name;
    }
}
