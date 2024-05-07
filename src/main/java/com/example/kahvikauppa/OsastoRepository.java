package com.example.kahvikauppa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OsastoRepository extends JpaRepository<Osasto, Long> {
    Osasto findByName(String name);

    List<Osasto> findByOsastoIDP(Long osastoIDP);
}