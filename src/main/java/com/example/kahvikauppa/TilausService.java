package com.example.kahvikauppa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TilausService {
    @Autowired
    private TilausRepository tilausRepository;

    public Tilaus newOrder(String order) {
        Tilaus newOrder = new Tilaus();
        newOrder.setOrderDetails(order);

        return this.tilausRepository.save(newOrder);
    }

}
