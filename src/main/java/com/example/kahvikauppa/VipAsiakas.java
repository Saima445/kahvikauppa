package com.example.kahvikauppa;

import org.hibernate.annotations.Collate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "vipasiakas_details")
public class VipAsiakas extends AbstractPersistable<Long> {
    @NotEmpty
    @Column(name = "firstname")
    private String firstname;
    @NotEmpty
    @Column(name = "lastname")
    private String lastname;
    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;

}
