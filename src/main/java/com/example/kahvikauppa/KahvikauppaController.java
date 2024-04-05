package com.example.kahvikauppa;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/valmistajat")
    public String producers(Model model) {
        model.addAttribute("producers", this.valmistajaRepository.findAll());
        return "valmistajat";
    }

    @GetMapping("/osastot")
    public String departments(Model model) {
        model.addAttribute("departments", this.osastoRepository.findAll());
        return "osastot";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", this.tuoteRepository.findAll());
        return "admin";
    }

    @PostMapping("/admin") // lisää kuvakenttä myöhemmin!!
    public String addProduct(@RequestParam String name, @RequestParam BigDecimal price,
            @RequestParam String description, @RequestParam String department, @RequestParam String supplier,
            @RequestParam String producer) {

        Tuote newProduct = new Tuote();
        newProduct.setName(name.trim());
        newProduct.setPrice(price);
        newProduct.setDescription(description.trim());
        // newProduct.setDepartment(department.trim());
        // newProduct.setSupplier(supplier.trim());
        // newProduct.setProducer(producer.trim());

        this.tuoteRepository.save(newProduct);

        return "redirect:/admin";
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

}
