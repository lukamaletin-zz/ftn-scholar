package io.github.lukamaletin.ftnscholar.task;

import io.github.lukamaletin.ftnscholar.model.user.StaffMember;
import io.github.lukamaletin.ftnscholar.service.MailService;
import io.github.lukamaletin.ftnscholar.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ReviewTimeoutTask implements JavaDelegate {

    private final UserService userService;

    private final MailService mailService;

    public ReviewTimeoutTask(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        final String fieldEditorUsername = (String) execution.getVariable("fieldEditor");
        final StaffMember fieldEditor = (StaffMember) userService.findByUsername(fieldEditorUsername);

        mailService.sendMail(fieldEditor.getEmail(), "Review period timed out");
    }
}
