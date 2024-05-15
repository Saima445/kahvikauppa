package com.example.kahvikauppa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VipAsiakasController {

    @Autowired
    VipAsiakasService vipAsiakasService;

    @GetMapping("/vipasiakas")
    public String vipCustomer(@ModelAttribute("message") String message, Model model) {
        if (!message.isEmpty()) {
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", false);
        }
        return "vipasiakas";
    }

    @PostMapping("/vipasiakas")
    public String addVipCustomer(@RequestParam String firstname, @RequestParam String lastname,
            @RequestParam String email, RedirectAttributes redirectAttributes) {
        VipAsiakas existingVIP = this.vipAsiakasService.findByEmail(email);
        String message;
        if (existingVIP != null) {
            message = "Sähköpostiosoite on jo rekisteröity";
        } else {
            this.vipAsiakasService.addVipCustomer(firstname, lastname, email);
            message = "Kiitos, kun liityit VIP-asiakkaaksemme! Saat kohta sähköpostiisi vahvistusviestin meiltä.";
        }
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/vipasiakas";
    }

    @GetMapping("/vipasiakkaat")
    public String vipCustomers(@ModelAttribute("message") String message, Model model) {
        List<VipAsiakas> vipCustomers = this.vipAsiakasService.getAllVipCustomers();
        if (!message.isEmpty()) {
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", false);
        }
        model.addAttribute("vipCustomers", vipCustomers);
        return "vipasiakkaat";
    }

    @PostMapping("/vipasiakkaat")
    public String addVipCustomer2(@RequestParam String firstname, @RequestParam String lastname,
            @RequestParam String email) {
        this.vipAsiakasService.addVipCustomer(firstname, lastname, email);
        return "redirect:/vipasiakkaat";
    }

    @PostMapping("/deleteVipCustomer/{id}")
    public String deleteVipCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message;
        try {
            this.vipAsiakasService.deleteVipCustomer(id);
            message = "Asiakas poistettu onnistuneesti tietokannasta.";
        } catch (RuntimeException e) {
            message = "Jotain meni pieleen ja asiakasta ei voitu poistaa tietokannasta.";
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/vipasiakkaat";
    }

}
