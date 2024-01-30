package ru.poltoranin.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.poltoranin.api.model.Country;


@RestController
public class RestExampleController {
    @GetMapping("/restexample")
    public String restExample() {
        return "this is simple rest text";
    }

    @GetMapping("/restcountry")
    public Country restCountry() {
        var country = Country.of("Russia", 150);
        return country;
    }

    @GetMapping("/restfrance")
    public ResponseEntity<Country>  getRestFrance() {
        var country= Country.of("France", 30);
        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .header("continent", "Europe")
            .header("capital", "Paris")
            .header("favorite_food", "cheese and wine")
            .body(country);
    }

}
