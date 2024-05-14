package com.example.kahvikauppa;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class KahvikauppaController {

    @Autowired
    private TilausRepository tilausRepository;

    @Autowired
    private TuoteService tuoteService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/kahvilaitteet")
    public String machines(Model model, @RequestParam(defaultValue = "0") int page) {
        // List<Tuote> kahvilaitteet = tuoteRepository.findProductsByOsastoID(1L); //
        int pageSize = 9; // Haluttu rivien määrä yhdellä sivulla
        Page<Tuote> tuotePage = tuoteService.getProductsKahvilaitteetPage(page, pageSize);
        List<Tuote> kahvilaitteet = tuotePage.getContent();
        model.addAttribute("kahvilaitteet", kahvilaitteet);
        model.addAttribute("totalPages", tuotePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageNumber", page + 1); // Lisää sivunumeron malliin
        // kaikki tuotteet osasto 1 alla
        List<Tuote> kaikkiKahvilaitteet = tuoteService.getProductsKahvilaitteet();
        model.addAttribute("kaikkiKahvilaitteet", kaikkiKahvilaitteet);
        return "kahvilaitteet";
    }

    @GetMapping("/laite/{id}")
    public String individualMachinePage(@PathVariable Long id, Model model) {
        Tuote product = tuoteService.getProductById(id);

        if (product == null) {
            return "redirect:/kahvilaitteet";
        }
        String imageURL = "/productImage/" + id;
        model.addAttribute("imageURL", imageURL);
        model.addAttribute("product", product);

        return "laite";
    }

    @GetMapping("/kulutustuotteet")
    public String consumerProducts(Model model, @RequestParam(defaultValue = "0") int page) {
        // kaikki tuotteet osasto 2 alla, EI TOIMI OSASTO 7 ALLE OLEVILLE!
        // List<Tuote> kulutustuotteet = tuoteRepository.findProductsByOsastoID(2L);
        // model.addAttribute("kulutustuotteet", kulutustuotteet);

        // Haetaan tuotteet osaston 2 alla
        // List<Tuote> osasto2Tuotteet = tuoteRepository.findProductsByOsastoID(2L);
        // // Haetaan tuotteet osaston 7 alla
        // List<Tuote> osasto7Tuotteet = tuoteRepository.findProductsByOsastoID(7L);
        // // Yhdistetään
        // osasto2Tuotteet.addAll(osasto7Tuotteet);
        // // poistetaan mahdolliset duplikaatit
        // Set<Tuote> yhdistetytTuotteet = new LinkedHashSet<>(osasto2Tuotteet);

        // model.addAttribute("kulutustuotteet", new ArrayList<>(yhdistetytTuotteet));

        int pageSize = 9; // Haluttu rivien määrä yhdellä sivulla
        Page<Tuote> tuotePage = tuoteService.getProductsKulutustuotteetPage(page, pageSize);
        List<Tuote> kulutustuotteet = tuotePage.getContent();
        model.addAttribute("kulutustuotteet", kulutustuotteet);
        model.addAttribute("totalPages", tuotePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageNumber", page + 1); // Lisää sivunumeron malliin
        List<Tuote> kaikkiKulutustuotteet = tuoteService.getProductsKulutustuotteet();
        model.addAttribute("kaikkiKulutustuotteet", kaikkiKulutustuotteet);
        return "kulutustuotteet";
    }

    @GetMapping("/tuote/{id}")
    public String individualProductPage(@PathVariable Long id, Model model) {
        Tuote product = tuoteService.getProductById(id);

        if (product == null) {
            return "redirect:/kulutustuotteet";
        }
        String imageURL = "/productImage/" + id;
        model.addAttribute("imageURL", imageURL);
        model.addAttribute("product", product);

        return "tuote";
    }

    // KUVIEN PURKAMINEN
    @GetMapping("/productImage/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Tuote product = tuoteService.getProductById(id);
        if (product != null && product.getProductImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(product.getProductImage(), headers,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // HAKU-TOIMINNALLISUUDET
    @GetMapping("/searchMachines")
    public String searchMachines(@RequestParam String keyword, Model model) {
        List<Tuote> machines = tuoteService.searchMachines(keyword);
        List<Tuote> kaikkiKahvilaitteet = tuoteService.getProductsKahvilaitteet();
        if (machines.isEmpty()) {
            model.addAttribute("keyword", "Hakuehtoa vastaavia tuotteita ei löytynyt kahvilaitteistamme");
            model.addAttribute("keywordExists", true);
        }
        model.addAttribute("kahvilaitteet", machines);
        model.addAttribute("keywordExists", true);
        model.addAttribute("kaikkiKahvilaitteet", kaikkiKahvilaitteet);
        return "kahvilaitteet";
    }

    @GetMapping("/searchConsumerProducts")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<Tuote> products = tuoteService.searchConsumerProducts(keyword);
        List<Tuote> kaikkiKulutustuotteet = tuoteService.getProductsKulutustuotteet();
        if (products.isEmpty()) {
            model.addAttribute("keyword", "Hakuehtoa vastaavia tuotteita ei löytynyt kulutustuotteistamme");
            model.addAttribute("keywordExists", true);
        }
        model.addAttribute("kulutustuotteet", products);
        model.addAttribute("keywordExists", true);
        model.addAttribute("kaikkiKulutustuotteet", kaikkiKulutustuotteet);
        return "kulutustuotteet";
    }

    // TILAUSLISTAN VASTAANOTTAMISEN TOIMINNALLISUUDET
    @PostMapping("/sendOrder")
    public String sendOrder(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("TIEDOSTON SISÄLTÖ: " + new String(file.getBytes()));
        Tilaus newOrder = new Tilaus();
        newOrder.setContent(file.getBytes());
        this.tilausRepository.save(newOrder);
        return "redirect:/kulutustuotteet";
    }

}
