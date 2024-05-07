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

    // @Autowired
    // private VipAsiakasRepository vipAsiakasRepository;
    @Autowired
    VipAsiakasService vipAsiakasService;

    // @GetMapping("/vipasiakas")
    // public String vipCustomer(Model model,
    // @RequestParam(required = false, name = "error") String error,
    // @RequestParam(required = false, name = "success") String success) {

    // if (error != null && !error.isEmpty()) {
    // model.addAttribute("error", error);
    // }
    // if (success != null && !success.isEmpty()) {
    // model.addAttribute("success",
    // success);
    // }
    // // else {
    // // model.addAttribute("error", "");
    // // model.addAttribute("success", "");
    // // }
    // return "vipasiakas";
    // }
    @GetMapping("/vipasiakas")
    public String vipCustomer(@ModelAttribute("message") String message, Model model) {
        if (!message.isEmpty()) {
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", false);
        }
        return "vipasiakas";
    }

    // @PostMapping("/vipasiakas")
    // public String addVipCunstomer(@RequestParam String firstname, @RequestParam
    // String lastname,
    // @RequestParam String email, @RequestParam(required = false, name = "error")
    // String agree1,
    // @RequestParam(required = false, name = "error") String agree2) {

    // // Tarkistetaan onko uutiskirjeen tilaus on hyväksytty
    // if (agree1 == null || !agree1.equals("accepted")) {
    // return "redirect:/vipasiakas?error=newsletter_not_accepted";
    // }

    // // Tarkistetaan onko sopimusehdot on hyväksytty
    // if (agree2 == null || !agree2.equals("accepted")) {
    // return "redirect:/vipasiakas?error=terms_not_accepted";
    // }

    // VipAsiakas existingVIP = vipAsiakasRepository.findByEmail(email);

    // if (existingVIP != null) {
    // return "redirect:/vipasiakas?message=email_already_registered";
    // }

    // if (existingVIP == null) {
    // VipAsiakas newVipCustomer = new VipAsiakas();
    // newVipCustomer.setFirstname(firstname.trim());
    // newVipCustomer.setLastname(lastname.trim());
    // newVipCustomer.setEmail(email.trim());

    // this.vipAsiakasRepository.save(newVipCustomer);
    // }

    // return "redirect:/vipasiakas?success=email_registered";
    // }
    // --
    // @PostMapping("/vipasiakas")
    // public String addVipCustomer(@RequestParam String firstname, @RequestParam
    // String lastname,
    // @RequestParam String email, @RequestParam(required = false) String agree1,
    // @RequestParam(required = false) String agree2, RedirectAttributes
    // redirectAttributes) {

    // // Tarkistetaan onko uutiskirjeen tilaus on hyväksytty
    // if (agree1 == null || !agree1.equals("accepted")) {
    // redirectAttributes.addAttribute("error", "newsletter_not_accepted");
    // return "redirect:/vipasiakas";
    // }

    // // Tarkistetaan onko sopimusehdot on hyväksytty
    // if (agree2 == null || !agree2.equals("accepted")) {
    // redirectAttributes.addAttribute("error", "terms_not_accepted");
    // return "redirect:/vipasiakas";
    // }

    // VipAsiakas existingVIP = vipAsiakasRepository.findByEmail(email);

    // if (existingVIP != null) {
    // redirectAttributes.addAttribute("message", "email_already_registered");
    // return "redirect:/vipasiakas";
    // }

    // if (existingVIP == null) {
    // VipAsiakas newVipCustomer = new VipAsiakas();
    // newVipCustomer.setFirstname(firstname.trim());
    // newVipCustomer.setLastname(lastname.trim());
    // newVipCustomer.setEmail(email.trim());

    // this.vipAsiakasRepository.save(newVipCustomer);
    // }

    // redirectAttributes.addAttribute("success", "email_registered");
    // return "redirect:/vipasiakas";
    // }
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
    // @PostMapping("/vipasiakas")
    // public String addVipCustomer(@RequestParam String firstname, @RequestParam
    // String lastname,
    // @RequestParam String email, Model model, RedirectAttributes
    // redirectAttributes) {
    // // this.vipAsiakasService.addVipCustomer(firstname, lastname, email);
    // // return "redirect:/vipasiakas";
    // try {
    // this.vipAsiakasService.addVipCustomer(firstname, lastname, email);
    // // Onnistunut rekisteröinti
    // redirectAttributes.addFlashAttribute("success",
    // "Kiitos, kun liityit VIP-asiakkaaksemme! Saat kohta sähköpostiisi
    // vahvistusviestin meiltä.");
    // } catch (RuntimeException e) {
    // // Virhe tapahtui
    // redirectAttributes.addFlashAttribute("error", "Sähköpostiosoite on jo
    // rekisteröity");
    // }
    // return "redirect:/vipasiakas";
    // }

    // @GetMapping("/vipasiakkaat")
    // public String vipCustomers(Model model) {
    // model.addAttribute("vipCustomers", this.vipAsiakasRepository.findAll());
    // return "vipasiakkaat";
    // }
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

    // @PostMapping("/vipasiakkaat")
    // public String addVipCustomer(@RequestParam String firstname, @RequestParam
    // String lastname,
    // @RequestParam String email, RedirectAttributes redirectAttributes) {

    // VipAsiakas existingVIP = vipAsiakasRepository.findByEmail(email);

    // if (existingVIP != null) {
    // redirectAttributes.addAttribute("message", "email_already_registered");
    // return "redirect:/vipasiakkaat";
    // }

    // if (existingVIP == null) {
    // VipAsiakas newVipCustomer = new VipAsiakas();
    // newVipCustomer.setFirstname(firstname.trim());
    // newVipCustomer.setLastname(lastname.trim());
    // newVipCustomer.setEmail(email.trim());

    // this.vipAsiakasRepository.save(newVipCustomer);
    // }

    // return "redirect:/vipasiakkaat";
    // }
    @PostMapping("/vipasiakkaat")
    public String addVipCustomer2(@RequestParam String firstname, @RequestParam String lastname,
            @RequestParam String email) {
        this.vipAsiakasService.addVipCustomer(firstname, lastname, email);
        return "redirect:/vipasiakkaat";
    }

    // @PostMapping("/deleteVipCustomer/{id}")
    // public String deleteVipCustomer(@PathVariable Long id) {
    // vipAsiakasRepository.deleteById(id);
    // return "redirect:/vipasiakkaat";
    // }
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
