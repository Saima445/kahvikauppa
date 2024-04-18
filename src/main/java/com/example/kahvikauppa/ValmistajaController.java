package com.example.kahvikauppa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ValmistajaController {

    @Autowired
    private OsastoRepository osastoRepository;
    @Autowired
    private ToimittajaRepository toimittajaRepository;
    @Autowired
    private TuoteRepository tuoteRepository;
    @Autowired
    private ValmistajaRepository valmistajaRepository;

    @GetMapping("/valmistajat")
    public String producers(Model model) {
        List<Valmistaja> producers = this.valmistajaRepository.findAll();
        // Käydään läpi jokainen valmistaja ja lasketaan tuotteiden määrä
        for (Valmistaja producer : producers) {
            Long productCount = tuoteRepository.countProductsByValmistajaID(producer.getId());
            producer.setProductCount(productCount.intValue());
        }
        model.addAttribute("producers", this.valmistajaRepository.findAll());
        return "valmistajat";
    }

    @PostMapping("/valmistajat")
    public String addProducer(@RequestParam String name, @RequestParam String url) {
        Valmistaja newValmistaja = new Valmistaja();
        newValmistaja.setName(name.trim());
        newValmistaja.setUrl(url.trim());

        this.valmistajaRepository.save(newValmistaja);

        return "redirect:/valmistajat";
    }

    @GetMapping("/updateProducer/{id}")
    public String getUpdateProducerPage(@PathVariable Long id, Model model) {
        // Haetaan valmistaja tietokannasta valmistajan id:n perusteella
        Valmistaja producer = valmistajaRepository.findById(id).orElse(null);

        if (producer == null) {
            // Jos tuotetta ei löydy ohjataan takaisin valmistajat-sivulle
            return "redirect:/valmistajat";
        }
        model.addAttribute("producer", producer);

        return "muokkaa-valmistajaa";
    }

    @PostMapping("/updateProducer/{id}")
    public String updateProducer(@PathVariable Long id, @RequestParam String name, @RequestParam String url) {

        Valmistaja producer = valmistajaRepository.findById(id).orElse(null);
        if (producer != null) {
            producer.setName(name);
            producer.setUrl(url);

            valmistajaRepository.save(producer);
        }

        return "redirect:/valmistajat";
    }

    @PostMapping("/deleteProducer/{id}")
    public String deleteProducer(@PathVariable Long id) {
        valmistajaRepository.deleteById(id);
        return "redirect:/valmistajat";
    }

}
