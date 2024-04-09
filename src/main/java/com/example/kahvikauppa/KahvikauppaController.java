package com.example.kahvikauppa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String machines() {
        return "kahvilaitteet";
    }

    @GetMapping("/kulutustuotteet")
    public String consumerProducts() {
        return "kulutustuotteet";
    }

    @GetMapping("/tuote-laite")
    public String individualProduct() {
        return "tuote-laite";
    }

    @GetMapping("/toimittajat")
    public String suppliers(Model model) {
        model.addAttribute("suppliers", this.toimittajaRepository.findAll());
        return "toimittajat";
    }

    @PostMapping("/toimittajat")
    public String addSupplier(@RequestParam String name, @RequestParam String contactPerson,
            @RequestParam String contactPersonEmail) {
        Toimittaja newToimittaja = new Toimittaja();
        newToimittaja.setName(name.trim());
        newToimittaja.setContactPerson(contactPerson.trim());
        newToimittaja.setContactPersonEmail(contactPersonEmail.trim());

        this.toimittajaRepository.save(newToimittaja);

        return "redirect:/toimittajat";
    }

    @GetMapping("/valmistajat")
    public String producers(Model model) {
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

    @GetMapping("/osastot")
    public String departments(Model model) {
        model.addAttribute("departments", this.osastoRepository.findAll());
        return "osastot";
    }

    @PostMapping("/osastot")
    public String addDepartment(@RequestParam String name, @RequestParam Long osastoIDP) {
        Osasto newOsasto = new Osasto();
        newOsasto.setName(name.trim());
        newOsasto.setOsastoIDP(osastoIDP);

        this.osastoRepository.save(newOsasto);

        return "redirect:/osastot";
    }

    @GetMapping("/admin")
    public String admin(Model model) {

        model.addAttribute("products", this.tuoteRepository.findAll());

        model.addAttribute("departments", this.osastoRepository.findAll());
        model.addAttribute("suppliers", this.toimittajaRepository.findAll());
        model.addAttribute("producers", this.valmistajaRepository.findAll());
        model.addAttribute("isNewSupplier", true);
        model.addAttribute("isNewProducer", true);

        // JUURI tasolla olevat osastot (ensimmäinen taso)
        // List<Osasto> rootDepartments = this.osastoRepository.findRootDepartments();
        // model.addAttribute("rootDepartments", rootDepartments);
        return "admin";
    }

    // // ALAOSASTOT annetun vanhemman osaston perusteella (toinen taso)
    // @GetMapping("/{parentId}/getSubDepartments")
    // public String getSubDepartments(@PathVariable Long parentId, Model model) {
    // // List<Osasto> subDepartments =
    // // this.osastoRepository.findSubDepartments(parentId);
    // // model.addAttribute("subDepartments", subDepartments);
    // model.addAttribute("subDepartments",
    // this.osastoRepository.findSubDepartments(parentId));

    // // Palautetaan samalle admin-sivulle päivitetyllä mallilla
    // return "admin";
    // }

    // // ALEMMAN TASON OSASTOT annetun vanhemman osaston perusteella (kolmas taso)
    // @GetMapping("/{parentId}/getLowerLevelDepartments/")
    // @ResponseBody
    // public List<Osasto> getLowerLevelDepartments(@PathVariable Long parentId) {
    // List<Osasto> lowerLevelDepartments =
    // this.osastoRepository.findLowerLevelDepartments(parentId);
    // return lowerLevelDepartments;
    // }

    @PostMapping("/admin") // lisää kuvakenttä myöhemmin!!
    public String addProduct(@RequestParam String name, @RequestParam BigDecimal price,
            @RequestParam String description, @RequestParam Long departmentId, @RequestParam String supplierId,
            @RequestParam String newSupplierName, @RequestParam String producerId,
            @RequestParam String newProducerName) {

        // Haetaan osasto, toimittaja ja valmistaja niiden ID:n perusteella
        Osasto existingDepartment = osastoRepository.findById(departmentId).orElse(null);

        Toimittaja existingSupplier = null;
        if ("-1".equals(supplierId)) {
            // Luo uusi toimittaja
            Toimittaja newSupplier = new Toimittaja();
            newSupplier.setName(newSupplierName.trim());
            existingSupplier = toimittajaRepository.save(newSupplier);
        } else { // Hae olemassa oleva toimittaja
            existingSupplier = toimittajaRepository.findById(Long.parseLong(supplierId)).orElse(null);
        }

        Valmistaja existingProducer = null;
        if ("-1".equals(producerId)) {
            Valmistaja newProducer = new Valmistaja();
            newProducer.setName(newProducerName.trim());
            existingProducer = valmistajaRepository.save(newProducer);
            // newProducer.setUrl(url.trim());
            // this.valmistajaRepository.save(newProducer);
            // existingProducer = newProducer;
        } else {
            existingProducer = valmistajaRepository.findById(Long.parseLong(producerId)).orElse(null);
        }
        // Luo uusi tuoteolio ja aseta sille tiedot
        Tuote newProduct = new Tuote();
        newProduct.setName(name.trim());
        newProduct.setPrice(price);
        newProduct.setDescription(description.trim());
        newProduct.setOsasto(existingDepartment);
        newProduct.setToimittaja(existingSupplier);
        newProduct.setValmistaja(existingProducer);
        // Tallenna uusi tuote tietokantaan
        this.tuoteRepository.save(newProduct);

        existingSupplier.getProducts().add(newProduct);
        existingProducer.getProducts().add(newProduct);
        // existingDepartment.getProducts().add(newProduct);

        return "redirect:/admin";
    }
}

// @PostMapping("/updateProduct/{id}")
// public String update(@PathVariable Long id) {
// tuoteRepository.updateById(id);
// return "redirect:/admin";
// }

// @PostMapping("/deleteProduct/{id}")
// public String deleteProduct(@PathVariable Long id) {
// tuoteRepository.deleteById(id);
// return "redirect:/admin";
// }
