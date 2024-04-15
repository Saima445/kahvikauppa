package com.example.kahvikauppa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TuoteRepository extends JpaRepository<Tuote, Long> {
    @Query("SELECT t FROM Tuote t JOIN FETCH t.osasto o WHERE o.id = :osastoID OR o.osastoIDP = :osastoID")
    List<Tuote> findProductsByOsastoID(@Param("osastoID") Long osastoID);
}
