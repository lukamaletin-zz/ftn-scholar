package io.github.lukamaletin.ftnscholar.dto;

import java.math.BigDecimal;

public class PaymentRequest {

    private String buyerId;

    private String productId;

    private String clientId;

    private BigDecimal amount;

    private String successUrl;

    private String errorUrl;

    private boolean subscriptionBased;

    public PaymentRequest() {
    }

    public PaymentRequest(String buyerId, String productId, String clientId, BigDecimal amount, boolean subscriptionBased) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.clientId = clientId;
        this.amount = amount;
        this.subscriptionBased = subscriptionBased;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean isSubscriptionBased() {
        return subscriptionBased;
    }

    public void setSubscriptionBased(boolean subscriptionBased) {
        this.subscriptionBased = subscriptionBased;
    }
}
