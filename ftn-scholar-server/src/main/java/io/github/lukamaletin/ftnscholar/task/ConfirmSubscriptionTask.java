package io.github.lukamaletin.ftnscholar.task;

import io.github.lukamaletin.ftnscholar.model.constants.Constants;
import io.github.lukamaletin.ftnscholar.service.PaymentService;
import io.github.lukamaletin.ftnscholar.util.TimeUtils;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConfirmSubscriptionTask implements JavaDelegate {

    private final PaymentService paymentService;

    public ConfirmSubscriptionTask(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        final Date now = new Date();
        while (!TimeUtils.isExpired(now, Constants.CONFIRM_PAYMENT_TIMEOUT_MINUTES)) {
            final String username = String.valueOf(execution.getVariable("author"));
            final String journalId = String.valueOf(execution.getVariable("journalId"));
            final boolean subscribed = paymentService.checkSubscription(username, Long.valueOf(journalId));

            if (subscribed) {
                return;
            }
        }

        throw new BpmnError("-1", "Subscription unsuccessful!");
    }
}
