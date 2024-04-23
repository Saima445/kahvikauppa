package com.example.kahvikauppa;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KahvikauppaController {

    @Autowired
    private TuoteRepository tuoteRepository;
    @Autowired
    private TuoteService tuoteService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/kahvilaitteet")
    public String machines(Model model) {
        // List<Tuote> kahvilaitteet = tuoteRepository.findProductsByOsastoID(1L); //
        // kaikki tuotteet osasto 1 alla
        List<Tuote> kahvilaitteet = tuoteService.getProductsKahvilaitteet();
        model.addAttribute("kahvilaitteet", kahvilaitteet);
        return "kahvilaitteet";
    }

    @GetMapping("/laite/{id}")
    public String individualMachinePage(@PathVariable Long id, Model model) {
        Tuote product = tuoteService.getProductById(id);

        if (product == null) {
            return "redirect:/kahvilaitteet";
        }
        String imageURL = "/productImage/" + id;
        model.addAttribute("imageURL", imageURL);
        model.addAttribute("product", product);

        return "laite";
    }

    @GetMapping("/kulutustuotteet")
    public String consumerProducts(Model model) {
        // kaikki tuotteet osasto 2 alla, EI TOIMI OSASTO 7 ALLE OLEVILLE!
        // List<Tuote> kulutustuotteet = tuoteRepository.findProductsByOsastoID(2L);
        // model.addAttribute("kulutustuotteet", kulutustuotteet);

        // Haetaan tuotteet osaston 2 alla
        // List<Tuote> osasto2Tuotteet = tuoteRepository.findProductsByOsastoID(2L);
        // // Haetaan tuotteet osaston 7 alla
        // List<Tuote> osasto7Tuotteet = tuoteRepository.findProductsByOsastoID(7L);
        // // Yhdistetään
        // osasto2Tuotteet.addAll(osasto7Tuotteet);
        // // poistetaan mahdolliset duplikaatit
        // Set<Tuote> yhdistetytTuotteet = new LinkedHashSet<>(osasto2Tuotteet);

        // model.addAttribute("kulutustuotteet", new ArrayList<>(yhdistetytTuotteet));
        List<Tuote> kulutustuotteet = tuoteService.getProductsKulutustuotteet();
        model.addAttribute("kulutustuotteet", kulutustuotteet);
        return "kulutustuotteet";
    }

    @GetMapping("/tuote/{id}")
    public String individualProductPage(@PathVariable Long id, Model model) {
        Tuote product = tuoteService.getProductById(id);

        if (product == null) {
            return "redirect:/kulutustuotteet";
        }
        String imageURL = "/productImage/" + id;
        model.addAttribute("imageURL", imageURL);
        model.addAttribute("product", product);

        return "tuote";
    }

    // KUVIEN PURKAMINEN
    @GetMapping("/productImage/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Tuote product = tuoteService.getProductById(id);
        if (product != null && product.getProductImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(product.getProductImage(), headers,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchMachines")
    public String searchMachines(@RequestParam String keyword, Model model) {
        List<Tuote> machines = tuoteService.searchMachines(keyword);
        if (machines.isEmpty()) {
            model.addAttribute("keyword", "Hakuehtoa vastaavia tuotteita ei löytynyt kahvilaitteistamme");
            model.addAttribute("keywordExists", true);
        }
        model.addAttribute("kahvilaitteet", machines);
        model.addAttribute("keywordExists", true);
        return "kahvilaitteet";
    }

    @GetMapping("/searchConsumerProducts")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<Tuote> machines = tuoteService.searchConsumerProducts(keyword);
        if (machines.isEmpty()) {
            model.addAttribute("keyword", "Hakuehtoa vastaavia tuotteita ei löytynyt kulutustuotteistamme");
            model.addAttribute("keywordExists", true);
        }
        model.addAttribute("kulutustuotteet", machines);
        model.addAttribute("keywordExists", true);
        return "kulutustuotteet";
    }

}
