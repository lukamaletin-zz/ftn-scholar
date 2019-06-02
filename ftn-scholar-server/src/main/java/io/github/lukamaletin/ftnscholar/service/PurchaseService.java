package io.github.lukamaletin.ftnscholar.service;

import io.github.lukamaletin.ftnscholar.model.Purchase;
import io.github.lukamaletin.ftnscholar.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }
}
