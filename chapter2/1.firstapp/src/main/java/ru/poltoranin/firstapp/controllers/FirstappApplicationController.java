package ru.poltoranin.firstapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class FirstappApplicationController {
    @RequestMapping("/home")
    public String home() {
        return "home.html";
    }

}
