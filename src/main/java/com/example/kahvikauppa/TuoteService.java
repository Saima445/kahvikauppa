package com.example.kahvikauppa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TuoteService {
    @Autowired
    private OsastoRepository osastoRepository;

    @Autowired
    private ToimittajaRepository toimittajaRepository;

    @Autowired
    private TuoteRepository tuoteRepository;

    @Autowired
    private ValmistajaRepository valmistajaRepository;

    public List<Tuote> getAllProducts() {
        return tuoteRepository.findAll();
    }

    public Tuote getProductById(Long id) {
        return this.tuoteRepository.findById(id).orElse(null);
    }

    public List<Osasto> getAllDepartments() {
        return osastoRepository.findAll();
    }

    public Osasto findDepartmentById(Long id) {
        return this.osastoRepository.findById(id).orElse(null);
    }

    public List<Toimittaja> getAllSuppliers() {
        return toimittajaRepository.findAll();
    }

    public Toimittaja findSupplierById(Long id) {
        return this.toimittajaRepository.findById(id).orElse(null);
    }

    public List<Valmistaja> getAllProducers() {
        return valmistajaRepository.findAll();
    }

    public Valmistaja findProducerById(Long id) {
        return this.valmistajaRepository.findById(id).orElse(null);
    }

    private Toimittaja createOrGetSupplier(String supplierId, String newSupplierName) {
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
            existingSupplier = findSupplierById(Long.parseLong(supplierId));
            // toimittajaRepository.findById(Long.parseLong(supplierId)).orElse(null);
        }
        return existingSupplier;
    }

    private Valmistaja createOrGetProducer(String producerId, String newProducerName) {
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
            existingProducer = findProducerById(Long.parseLong(producerId));
        }
        return existingProducer;
    }

    public Tuote addProduct(String name, BigDecimal price, String description,
            Long departmentId, String supplierId, String newSupplierName,
            String producerId, String newProducerName, byte[] imageBytes) {

        // Haetaan osasto, toimittaja ja valmistaja niiden ID:n perusteella
        Osasto existingDepartment = findDepartmentById(departmentId);
        Toimittaja existingSupplier = createOrGetSupplier(supplierId, newSupplierName);
        Valmistaja existingProducer = createOrGetProducer(producerId, newProducerName);
        // Tarkistetaan onko tuotetta jo tietokannassa nimen perusteella
        Tuote existingProduct = this.tuoteRepository.findByName(name);
        if (existingProduct != null) {
            // Tuote on jo olemassa, palauta se
            return existingProduct;
        }
        // Uusi tuoteolio ja sille tiedot
        Tuote newProduct = new Tuote();
        newProduct.setName(name.trim());
        newProduct.setPrice(price);
        newProduct.setDescription(description);
        newProduct.setOsasto(existingDepartment);
        newProduct.setToimittaja(existingSupplier);
        newProduct.setValmistaja(existingProducer);
        newProduct.setProductImage(imageBytes);

        existingSupplier.getProducts().add(newProduct);
        existingProducer.getProducts().add(newProduct);
        existingDepartment.getProducts().add(newProduct);
        // Tallennetaan tietokantaan
        return this.tuoteRepository.save(newProduct);
    }

    public Tuote updateProduct(Long id, String name, BigDecimal price, String description,
            Long departmentId, String supplierId, String newSupplierName,
            String producerId, String newProducerName, byte[] imageBytes) {

        Tuote product = getProductById(id);
        if (product != null) {
            // Haetaan osasto, toimittaja ja valmistaja niiden ID:n perusteella
            Osasto existingDepartment = findDepartmentById(departmentId);
            Toimittaja existingSupplier = createOrGetSupplier(supplierId, newSupplierName);
            Valmistaja existingProducer = createOrGetProducer(producerId, newProducerName);

            product.setPrice(price);
            product.setName(name.trim());
            product.setDescription(description.trim());
            product.setOsasto(existingDepartment);
            product.setToimittaja(existingSupplier);
            product.setValmistaja(existingProducer);
            product.setProductImage(imageBytes);

            existingSupplier.getProducts().add(product);
            existingProducer.getProducts().add(product);
            existingDepartment.getProducts().add(product);
            // Tallennetaan tietokantaan
            return this.tuoteRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        this.tuoteRepository.deleteById(id);
    }
}

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
// existingSupplier = findSupplierById(Long.parseLong(supplierId));
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
// existingProducer = findProducerById(Long.parseLong(producerId));
// }