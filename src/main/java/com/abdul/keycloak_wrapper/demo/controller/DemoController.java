package com.abdul.keycloak_wrapper.demo.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/example")
public class DemoController {

    @GetMapping("/greet")
    @PreAuthorize("hasRole('client_user')")
    public String greet(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/items")
    @PreAuthorize("hasRole('client_admin')")
    public List<String> getItems() {
        return Arrays.asList("Item1", "Item2", "Item3");
    }

    @GetMapping("/calculate/{a}/{b}")
    public int calculate(@PathVariable int a, @PathVariable int b) {
        return a + b;
    }
}

