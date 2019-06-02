package io.github.lukamaletin.ftnscholar.task;

import io.github.lukamaletin.ftnscholar.model.Journal;
import io.github.lukamaletin.ftnscholar.model.Publication;
import io.github.lukamaletin.ftnscholar.model.ScientificPaper;
import io.github.lukamaletin.ftnscholar.service.JournalService;
import io.github.lukamaletin.ftnscholar.service.MailService;
import io.github.lukamaletin.ftnscholar.service.PaperService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcceptPaperTask implements JavaDelegate {

    private final JournalService journalService;

    private final PaperService paperService;

    private final MailService mailService;

    public AcceptPaperTask(JournalService journalService, PaperService paperService, MailService mailService) {
        this.journalService = journalService;
        this.paperService = paperService;
        this.mailService = mailService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        final String processId = execution.getProcessInstanceId();
        final ScientificPaper paper = paperService.findByProcessId(processId);
        final Long journalId = new Long((Integer) execution.getVariable("journalId"));
        final Journal journal = journalService.findById(journalId);

        paper.setReviewReply(null);
        paper.setReviewReply(null);

        final List<Publication> publications = journal.getPublications();
        final Publication latestPublication = publications.get(publications.size() - 1);
        latestPublication.getArticles().add(paper);
        journalService.save(journal);

        mailService.sendMail(paper.getAuthor().getEmail(), "Paper accepted");
    }
}
