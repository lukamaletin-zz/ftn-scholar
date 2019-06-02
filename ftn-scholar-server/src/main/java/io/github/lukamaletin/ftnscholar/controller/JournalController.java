package io.github.lukamaletin.ftnscholar.controller;

import io.github.lukamaletin.ftnscholar.model.Journal;
import io.github.lukamaletin.ftnscholar.service.JournalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/journal")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity findAll() {
        final List<Journal> journals = journalService.findAll();

        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Journal journal = journalService.findById(id);

        return new ResponseEntity<>(journal, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("chief/{chiefEditorId}")
    public ResponseEntity findByChiefEditorId(@PathVariable long chiefEditorId) {
        final Journal journal = journalService.findByChiefEditorId(chiefEditorId);

        return new ResponseEntity<>(journal, HttpStatus.OK);
    }
}
