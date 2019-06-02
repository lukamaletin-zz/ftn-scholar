package io.github.lukamaletin.ftnscholar.dto;

import io.github.lukamaletin.ftnscholar.model.Journal;

public class PurchaseRequest {

    private Journal journal;

    private boolean subscriptionBased;

    public PurchaseRequest() {
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public boolean isSubscriptionBased() {
        return subscriptionBased;
    }

    public void setSubscriptionBased(boolean subscriptionBased) {
        this.subscriptionBased = subscriptionBased;
    }
}
