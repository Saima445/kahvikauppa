package com.example.kahvikauppa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        // sisäänkirjautumisen rakentamisessa käytetty ohjeita:
        // https://spring.io/guides/gs/securing-web
        // login sivulle kontrolleri
        registry.addViewController("/login").setViewName("login");
    }
}
// voisi käyttää muillekin sivuille, mutta ei ole moocin materiaalista tullut
// tutuksi käytännöksi
// // Perussivut
// registry.addViewController("/").setViewName("index");
// registry.addViewController("/kahvilaitteet").setViewName("kahvilaitteet");
// // Admin-sivut
// registry.addViewController("/admin").setViewName("admin");
// jne
