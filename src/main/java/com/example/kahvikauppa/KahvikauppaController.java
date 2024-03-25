package com.example.kahvikauppa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KahvikauppaController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

}
