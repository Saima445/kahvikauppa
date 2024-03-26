package com.example.kahvikauppa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KahvikauppaController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/kahvilaitteet")
    public String laitteet() {
        return "kahvilaitteet";
    }

    @GetMapping("/kulutustuotteet")
    public String tuotteet() {
        return "kulutustuotteet";
    }

    @GetMapping("/tuote-laite")
    public String yksittainentuote() {
        return "tuote-laite";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
