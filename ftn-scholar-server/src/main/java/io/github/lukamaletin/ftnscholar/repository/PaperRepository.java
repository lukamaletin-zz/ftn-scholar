package io.github.lukamaletin.ftnscholar.repository;

import io.github.lukamaletin.ftnscholar.model.ScientificPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<ScientificPaper, Long> {
}
