package ru.poltoranin.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.poltoranin.api.model.Country;

@Controller
public class ExampleController {
    @GetMapping("/example")
    @ResponseBody
    public String example() {
        return "this is simple text";
    }

    @GetMapping("/country")
    @ResponseBody
    public Country country() {
        var country = Country.of("SuperRussia", 250);
        return country;
    }

    @GetMapping("/france")
    @ResponseBody
    public ResponseEntity<Country>  getFrance() {
        var country= Country.of("France", 30);
        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .header("continent", "Europe")
            .header("capital", "Paris")
            .header("favorite_food", "cheese and wine")
            .body(country);
    }
}
