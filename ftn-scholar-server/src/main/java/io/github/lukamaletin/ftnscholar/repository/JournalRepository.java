package io.github.lukamaletin.ftnscholar.repository;

import io.github.lukamaletin.ftnscholar.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    Optional<Journal> findByChiefEditorId(long id);
}
