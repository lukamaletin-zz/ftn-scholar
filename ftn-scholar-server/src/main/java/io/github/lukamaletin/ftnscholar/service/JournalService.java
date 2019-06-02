package io.github.lukamaletin.ftnscholar.service;

import io.github.lukamaletin.ftnscholar.controller.exception.NotFoundException;
import io.github.lukamaletin.ftnscholar.model.Journal;
import io.github.lukamaletin.ftnscholar.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalService {

    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public Journal save(Journal journal) {
        return journalRepository.save(journal);
    }

    public Journal findById(Long id) {
        return journalRepository.findById(id).orElseThrow(() -> new NotFoundException("Journal not found!"));
    }

    public Journal findByChiefEditorId(long chiefEditorId) {
        return journalRepository.findByChiefEditorId(chiefEditorId).orElseThrow(() -> new NotFoundException("Journal not found!"));
    }

    public List<Journal> findAll() {
        return journalRepository.findAll();
    }
}
