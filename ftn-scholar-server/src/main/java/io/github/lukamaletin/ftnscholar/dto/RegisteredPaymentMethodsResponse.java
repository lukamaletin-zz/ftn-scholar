package io.github.lukamaletin.ftnscholar.dto;

public class RegisteredPaymentMethodsResponse {

    private String method;

    private boolean subscriptionBased;

    private boolean linked;

    public RegisteredPaymentMethodsResponse() {
    }

    public RegisteredPaymentMethodsResponse(String method, boolean subscriptionBased, boolean linked) {
        this.method = method;
        this.subscriptionBased = subscriptionBased;
        this.linked = linked;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isSubscriptionBased() {
        return subscriptionBased;
    }

    public void setSubscriptionBased(boolean subscriptionBased) {
        this.subscriptionBased = subscriptionBased;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }
}
