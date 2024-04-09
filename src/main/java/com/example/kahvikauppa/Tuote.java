package com.example.kahvikauppa;

import java.math.BigDecimal;
import java.sql.Blob;
import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    // @Column(name = "productPicture")
    // private Blob productPicture;

    @ManyToOne
    private Osasto osasto;

    @ManyToOne
    private Toimittaja toimittaja;

    @ManyToOne
    private Valmistaja valmistaja;
}
