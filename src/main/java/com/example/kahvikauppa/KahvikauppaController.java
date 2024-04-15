package com.example.kahvikauppa;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
        // List<Osasto> kahvilaitteet = osastoRepository.findByOsastoIDP(1);
        List<Tuote> kahvilaitteet = tuoteRepository.findProductsByOsastoID(1L); // kaikki tuotteet osasto 1 alla
        model.addAttribute("kahvilaitteet", kahvilaitteet);
        return "kahvilaitteet";
    }

    @GetMapping("/kulutustuotteet")
    public String consumerProducts(Model model) {
        List<Osasto> kulutustuotteet = osastoRepository.findByOsastoIDP(2);
        model.addAttribute("kulutustuotteet", kulutustuotteet);
        return "kulutustuotteet";
    }

    @GetMapping("/tuote-laite")
    public String individualProduct() {
        return "tuote-laite";
    }

    @GetMapping("/toimittajat")
    public String suppliers(Model model) {
        model.addAttribute("suppliers", this.toimittajaRepository.findAll());
        model.addAttribute("editSupplier", true);
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

    @GetMapping("/updateSupplier/{id}")
    public String getUpdateSupplierPage(@PathVariable Long id, Model model) {
        // Haetaan toimittaja tietokannasta toimittajan id:n perusteella
        Toimittaja supplier = toimittajaRepository.findById(id).orElse(null);

        if (supplier == null) {
            // Jos tuotetta ei löydy
            return "redirect:/toimittajat"; // Ohjataan takaisin toimittajat-sivulle
        }
        model.addAttribute("supplier", supplier);

        return "muokkaa-toimittajaa"; // Palautetaan näkymän nimi
    }

    @PostMapping("/updateSupplier/{id}")
    public String updateSupplier(@PathVariable Long id, @RequestParam String name, @RequestParam String contactPerson,
            @RequestParam String contactPersonEmail) {

        Toimittaja supplier = toimittajaRepository.findById(id).orElse(null);
        if (supplier != null) {
            supplier.setName(name);
            supplier.setContactPerson(contactPerson);
            supplier.setContactPersonEmail(contactPersonEmail);

            toimittajaRepository.save(supplier);
        }

        return "redirect:/toimittajat";
    }

    @PostMapping("/deleteSupplier/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        toimittajaRepository.deleteById(id);
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

    @GetMapping("/updateProducer/{id}")
    public String getUpdateProducerPage(@PathVariable Long id, Model model) {
        // Haetaan valmistaja tietokannasta valmistajan id:n perusteella
        Valmistaja producer = valmistajaRepository.findById(id).orElse(null);

        if (producer == null) {
            // Jos tuotetta ei löydy
            return "redirect:/valmistajat"; // Ohjataan takaisin valmistajat-sivulle
        }
        model.addAttribute("producer", producer);

        return "muokkaa-valmistajaa"; // Palautetaan näkymän nimi
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

    @GetMapping("/updateDepartment/{id}")
    public String getUpdateDepartmentPage(@PathVariable Long id, Model model) {
        // Haetaan osasto tietokannasta osaston id:n perusteella
        Osasto department = osastoRepository.findById(id).orElse(null);

        if (department == null) {
            // Jos osastoa ei löydy
            return "redirect:/osastot"; // Ohjataan takaisin osastot-sivulle
        }

        model.addAttribute("department", department);

        return "muokkaa-osastoa"; // Palautetaan näkymän nimi
    }

    @PostMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable Long id, @RequestParam String name, @RequestParam Long osastoIDP) {

        Osasto department = osastoRepository.findById(id).orElse(null);
        if (department != null) {
            department.setName(name);
            department.setOsastoIDP(osastoIDP);

            osastoRepository.save(department);
        }

        return "redirect:/osastot";
    }

    @PostMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        osastoRepository.deleteById(id);
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

        return "admin";
    }

    @PostMapping("/admin")
    public String addProduct(@RequestParam String name, @RequestParam BigDecimal price,
            @RequestParam String description, @RequestParam Long departmentId, @RequestParam String supplierId,
            @RequestParam String newSupplierName, @RequestParam String producerId,
            @RequestParam String newProducerName, @RequestParam("productImage") MultipartFile productImage,
            Model model) throws IOException {

        // Kuvan käsittely ja tallennus tietokantaan
        // byte[] imageBytes = null;
        // try {
        // imageBytes = productImage.getBytes();
        // } catch (IOException e) {
        // // Virheenkäsittely tarvittaessa
        // e.printStackTrace();
        // }
        byte[] imageBytes = productImage.getBytes();
        // String imageName = productImage.getOriginalFilename();
        // String imageType = productImage.getContentType();
        // Long imageSize = productImage.getSize();

        // Haetaan osasto, toimittaja ja valmistaja niiden ID:n perusteella
        Osasto existingDepartment = osastoRepository.findById(departmentId).orElse(null);

        Toimittaja existingSupplier = null;
        if ("new".equals(supplierId)) {
            // Uusi toimittaja
            existingSupplier = toimittajaRepository.findByName(newSupplierName.trim());
            if (existingSupplier == null) {
                // Jos toimittajaa ei löydy, luodaan uusi
                existingSupplier = new Toimittaja();
                existingSupplier.setName(newSupplierName.trim());
                existingSupplier = toimittajaRepository.save(existingSupplier);
            }
        } else {
            // Hae olemassa oleva toimittaja
            existingSupplier = toimittajaRepository.findById(Long.parseLong(supplierId)).orElse(null);
        }

        Valmistaja existingProducer = null;
        if ("new".equals(producerId)) {
            // Uusi valmistaja
            existingProducer = valmistajaRepository.findByName(newProducerName.trim());
            if (existingProducer == null) {
                // Jos valmistajaa ei löydy, luodaan uusi
                existingProducer = new Valmistaja();
                existingProducer.setName(newProducerName.trim());
                existingProducer = valmistajaRepository.save(existingProducer);
            }
        } else {
            // Hae olemassa oleva valmistaja
            existingProducer = valmistajaRepository.findById(Long.parseLong(producerId)).orElse(null);
        }

        // Uusi tuoteolio ja sille tiedot
        Tuote newProduct = new Tuote();
        newProduct.setName(name.trim());
        newProduct.setPrice(price);
        newProduct.setDescription(description.trim());
        newProduct.setOsasto(existingDepartment);
        newProduct.setToimittaja(existingSupplier);
        newProduct.setValmistaja(existingProducer);
        newProduct.setProductImage(imageBytes);
        // Tallennetaan tietokantaan
        this.tuoteRepository.save(newProduct);

        existingSupplier.getProducts().add(newProduct);
        existingProducer.getProducts().add(newProduct);
        existingDepartment.getProducts().add(newProduct);

        return "redirect:/admin";
    }

    @GetMapping("/productImage/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Tuote product = tuoteRepository.findById(id).orElse(null);
        if (product != null && product.getProductImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(product.getProductImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/updateProduct/{id}")
    public String getUpdateProductPage(@PathVariable Long id, Model model) {
        // Haetaan tuote tietokannasta tuotteen id:n perusteella
        Tuote product = tuoteRepository.findById(id).orElse(null);

        if (product == null) {
            // Jos tuotetta ei löydy
            return "redirect:/admin"; // Ohjataan takaisin admin-sivulle
        }
        // Muunnetaan byte array Base64-muotoon
        // String base64Image =
        // Base64.getEncoder().encodeToString(product.getProductImage());
        String imageURL = "/productImage/" + id;

        model.addAttribute("product", product);
        model.addAttribute("description", product.getDescription()); // Lisätään tuotteen kuvaus attribuuttina
        // model.addAttribute("base64Image", base64Image); // // tuotekuva attribuuttina
        model.addAttribute("imageURL", imageURL); // Lisätään kuvan URL attribuuttina
        model.addAttribute("departments", osastoRepository.findAll());
        model.addAttribute("suppliers", toimittajaRepository.findAll());
        model.addAttribute("producers", valmistajaRepository.findAll());
        model.addAttribute("isNewSupplier", true); // Ei luoda uutta toimittajaa muokkauksessa
        model.addAttribute("isNewProducer", true); // Ei luoda uutta valmistajaa muokkauksessa

        return "muokkaa-tuotetta"; // Palautetaan näkymän nimi
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam String name, @RequestParam BigDecimal price,
            @RequestParam String description, @RequestParam Long departmentId, @RequestParam String supplierId,
            @RequestParam String newSupplierName, @RequestParam String producerId,
            @RequestParam String newProducerName, @RequestParam("productImage") MultipartFile productImage,
            Model model) throws IOException {

        Tuote product = tuoteRepository.findById(id).orElse(null);

        if (product != null) {
            // Kuvan käsittely ja tallennus tietokantaan
            byte[] imageBytes = productImage.getBytes();
            // String imageURL = "/productImage/" + id;
            // model.addAttribute("imageURL", imageURL);

            // Haetaan osasto, toimittaja ja valmistaja niiden ID:n perusteella
            Osasto existingDepartment = osastoRepository.findById(departmentId).orElse(null);

            Toimittaja existingSupplier = null;
            if ("new".equals(supplierId)) {
                // Uusi toimittaja
                existingSupplier = toimittajaRepository.findByName(newSupplierName.trim());
                if (existingSupplier == null) {
                    // Jos toimittajaa ei löydy, luodaan uusi
                    existingSupplier = new Toimittaja();
                    existingSupplier.setName(newSupplierName.trim());
                    existingSupplier = toimittajaRepository.save(existingSupplier);
                }
            } else {
                // Hae olemassa oleva toimittaja
                existingSupplier = toimittajaRepository.findById(Long.parseLong(supplierId)).orElse(null);
            }

            Valmistaja existingProducer = null;
            if ("new".equals(producerId)) {
                // Uusi valmistaja
                existingProducer = valmistajaRepository.findByName(newProducerName.trim());
                if (existingProducer == null) {
                    // Jos valmistajaa ei löydy, luodaan uusi
                    existingProducer = new Valmistaja();
                    existingProducer.setName(newProducerName.trim());
                    existingProducer = valmistajaRepository.save(existingProducer);
                }
            } else {
                // Hae olemassa oleva valmistaja
                existingProducer = valmistajaRepository.findById(Long.parseLong(producerId)).orElse(null);
            }

            product.setPrice(price);
            product.setName(name.trim());
            product.setDescription(description.trim());
            product.setOsasto(existingDepartment);
            product.setToimittaja(existingSupplier);
            product.setValmistaja(existingProducer);
            product.setProductImage(imageBytes);
            // Tallennetaan tietokantaan
            tuoteRepository.save(product);

            existingSupplier.getProducts().add(product);
            existingProducer.getProducts().add(product);
            existingDepartment.getProducts().add(product);
        }

        return "redirect:/admin";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        tuoteRepository.deleteById(id);
        return "redirect:/admin";
    }

}
// JUURI tasolla olevat osastot (ensimmäinen taso)
// List<Osasto> rootDepartments = this.osastoRepository.findRootDepartments();
// model.addAttribute("rootDepartments", rootDepartments);

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