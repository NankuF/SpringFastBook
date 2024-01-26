package ru.poltoranin.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.poltoranin.config.ProjectConfig;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Parrot parrot1 = context.getBean(Parrot.class);
        Parrot parrot2 = context.getBean("miki", Parrot.class);
        System.out.println(parrot1.getName());
        System.out.println(parrot2.getName());
        context.close();
        }
}