package io.github.lukamaletin.ftnscholar.controller;

import io.github.lukamaletin.ftnscholar.dto.PaymentServicesSignupRequest;
import io.github.lukamaletin.ftnscholar.dto.PurchaseRequest;
import io.github.lukamaletin.ftnscholar.dto.RegisteredPaymentMethodsResponse;
import io.github.lukamaletin.ftnscholar.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasAuthority('CHIEF_EDITOR')")
    @PostMapping("signup")
    public ResponseEntity signup(@RequestBody PaymentServicesSignupRequest dto) {
        paymentService.signup(dto.getClientId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CHIEF_EDITOR')")
    @GetMapping("registered-methods")
    public ResponseEntity getRegisteredPaymentMethods() {
        final List<RegisteredPaymentMethodsResponse> paymentMethods = paymentService.getRegisteredPaymentMethods();

        return new ResponseEntity<>(paymentMethods, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CHIEF_EDITOR')")
    @GetMapping("url/{paymentMethod}")
    public ResponseEntity getPaymentRegistrationUrl(@PathVariable String paymentMethod) {
        final String redirectUrl = paymentService.getPaymentRegistrationUrl(paymentMethod);

        return new ResponseEntity<>(redirectUrl, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity initPurchase(@RequestBody PurchaseRequest purchaseRequest) {
        final String redirectUrl = paymentService.createPayment(purchaseRequest);

        return new ResponseEntity<>(redirectUrl, HttpStatus.OK);
    }
}
