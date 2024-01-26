package ru.poltoranin.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.poltoranin.beans.Person;
import ru.poltoranin.config.ProjectConfig;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Person person = context.getBean(Person.class);
        System.out.println(person);
        System.out.println(person.getParrot());
        System.out.println(person.getParrot().getParrotEat());
        context.close();
    }
}