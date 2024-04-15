package com.example.kahvikauppa;

import org.hibernate.annotations.Collate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "toimittaja_details")
public class Toimittaja extends AbstractPersistable<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "contactPerson")
    private String contactPerson;

    @Column(name = "contactPersonEmail")
    private String contactPersonEmail;

    @OneToMany(mappedBy = "toimittaja")
    private List<Tuote> products = new ArrayList<>();

}
