package ru.poltoranin.sessionscope.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.poltoranin.sessionscope.model.LoginProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class LoginController {
    private final LoginProcessor loginProcessor;

    public LoginController(LoginProcessor loginProcessor) {
        this.loginProcessor = loginProcessor;
    }

    @GetMapping("/")
    public String loginGet() {
        return "login.html";
    }

    @PostMapping("/")
    public String loginPost(@RequestParam String username, @RequestParam String password,
            Model model) {
        loginProcessor.setUsername(username);
        loginProcessor.setPassword(password);
        boolean loddedIn = loginProcessor.login();
        if (loddedIn) {
            return "redirect:/main";
        }

        model.addAttribute("message", "Login failed!");
        return "login.html";
    }

}
