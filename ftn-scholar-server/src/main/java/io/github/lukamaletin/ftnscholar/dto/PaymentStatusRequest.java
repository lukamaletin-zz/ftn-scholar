package io.github.lukamaletin.ftnscholar.dto;

public class PaymentStatusRequest {

    private String buyerId;

    private String productId;

    private String clientId;

    public PaymentStatusRequest() {
    }

    public PaymentStatusRequest(String buyerId, String productId, String clientId) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.clientId = clientId;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
