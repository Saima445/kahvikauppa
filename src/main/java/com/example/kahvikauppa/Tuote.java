package com.example.kahvikauppa;

import java.math.BigDecimal;
import java.sql.Blob;
import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.NamedAttributeNode;
// import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "tuote_details")
public class Tuote extends AbstractPersistable<Long> {

    @Column(name = "name")
    private String name;
    // ALTER TABLE TUOTE_DETAILS ALTER COLUMN NAME VARCHAR(500)

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    // muokattu h2-tietokantaan kuvaus-kentän merkkien pituutta, jotta käyttäjä voi
    // kirjoittaa pidemmän kuvauksen
    // ALTER TABLE TUOTE_DETAILS ALTER COLUMN DESCRIPTION VARCHAR(2000);

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "productImage")
    @Lob
    private byte[] productImage;

    // TARVITAANKO?
    // @Column(name = "imageName")
    // private String imageName;

    // @Column(name = "imageType")
    // private String imageType;

    // @Column(name = "imageSize")
    // private Long imageSize;

    @ManyToOne
    private Osasto osasto;

    @ManyToOne
    private Toimittaja toimittaja;

    @ManyToOne
    private Valmistaja valmistaja;
}
