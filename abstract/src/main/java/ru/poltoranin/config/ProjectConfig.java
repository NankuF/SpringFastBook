package ru.poltoranin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// @ComponentScan(basePackages = {"proxies", "repositories", "services"})
@ComponentScan(basePackages = "ru.poltoranin")
public class ProjectConfig {

}
