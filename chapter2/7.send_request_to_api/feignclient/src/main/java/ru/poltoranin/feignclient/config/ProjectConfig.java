package ru.poltoranin.feignclient.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "ru.poltoranin.feignclient.proxy")
public class ProjectConfig {

}
