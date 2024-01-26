package ru.poltoranin.beans;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name;
    private final Parrot parrot;

    @PostConstruct
    public void init() {
        this.name = "Bob";
    }

    @Autowired
    public Person(Parrot parrot) {
        this.parrot = parrot;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Parrot getParrot() {
        return parrot;
    }

    @Override
    public String toString() {
        return "Person name: " + name;
    }


}
