package io.github.lukamaletin.ftnscholar.repository;

import io.github.lukamaletin.ftnscholar.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
