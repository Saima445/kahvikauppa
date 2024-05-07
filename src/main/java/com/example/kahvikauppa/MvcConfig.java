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
// registry.addViewController("/kulutustuotteet").setViewName("kulutustuotteet");
// registry.addViewController("/laite").setViewName("laite");
// registry.addViewController("/tuote").setViewName("tuote");
// registry.addViewController("/vipasiakas").setViewName("vipasiakas");

// // Admin-sivut
// registry.addViewController("/admin").setViewName("admin");
// registry.addViewController("/muokkaa-osastoa").setViewName("muokkaa-osastoa");
// registry.addViewController("/muokkaa-toimittajaa").setViewName("muokkaa-toimittajaa");
// registry.addViewController("/muokkaa-tuotetta").setViewName("muokkaa-tuotetta");
// registry.addViewController("/muokkaa-valmistajaa").setViewName("muokkaa-valmistajaa");
// registry.addViewController("/osastot").setViewName("osastot");
// registry.addViewController("/toimittajat").setViewName("toimittajat");
// registry.addViewController("/valmistajat").setViewName("valmistajat");
// registry.addViewController("/vipasiakkaat").setViewName("vipasiakkaat");