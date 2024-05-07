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
public class OsastoController {

    // @Autowired
    // private OsastoRepository osastoRepository;
    // @Autowired
    // private TuoteRepository tuoteRepository;
    @Autowired
    private OsastoService osastoService;

    // @GetMapping("/osastot")
    // public String departments(Model model) {
    // List<Osasto> departments = this.osastoRepository.findAll();
    // // Käydään läpi jokainen osasto ja lasketaan tuotteiden määrä
    // for (Osasto department : departments) {
    // Long productCount =
    // tuoteRepository.countProductsByOsastoID(department.getId());
    // department.setProductCount(productCount.intValue());
    // }
    // model.addAttribute("departments", this.osastoRepository.findAll());
    // return "osastot";
    // }

    @GetMapping("/osastot")
    public String departments(@ModelAttribute("message") String message, Model model) {
        List<Osasto> departments = this.osastoService.getAllDepartments();
        if (!message.isEmpty()) {
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", false);
        }
        model.addAttribute("departments", departments);
        return "osastot";
    }

    // @PostMapping("/osastot")
    // public String addDepartment(@RequestParam String name, @RequestParam Long
    // osastoIDP) {
    // Osasto newOsasto = new Osasto();
    // newOsasto.setName(name.trim());
    // newOsasto.setOsastoIDP(osastoIDP);

    // this.osastoRepository.save(newOsasto);

    // return "redirect:/osastot";
    // }
    @PostMapping("/osastot")
    public String addDepartment(@RequestParam String name, @RequestParam Long osastoIDP) {
        this.osastoService.addDepartment(name, osastoIDP);
        return "redirect:/osastot";
    }

    // @GetMapping("/updateDepartment/{id}")
    // public String getUpdateDepartmentPage(@PathVariable Long id, Model model) {
    // // Haetaan osasto tietokannasta osaston id:n perusteella
    // Osasto department = osastoRepository.findById(id).orElse(null);

    // if (department == null) {
    // // Jos osastoa ei löydy ohjataan takaisin osastot-sivulle
    // return "redirect:/osastot";
    // }

    // model.addAttribute("department", department);

    // return "muokkaa-osastoa";
    // }
    @GetMapping("/updateDepartment/{id}")
    public String getUpdateDepartmentPage(@PathVariable Long id, Model model) {
        Osasto department = this.osastoService.getDepartmentById(id);
        if (department != null) {
            model.addAttribute("department", department);
            return "muokkaa-osastoa";
        }
        return "redirect:/osastot";
    }

    // @PostMapping("/updateDepartment/{id}")
    // public String updateDepartment(@PathVariable Long id, @RequestParam String
    // name, @RequestParam Long osastoIDP) {

    // Osasto department = osastoRepository.findById(id).orElse(null);
    // if (department != null) {
    // department.setName(name);
    // department.setOsastoIDP(osastoIDP);

    // osastoRepository.save(department);
    // }

    // return "redirect:/osastot";
    // }
    @PostMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable Long id, @RequestParam String name, @RequestParam Long osastoIDP) {
        this.osastoService.updateDepartment(id, name, osastoIDP);
        return "redirect:/osastot";
    }

    // @PostMapping("/deleteDepartment/{id}")
    // public String deleteDepartment(@PathVariable Long id) {
    // osastoRepository.deleteById(id);
    // return "redirect:/osastot";
    // }
    @PostMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message;
        try {
            this.osastoService.deleteDepartment(id);
            message = "Osasto poistettu onnistuneesti tietokannasta.";
        } catch (RuntimeException e) {
            message = "Osastoa ei voi poistaa, koska siihen liittyy tuotteita tietokannassa (ks. tuotteiden määrä valikoimassa).";
        }
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/osastot";
    }
}