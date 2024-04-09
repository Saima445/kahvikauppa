package com.example.kahvikauppa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OsastoRepository extends JpaRepository<Osasto, Long> {
    // Hae juuriosastot (ensimmäinen taso)
    @Query("SELECT o FROM Osasto o WHERE o.osastoIDP = 0")
    List<Osasto> findRootDepartments();

    // Hae alaosastot annetun osaston perusteella (toinen taso)
    @Query("SELECT o FROM Osasto o WHERE o.osastoIDP = :parentId")
    List<Osasto> findSubDepartments(@Param("parentId") Long parentId);

    // Hae mahdolliset alemman tason osastot (kolmas taso)
    // @Query("SELECT o FROM Osasto o WHERE o.osastoIDP IN (SELECT o2.id FROM Osasto
    // o2 WHERE o2.osastoIDP = :parentId)")
    // List<Osasto> findLowerLevelDepartments(@Param("parentId") Long parentId);

    // Hae yksittäinen osasto sen nimen perusteella
    // Osasto findByName(String name);
}