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

@Controller
public class KahvikauppaController {

    @Autowired
    private OsastoRepository osastoRepository;
    @Autowired
    private ToimittajaRepository toimittajaRepository;
    @Autowired
    private TuoteRepository tuoteRepository;
    @Autowired
    private ValmistajaRepository valmistajaRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/kahvilaitteet")
    public String machines(Model model) {
        List<Tuote> kahvilaitteet = tuoteRepository.findProductsByOsastoID(1L); // kaikki tuotteet osasto 1 alla
        model.addAttribute("kahvilaitteet", kahvilaitteet);
        return "kahvilaitteet";
    }

    @GetMapping("/laite/{id}")
    public String individualMachinePage(@PathVariable Long id, Model model) {
        Tuote product = tuoteRepository.findById(id).orElse(null);

        if (product == null) {
            return "redirect:/kulutustuotteet";
        }
        String imageURL = "/productImage/" + id;
        model.addAttribute("imageURL", imageURL);
        model.addAttribute("product", product);

        return "tuote";
    }

    @GetMapping("/kulutustuotteet")
    public String consumerProducts(Model model) {
        // kaikki tuotteet osasto 2 alla, EI TOIMI OSASTO 7 ALLE OLEVILLE!
        // List<Tuote> kulutustuotteet = tuoteRepository.findProductsByOsastoID(2L);
        // model.addAttribute("kulutustuotteet", kulutustuotteet);

        // Haetaan tuotteet osaston 2 alla
        List<Tuote> osasto2Tuotteet = tuoteRepository.findProductsByOsastoID(2L);
        // Haetaan tuotteet osaston 7 alla
        List<Tuote> osasto7Tuotteet = tuoteRepository.findProductsByOsastoID(7L);
        // Yhdistetään
        osasto2Tuotteet.addAll(osasto7Tuotteet);
        // poistetaan mahdolliset duplikaatit
        Set<Tuote> yhdistetytTuotteet = new LinkedHashSet<>(osasto2Tuotteet);

        model.addAttribute("kulutustuotteet", new ArrayList<>(yhdistetytTuotteet));
        return "kulutustuotteet";
    }

    @GetMapping("/tuote/{id}")
    public String individualProductPage(@PathVariable Long id, Model model) {
        Tuote product = tuoteRepository.findById(id).orElse(null);

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
        Tuote product = tuoteRepository.findById(id).orElse(null);
        if (product != null && product.getProductImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(product.getProductImage(), headers,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
