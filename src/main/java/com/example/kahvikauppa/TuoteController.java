package com.example.kahvikauppa;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TuoteController {

    // @Autowired
    // private OsastoRepository osastoRepository;
    // @Autowired
    // private ToimittajaRepository toimittajaRepository;
    // @Autowired
    // private TuoteRepository tuoteRepository;
    // @Autowired
    // private ValmistajaRepository valmistajaRepository;
    @Autowired
    private TuoteService tuoteService;

    // @GetMapping("/admin")
    // public String admin(Model model) {

    // model.addAttribute("products", this.tuoteRepository.findAll());

    // model.addAttribute("departments", this.osastoRepository.findAll());
    // model.addAttribute("suppliers", this.toimittajaRepository.findAll());
    // model.addAttribute("producers", this.valmistajaRepository.findAll());
    // model.addAttribute("isNewSupplier", true);
    // model.addAttribute("isNewProducer", true);

    // return "admin";
    // }
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", this.tuoteService.getAllProducts());
        model.addAttribute("departments", this.tuoteService.getAllDepartments());
        model.addAttribute("suppliers", this.tuoteService.getAllSuppliers());
        model.addAttribute("producers", this.tuoteService.getAllProducers());
        model.addAttribute("isNewSupplier", true);
        model.addAttribute("isNewProducer", true);

        return "admin";
    }

    // @PostMapping("/admin")
    // public String addProduct(@RequestParam String name, @RequestParam BigDecimal
    // price,
    // @RequestParam String description, @RequestParam Long departmentId,
    // @RequestParam String supplierId,
    // @RequestParam String newSupplierName, @RequestParam String producerId,
    // @RequestParam String newProducerName, @RequestParam("productImage")
    // MultipartFile productImage,
    // Model model) throws IOException {

    // // Kuvan käsittely ja tallennus tietokantaan
    // // byte[] imageBytes = null;
    // // try {
    // // imageBytes = productImage.getBytes();
    // // } catch (IOException e) {
    // // // Virheenkäsittely tarvittaessa
    // // e.printStackTrace();
    // // }
    // byte[] imageBytes = productImage.getBytes();
    // // String imageName = productImage.getOriginalFilename();
    // // String imageType = productImage.getContentType();
    // // Long imageSize = productImage.getSize();

    // // Haetaan osasto, toimittaja ja valmistaja niiden ID:n perusteella
    // Osasto existingDepartment =
    // osastoRepository.findById(departmentId).orElse(null);

    // Toimittaja existingSupplier = null;
    // if ("new".equals(supplierId)) {
    // // Uusi toimittaja
    // existingSupplier = toimittajaRepository.findByName(newSupplierName.trim());
    // if (existingSupplier == null) {
    // // Jos toimittajaa ei löydy, luodaan uusi
    // existingSupplier = new Toimittaja();
    // existingSupplier.setName(newSupplierName.trim());
    // existingSupplier = toimittajaRepository.save(existingSupplier);
    // }
    // } else {
    // // Hae olemassa oleva toimittaja
    // existingSupplier =
    // toimittajaRepository.findById(Long.parseLong(supplierId)).orElse(null);
    // }

    // Valmistaja existingProducer = null;
    // if ("new".equals(producerId)) {
    // // Uusi valmistaja
    // existingProducer = valmistajaRepository.findByName(newProducerName.trim());
    // if (existingProducer == null) {
    // // Jos valmistajaa ei löydy, luodaan uusi
    // existingProducer = new Valmistaja();
    // existingProducer.setName(newProducerName.trim());
    // existingProducer = valmistajaRepository.save(existingProducer);
    // }
    // } else {
    // // Hae olemassa oleva valmistaja
    // existingProducer =
    // valmistajaRepository.findById(Long.parseLong(producerId)).orElse(null);
    // }

    // // Uusi tuoteolio ja sille tiedot
    // Tuote newProduct = new Tuote();
    // newProduct.setName(name.trim());
    // newProduct.setPrice(price);
    // newProduct.setDescription(description);
    // newProduct.setOsasto(existingDepartment);
    // newProduct.setToimittaja(existingSupplier);
    // newProduct.setValmistaja(existingProducer);
    // newProduct.setProductImage(imageBytes);
    // // Tallennetaan tietokantaan
    // this.tuoteRepository.save(newProduct);

    // existingSupplier.getProducts().add(newProduct);
    // existingProducer.getProducts().add(newProduct);
    // existingDepartment.getProducts().add(newProduct);

    // return "redirect:/admin";
    // }
    @PostMapping("/admin")
    public String addProduct(@RequestParam String name, @RequestParam BigDecimal price,
            @RequestParam String description, @RequestParam Long departmentId, @RequestParam String supplierId,
            @RequestParam String newSupplierName, @RequestParam String producerId,
            @RequestParam String newProducerName, @RequestParam("productImage") MultipartFile productImage,
            Model model) throws IOException {

        byte[] imageBytes = productImage.getBytes();

        // TuoteService uuden tuotteen lisäämiseen
        this.tuoteService.addProduct(name, price, description, departmentId,
                supplierId, newSupplierName, producerId, newProducerName,
                imageBytes);

        return "redirect:/admin";
    }

    @GetMapping("/updateProduct/{id}")
    public String getUpdateProductPage(@PathVariable Long id, Model model) {
        // Haetaan tuote tietokannasta tuotteen id:n perusteella
        Tuote product = this.tuoteService.getProductById(id);
        if (product == null) {
            // Jos tuotetta ei löydy hjataan takaisin admin-sivulle
            return "redirect:/admin";
        }
        String imageURL = "/productImage/" + id;

        model.addAttribute("product", product);
        model.addAttribute("description", product.getDescription()); // Lisätään tuotteen kuvaus attribuuttina
        model.addAttribute("imageURL", imageURL); // Lisätään kuvan URL attribuuttina
        model.addAttribute("departments", this.tuoteService.getAllDepartments());
        model.addAttribute("suppliers", this.tuoteService.getAllSuppliers());
        model.addAttribute("producers", this.tuoteService.getAllProducers());
        model.addAttribute("isNewSupplier", true); // Ei luoda uutta toimittajaa muokkauksessa
        model.addAttribute("isNewProducer", true); // Ei luoda uutta valmistajaa muokkauksessa

        return "muokkaa-tuotetta"; // Palautetaan näkymän nimi
    }

    // @PostMapping("/updateProduct/{id}")
    // public String updateProduct(@PathVariable Long id, @RequestParam String name,
    // @RequestParam BigDecimal price,
    // @RequestParam String description, @RequestParam Long departmentId,
    // @RequestParam String supplierId,
    // @RequestParam String newSupplierName, @RequestParam String producerId,
    // @RequestParam String newProducerName, @RequestParam("productImage")
    // MultipartFile productImage,
    // Model model) throws IOException {

    // Tuote product = tuoteRepository.findById(id).orElse(null);

    // if (product != null) {
    // // Kuvan käsittely ja tallennus tietokantaan
    // byte[] imageBytes = productImage.getBytes();
    // // Koitetaan tällä jos olemassa oleva kuva tulisi inputtiin, ei toimi
    // // String imageURL = "/productImage/" + id;
    // // model.addAttribute("imageURL", imageURL);

    // // Haetaan osasto, toimittaja ja valmistaja niiden ID:n perusteella
    // Osasto existingDepartment =
    // osastoRepository.findById(departmentId).orElse(null);

    // Toimittaja existingSupplier = null;
    // if ("new".equals(supplierId)) {
    // // Uusi toimittaja
    // existingSupplier = toimittajaRepository.findByName(newSupplierName.trim());
    // if (existingSupplier == null) {
    // // Jos toimittajaa ei löydy, luodaan uusi
    // existingSupplier = new Toimittaja();
    // existingSupplier.setName(newSupplierName.trim());
    // existingSupplier = toimittajaRepository.save(existingSupplier);
    // }
    // } else {
    // // Hae olemassa oleva toimittaja
    // existingSupplier =
    // toimittajaRepository.findById(Long.parseLong(supplierId)).orElse(null);
    // }

    // Valmistaja existingProducer = null;
    // if ("new".equals(producerId)) {
    // // Uusi valmistaja
    // existingProducer = valmistajaRepository.findByName(newProducerName.trim());
    // if (existingProducer == null) {
    // // Jos valmistajaa ei löydy, luodaan uusi
    // existingProducer = new Valmistaja();
    // existingProducer.setName(newProducerName.trim());
    // existingProducer = valmistajaRepository.save(existingProducer);
    // }
    // } else {
    // // Hae olemassa oleva valmistaja
    // existingProducer =
    // valmistajaRepository.findById(Long.parseLong(producerId)).orElse(null);
    // }

    // product.setPrice(price);
    // product.setName(name.trim());
    // product.setDescription(description.trim());
    // product.setOsasto(existingDepartment);
    // product.setToimittaja(existingSupplier);
    // product.setValmistaja(existingProducer);
    // product.setProductImage(imageBytes);
    // // Tallennetaan tietokantaan
    // tuoteRepository.save(product);

    // existingSupplier.getProducts().add(product);
    // existingProducer.getProducts().add(product);
    // existingDepartment.getProducts().add(product);
    // }

    // return "redirect:/admin";
    // }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam String name, @RequestParam BigDecimal price,
            @RequestParam String description, @RequestParam Long departmentId, @RequestParam String supplierId,
            @RequestParam String newSupplierName, @RequestParam String producerId,
            @RequestParam String newProducerName, @RequestParam("productImage") MultipartFile productImage,
            Model model) throws IOException {

        byte[] imageBytes = productImage.getBytes();

        // TuoteService uuden tuotteen lisäämiseen
        this.tuoteService.updateProduct(id, name, price, description, departmentId,
                supplierId, newSupplierName, producerId, newProducerName,
                imageBytes);

        return "redirect:/admin";
    }

    // @PostMapping("/deleteProduct/{id}")
    // public String deleteProduct(@PathVariable Long id) {
    // tuoteRepository.deleteById(id);
    // return "redirect:/admin";
    // }
    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.tuoteService.deleteProduct(id);
        return "redirect:/admin";
    }

}
// KUVAN BYTEN PURKAMISEEN VAIHTOEHTOINEN METODI
// Muunnetaan byte array Base64-muotoon
// String base64Image =
// Base64.getEncoder().encodeToString(product.getProductImage());
// model.addAttribute("base64Image", base64Image); // // tuotekuva attribuuttina