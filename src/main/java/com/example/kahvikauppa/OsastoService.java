package com.example.kahvikauppa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OsastoService {

    @Autowired
    private OsastoRepository osastoRepository;

    @Autowired
    private TuoteRepository tuoteRepository;

    public List<Osasto> getAllDepartments() {
        // Haetaan kaikki osastot tietokannasta
        List<Osasto> departments = this.osastoRepository.findAll();
        // Käydään läpi jokainen osasto ja lasketaan tuotteiden määrä
        for (Osasto department : departments) {
            // Long productCount =
            // tuoteRepository.countProductsByOsastoID(department.getId());
            // Päivitetään osaston tuotteiden määrä ja tallennetaan se tietokantaan
            updateProductCount(department);
        }
        // Palautetaan lista valmistajista
        return departments;
    }

    public Osasto getDepartmentById(Long id) {
        return this.osastoRepository.findById(id).orElse(null);
    }

    public Osasto addDepartment(String name, Long osastoIDP) {
        // Tarkistetaan onko osastoa jo tietokannassa
        Osasto existingDepartment = this.osastoRepository.findByName(name);
        if (existingDepartment != null) {
            // Osasto on jo olemassa, palauta se
            return existingDepartment;
        }
        // Luodaan uusi osasto
        Osasto newDepartment = new Osasto();
        newDepartment.setName(name.trim());
        newDepartment.setOsastoIDP(osastoIDP);
        // Tallennetaan uusi osasto tietokantaan
        return this.osastoRepository.save(newDepartment);
    }

    public Osasto updateDepartment(Long id, String name, Long osastoIDP) {
        // Etsitään osasto id:n perusteella
        Osasto department = getDepartmentById(id);
        if (department != null) {
            department.setName(name.trim());
            department.setOsastoIDP(osastoIDP);
            // Tallennetaan päivitetty osasto tietokantaan
            return this.osastoRepository.save(department);
        }
        return null;
    }

    public void deleteDepartment(Long id) {
        this.osastoRepository.deleteById(id);
    }

    // Metodi päivittää osaston tuotteiden määrän
    private void updateProductCount(Osasto department) {
        // Haetaan osaston tuotteiden määrä tietokannasta
        Long productCount = this.tuoteRepository.countProductsByOsastoID(department.getId());
        // Päivitetään osaston tuotteiden määrä
        department.setProductCount(productCount.intValue());
        // Tallennetaan päivitetty osasto tietokantaan
        this.osastoRepository.save(department);
    }

}
