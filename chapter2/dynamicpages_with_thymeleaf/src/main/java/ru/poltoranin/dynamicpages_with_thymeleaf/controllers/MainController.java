package ru.poltoranin.dynamicpages_with_thymeleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // передача данных в home.html
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping("/home") // localhost:8080/home?color=red&name=Katy
    public String home(@RequestParam(required = false) String color,
            @RequestParam(required = false) String name, Model page) {
        page.addAttribute("username", name);
        page.addAttribute("color", color);
        return "home.html";
    }

    @RequestMapping("/home/{color}") // localhost:8080/home/red?name=Katy
    public String homeWithPathVariable(@PathVariable String color,
            @RequestParam(required = false) String name, Model page) {
        page.addAttribute("username", name);
        page.addAttribute("color", color);
        return "home.html";
    }

}
