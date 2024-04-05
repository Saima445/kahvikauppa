package com.example.kahvikauppa;

import java.math.BigDecimal;
import java.sql.Blob;
import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

// @NamedEntityGraph(name = "Motion.maker", attributeNodes = {
// @NamedAttributeNode("maker") })

@Table(name = "tuote_details")
public class Tuote extends AbstractPersistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "productPicture")
    private Blob productPicture;

    @ManyToOne
    // @JoinColumn(name = "osasto.id", referencedColumnName = "tuote.id")
    private Osasto osasto;

    @ManyToOne
    // @JoinColumn(name = "toimittaja.id", referencedColumnName = "tuote.id")
    private Toimittaja toimittaja;

    @ManyToOne
    // @JoinColumn(name = "valmistaja.id", referencedColumnName = "tuote.id")
    private Valmistaja valmistaja;
}
