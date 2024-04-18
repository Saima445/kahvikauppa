package com.example.kahvikauppa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VipAsiakasController {

    @Autowired
    private VipAsiakasRepository vipAsiakasRepository;

    @GetMapping("/vipasiakas")
    public String vipCustomer(Model model, @RequestParam(required = false, name = "error") String error,
            @RequestParam(required = false, name = "message") String message,
            @RequestParam(required = false, name = "success") String success) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", "Sopimusehdot ja uutiskirje tulee hyväksyä");
        }
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", "Sähköpostiosoite on jo rekisteröity");
        }
        if (success != null && !success.isEmpty()) {
            model.addAttribute("success",
                    "Kiitos, kun liityit VIP-asiakkaaksemme! Saat kohta sähköpostiisi vahvistusviestin meiltä.");
        } else {
            model.addAttribute("error", ""); // Tyhjä virheviesti, jos virhettä ei ole
            model.addAttribute("message", "");
            model.addAttribute("success", "");
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

    @PostMapping("/vipasiakas")
    public String addVipCustomer(@RequestParam String firstname, @RequestParam String lastname,
            @RequestParam String email, @RequestParam(required = false) String agree1,
            @RequestParam(required = false) String agree2, RedirectAttributes redirectAttributes) {

        // Tarkistetaan onko uutiskirjeen tilaus on hyväksytty
        if (agree1 == null || !agree1.equals("accepted")) {
            redirectAttributes.addAttribute("error", "newsletter_not_accepted");
            return "redirect:/vipasiakas";
        }

        // Tarkistetaan onko sopimusehdot on hyväksytty
        if (agree2 == null || !agree2.equals("accepted")) {
            redirectAttributes.addAttribute("error", "terms_not_accepted");
            return "redirect:/vipasiakas";
        }

        VipAsiakas existingVIP = vipAsiakasRepository.findByEmail(email);

        if (existingVIP != null) {
            redirectAttributes.addAttribute("message", "email_already_registered");
            return "redirect:/vipasiakas";
        }

        if (existingVIP == null) {
            VipAsiakas newVipCustomer = new VipAsiakas();
            newVipCustomer.setFirstname(firstname.trim());
            newVipCustomer.setLastname(lastname.trim());
            newVipCustomer.setEmail(email.trim());

            this.vipAsiakasRepository.save(newVipCustomer);
        }

        redirectAttributes.addAttribute("success", "email_registered");
        return "redirect:/vipasiakas";
    }

    @GetMapping("/vipasiakkaat")
    public String vipCustomers(Model model) {
        model.addAttribute("vipCustomers", this.vipAsiakasRepository.findAll());
        return "vipasiakkaat";
    }

    @PostMapping("/vipasiakkaat")
    public String addVipCustomer(@RequestParam String firstname, @RequestParam String lastname,
            @RequestParam String email, RedirectAttributes redirectAttributes) {

        VipAsiakas existingVIP = vipAsiakasRepository.findByEmail(email);

        if (existingVIP != null) {
            redirectAttributes.addAttribute("message", "email_already_registered");
            return "redirect:/vipasiakkaat";
        }

        if (existingVIP == null) {
            VipAsiakas newVipCustomer = new VipAsiakas();
            newVipCustomer.setFirstname(firstname.trim());
            newVipCustomer.setLastname(lastname.trim());
            newVipCustomer.setEmail(email.trim());

            this.vipAsiakasRepository.save(newVipCustomer);
        }

        return "redirect:/vipasiakkaat";
    }

    @PostMapping("/deleteVipCustomer/{id}")
    public String deleteVipCustomer(@PathVariable Long id) {
        vipAsiakasRepository.deleteById(id);
        return "redirect:/vipasiakkaat";
    }
    // @GetMapping("/updateProducer/{id}")
    // public String getUpdateProducerPage(@PathVariable Long id, Model model) {
    // // Haetaan valmistaja tietokannasta valmistajan id:n perusteella
    // Valmistaja producer = valmistajaRepository.findById(id).orElse(null);

    // if (producer == null) {
    // // Jos tuotetta ei löydy ohjataan takaisin valmistajat-sivulle
    // return "redirect:/valmistajat";
    // }
    // model.addAttribute("producer", producer);

    // return "muokkaa-valmistajaa";
    // }

    // @PostMapping("/updateProducer/{id}")
    // public String updateProducer(@PathVariable Long id, @RequestParam String
    // name, @RequestParam String url) {

    // Valmistaja producer = valmistajaRepository.findById(id).orElse(null);
    // if (producer != null) {
    // producer.setName(name);
    // producer.setUrl(url);

    // valmistajaRepository.save(producer);
    // }

    // return "redirect:/valmistajat";
    // }

    // @PostMapping("/deleteProducer/{id}")
    // public String deleteProducer(@PathVariable Long id) {
    // valmistajaRepository.deleteById(id);
    // return "redirect:/valmistajat";
    // }

}
