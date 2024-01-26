package ru.poltoranin.beans;

import org.springframework.stereotype.Component;

@Component
public class ParrotEat {
    private String name = "seed";

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ParrotEat name: " + name;
    }
}
