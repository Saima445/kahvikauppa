package com.example.kahvikauppa;

import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "order_details")
    private String orderDetails;

}