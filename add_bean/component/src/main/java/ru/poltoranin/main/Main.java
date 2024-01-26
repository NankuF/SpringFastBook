package ru.poltoranin.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.poltoranin.config.ProjectConfig;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Parrot p = context.getBean(Parrot.class);
        System.out.println(p.getName());
        context.close();
    }
}