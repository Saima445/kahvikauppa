package com.example.kahvikauppa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OsastoRepository extends JpaRepository<Osasto, Long> {

}