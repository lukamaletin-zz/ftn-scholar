package io.github.lukamaletin.ftnscholar.task;

import io.github.lukamaletin.ftnscholar.service.PaymentService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CheckSubscriptionTask implements JavaDelegate {

    private final PaymentService paymentService;

    private final RuntimeService runtimeService;

    public CheckSubscriptionTask(PaymentService paymentService, RuntimeService runtimeService) {
        this.paymentService = paymentService;
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        final String processId = execution.getProcessInstanceId();
        final String username = String.valueOf(execution.getVariable("author"));
        final String journalId = String.valueOf(execution.getVariable("journalId"));
        final boolean subscribed = paymentService.checkSubscription(username, Long.valueOf(journalId));

        runtimeService.setVariable(processId, "isSubscriptionPaid", subscribed);
    }
}
