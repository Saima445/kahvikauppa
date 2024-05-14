package com.example.kahvikauppa;

import org.hibernate.annotations.Collate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "tilaus_details")
public class Tilaus extends AbstractPersistable<Long> {

    @Column(name = "order_content")
    @Lob
    private byte[] content;

    // @Column(name = "product_name")
    // private String productName;

    // @Column(name = "product_price")
    // private double productPrice;

    // @Column(name = "quantity")
    // private int quantity;

}
