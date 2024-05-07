package com.example.kahvikauppa;

import java.math.BigDecimal;
import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private Long id;

    @Column(name = "order_content")
    private String order;
    // @Column(name = "product_count")
    // private int productCount;

    // @Column(name = "product_name")
    // private String productName;

    // @Column(name = "product_price")
    // private BigDecimal productPrice;

    // @Column(name = "total_price")
    // private BigDecimal totalprice;

}
