package com.example.kahvikauppa;

import org.hibernate.annotations.Collate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "toimittaja_details")
public class Toimittaja extends AbstractPersistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private Long id;

    @Column(name = "contactPerson")
    private String contactPerson;

    @Column(name = "contactPersonEmail")
    private String contactPersonEmail;

    @OneToMany(mappedBy = "toimittaja")
    // @JoinColumn(name = "tuote_id", referencedColumnName = "toimittaja_id")
    private List<Tuote> tuotteet; // = new ArrayList<>();

}
