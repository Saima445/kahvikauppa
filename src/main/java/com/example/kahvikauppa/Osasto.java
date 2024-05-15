package com.example.kahvikauppa;

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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "osasto_details")

public class Osasto extends AbstractPersistable<Long> {
    @NotEmpty
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "osastoIDP")
    private Long osastoIDP;

    @Column(name = "productCount")
    private Integer productCount;

    @OneToMany(mappedBy = "osasto")
    private List<Tuote> products = new ArrayList<>();

}