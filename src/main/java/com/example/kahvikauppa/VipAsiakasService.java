package com.example.kahvikauppa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VipAsiakasService {

    @Autowired
    private VipAsiakasRepository vipAsiakasRepository;

    public List<VipAsiakas> getAllVipCustomers() {
        // Haetaan kaikki vipasiakkaas tietokannasta
        List<VipAsiakas> vipCustomers = this.vipAsiakasRepository.findAll();
        // Palautetaan lista valmistajista
        return vipCustomers;
    }

    public VipAsiakas findByEmail(String email) {
        return this.vipAsiakasRepository.findByEmail(email);
    }

    public VipAsiakas addVipCustomer(String firstname, String lastname, String email) {
        // Tarkistetaan onko asiakas jo tietokannassa
        VipAsiakas existingVIP = findByEmail(email);
        if (existingVIP != null) {
            // Asiakas jo olemassa, palauta se
            throw new RuntimeException();
        }
        // Luodaan uusi asiakas
        VipAsiakas newVIP = new VipAsiakas();
        newVIP.setFirstname(firstname.trim());
        newVIP.setLastname(lastname.trim());
        newVIP.setEmail(email.trim());
        // Tallennetaan uusi asiakas tietokantaan
        return this.vipAsiakasRepository.save(newVIP);
    }

    public void deleteVipCustomer(Long id) {
        this.vipAsiakasRepository.deleteById(id);
    }

}
