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
public class ToimittajaController {

    // @Autowired
    // private ToimittajaRepository toimittajaRepository;
    // @Autowired
    // private TuoteRepository tuoteRepository;
    @Autowired
    private ToimittajaService toimittajaService;

    // @GetMapping("/toimittajat")
    // public String suppliers(Model model) {
    // List<Toimittaja> suppliers = this.toimittajaRepository.findAll();
    // // Käydään läpi jokainen toimittaja ja lasketaan tuotteiden määrä
    // for (Toimittaja supplier : suppliers) {
    // Long productCount =
    // tuoteRepository.countProductsByToimittajaID(supplier.getId());
    // supplier.setProductCount(productCount.intValue());
    // }
    // model.addAttribute("suppliers", this.toimittajaRepository.findAll());
    // return "toimittajat";
    // }
    @GetMapping("/toimittajat")
    public String suppliers(Model model) {
        List<Toimittaja> suppliers = this.toimittajaService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "toimittajat";
    }

    // @PostMapping("/toimittajat")
    // public String addSupplier(@RequestParam String name, @RequestParam String
    // contactPerson,
    // @RequestParam String contactPersonEmail) {
    // Toimittaja newSupplier = new Toimittaja();
    // newSupplier.setName(name.trim());
    // newSupplier.setContactPerson(contactPerson.trim());
    // newSupplier.setContactPersonEmail(contactPersonEmail.trim());

    // this.toimittajaRepository.save(newSupplier);

    // return "redirect:/toimittajat";
    // }
    @PostMapping("/toimittajat")
    public String addSupplier(@RequestParam String name, @RequestParam String contactPerson,
            @RequestParam String contactPersonEmail) {
        this.toimittajaService.addSupplier(name, contactPerson, contactPersonEmail);
        return "redirect:/toimittajat";
    }

    // @GetMapping("/updateSupplier/{id}")
    // public String getUpdateSupplierPage(@PathVariable Long id, Model model) {
    // // Haetaan toimittaja tietokannasta toimittajan id:n perusteella
    // Toimittaja supplier = toimittajaRepository.findById(id).orElse(null);

    // if (supplier == null) {
    // // Jos tuotetta ei löydy ohjataan takaisin toimittajat-sivulle
    // return "redirect:/toimittajat";
    // }
    // model.addAttribute("supplier", supplier);

    // return "muokkaa-toimittajaa";
    // }
    @GetMapping("/updateSupplier/{id}")
    public String getUpdateSupplierPage(@PathVariable Long id, Model model) {
        Toimittaja supplier = this.toimittajaService.getSupplierById(id);
        if (supplier != null) {
            model.addAttribute("supplier", supplier);
            return "muokkaa-toimittajaa";
        }
        return "redirect:/toimittajat";
    }

    // @PostMapping("/updateSupplier/{id}")
    // public String updateSupplier(@PathVariable Long id, @RequestParam String
    // name, @RequestParam String contactPerson,
    // @RequestParam String contactPersonEmail) {

    // Toimittaja supplier = toimittajaRepository.findById(id).orElse(null);
    // if (supplier != null) {
    // supplier.setName(name);
    // supplier.setContactPerson(contactPerson);
    // supplier.setContactPersonEmail(contactPersonEmail);

    // toimittajaRepository.save(supplier);
    // }

    // return "redirect:/toimittajat";
    // }
    @PostMapping("/updateSupplier/{id}")
    public String updateSupplier(@PathVariable Long id, @RequestParam String name, @RequestParam String contactPerson,
            @RequestParam String contactPersonEmail) {
        this.toimittajaService.updateSupplier(id, name, contactPerson, contactPersonEmail);
        return "redirect:/toimittajat";
    }

    // @PostMapping("/deleteSupplier/{id}")
    // public String deleteSupplier(@PathVariable Long id) {
    // toimittajaRepository.deleteById(id);
    // return "redirect:/toimittajat";
    // }
    @PostMapping("/deleteSupplier/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        this.toimittajaService.deleteSupplier(id);
        return "redirect:/toimittajat";
    }

}
