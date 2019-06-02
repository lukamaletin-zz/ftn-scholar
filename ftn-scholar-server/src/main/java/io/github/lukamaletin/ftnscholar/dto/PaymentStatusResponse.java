package io.github.lukamaletin.ftnscholar.dto;

public class PaymentStatusResponse {

    private String paymentStatus;

    public PaymentStatusResponse() {
    }

    public PaymentStatusResponse(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
