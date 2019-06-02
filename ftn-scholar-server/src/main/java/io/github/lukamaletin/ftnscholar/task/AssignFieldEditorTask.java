package io.github.lukamaletin.ftnscholar.task;

import io.github.lukamaletin.ftnscholar.model.Journal;
import io.github.lukamaletin.ftnscholar.model.ScientificPaper;
import io.github.lukamaletin.ftnscholar.model.user.StaffMember;
import io.github.lukamaletin.ftnscholar.service.JournalService;
import io.github.lukamaletin.ftnscholar.service.MailService;
import io.github.lukamaletin.ftnscholar.service.PaperService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignFieldEditorTask implements JavaDelegate {

    private final RuntimeService runtimeService;

    private final JournalService journalService;

    private final PaperService paperService;

    private final MailService mailService;

    public AssignFieldEditorTask(RuntimeService runtimeService, JournalService journalService, PaperService paperService, MailService mailService) {
        this.runtimeService = runtimeService;
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
        final List<StaffMember> fieldEditors = journal.getFieldEditors();

        final Optional<StaffMember> fieldEditor = fieldEditors.stream().filter(e -> e.getExpertise().contains(paper.getField())).findFirst();
        final StaffMember assignee = fieldEditor.orElse(journal.getChiefEditor());

        runtimeService.setVariable(processId, "fieldEditor", assignee.getUsername());

        mailService.sendMail(assignee.getEmail(), "New paper assigned");
    }
}
