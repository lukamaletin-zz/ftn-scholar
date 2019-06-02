package io.github.lukamaletin.ftnscholar.task;

import io.github.lukamaletin.ftnscholar.model.ScientificPaper;
import io.github.lukamaletin.ftnscholar.service.MailService;
import io.github.lukamaletin.ftnscholar.service.PaperService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class AssignCorrectPaperTask implements JavaDelegate {

    private final PaperService paperService;

    private final MailService mailService;

    public AssignCorrectPaperTask(PaperService paperService, MailService mailService) {
        this.paperService = paperService;
        this.mailService = mailService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        final String processId = execution.getProcessInstanceId();
        final ScientificPaper paper = paperService.findByProcessId(processId);

        mailService.sendMail(paper.getAuthor().getEmail(), "Paper corrections required");

    }
}
