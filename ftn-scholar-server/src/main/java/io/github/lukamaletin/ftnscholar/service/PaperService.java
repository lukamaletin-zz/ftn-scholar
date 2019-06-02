package io.github.lukamaletin.ftnscholar.service;

import io.github.lukamaletin.ftnscholar.controller.exception.NotFoundException;
import io.github.lukamaletin.ftnscholar.dto.PaperRequest;
import io.github.lukamaletin.ftnscholar.model.Journal;
import io.github.lukamaletin.ftnscholar.model.ScientificPaper;
import io.github.lukamaletin.ftnscholar.model.user.Member;
import io.github.lukamaletin.ftnscholar.model.user.StaffMember;
import io.github.lukamaletin.ftnscholar.repository.PaperRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperService {

    private final PaperRepository paperRepository;

    private final JournalService journalService;

    private final ProcessService processService;

    public PaperService(PaperRepository paperRepository, JournalService journalService, ProcessService processService) {
        this.paperRepository = paperRepository;
        this.journalService = journalService;
        this.processService = processService;
    }

    public ScientificPaper save(ScientificPaper paper) {
        return paperRepository.save(paper);
    }

    public ScientificPaper save(PaperRequest paperRequest, Member member, String processId) {
        final ScientificPaper paper = paperRequest.createScientificPaper();
        paper.setAuthor(member);

        final ScientificPaper savedPaper = paperRepository.save(paper);

        processService.setProcessVariables(processId, new HashMap<String, Object>() {{
            put("paperId", savedPaper.getId());
        }});

        return savedPaper;
    }

    public ScientificPaper findById(Long id) {
        return paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Scientific paper not found!"));
    }

    public ScientificPaper findByProcessId(String processId) {
        final Object paperId = processService.getProcessVariables(processId, "paperId");

        return findById(new Long((Integer) paperId));
    }

    public List<StaffMember> getReviewers(String processId) {
        final Object journalId = processService.getProcessVariables(processId, "journalId");
        final Journal journal = journalService.findById(new Long((Integer) journalId));
        final ScientificPaper paper = findByProcessId(processId);
        List<StaffMember> reviewers = journal.getReviewers();

        reviewers = reviewers.stream()
                .filter(reviewer -> paper.getReviews().stream().noneMatch(review -> review.getReviewer().getId().equals(reviewer.getId()))).collect(Collectors.toList());

        reviewers.sort((r1, r2) ->
                r1.getExpertise().contains(paper.getField()) == r2.getExpertise().contains(paper.getField()) ? 0 : r1.getExpertise().contains(paper.getField()) ? -1 : 1);

        return reviewers;
    }
}
