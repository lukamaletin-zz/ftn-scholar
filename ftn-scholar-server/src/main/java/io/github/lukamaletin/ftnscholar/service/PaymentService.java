package io.github.lukamaletin.ftnscholar.service;

import io.github.lukamaletin.ftnscholar.controller.exception.BadRequestException;
import io.github.lukamaletin.ftnscholar.dto.*;
import io.github.lukamaletin.ftnscholar.model.Journal;
import io.github.lukamaletin.ftnscholar.model.Purchase;
import io.github.lukamaletin.ftnscholar.model.user.Member;
import io.github.lukamaletin.ftnscholar.model.user.StaffMember;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Value("${frontend-app-url}")
    private String frontendAppUrl;

    @Value("${payment-server-url}")
    private String paymentServerUrl;

    private final UserService userService;

    private final JournalService journalService;

    private final PurchaseService purchaseService;

    private final RestTemplate restTemplate;

    public PaymentService(UserService userService, JournalService journalService, PurchaseService purchaseService, RestTemplate restTemplate) {
        this.userService = userService;
        this.journalService = journalService;
        this.purchaseService = purchaseService;
        this.restTemplate = restTemplate;
    }

    public void signup(String clientId) {
        final StaffMember user = (StaffMember) userService.getCurrentUser();
        final Journal journal = journalService.findByChiefEditorId(user.getId());

        if (journal.getPaymentClientId() != null && !"".equals(journal.getPaymentClientId())) {
            throw new BadRequestException("Journal already linked!");
        }

        journal.setPaymentClientId(clientId);
        journal.setPaymentsEnabled(true);
        journalService.save(journal);
    }

    public List<RegisteredPaymentMethodsResponse> getRegisteredPaymentMethods() {
        final StaffMember user = (StaffMember) userService.getCurrentUser();
        final Journal journal = journalService.findByChiefEditorId(user.getId());
        final String url = String.format("%s/api/auth/registered-methods?clientId=%s", paymentServerUrl, journal.getPaymentClientId());

        final RegisteredPaymentMethodsResponse[] paymentMethods =
                restTemplate.getForObject(url, RegisteredPaymentMethodsResponse[].class);

        return Arrays.asList(paymentMethods);
    }

    public String getPaymentRegistrationUrl(String paymentMethod) {
        final StaffMember user = (StaffMember) userService.getCurrentUser();
        final Journal journal = journalService.findByChiefEditorId(user.getId());

        if (!journal.isPaymentsEnabled()) {
            throw new BadRequestException("Payments not enabled!");
        }

        return String.format("%s/#/register-payment/%s/%s", paymentServerUrl, paymentMethod, journal.getPaymentClientId());
    }

    public boolean checkSubscription(String username, Long journalId) {
        final Member member = (Member) userService.findByUsername(username);
        final Optional<Purchase> purchase = member.getPurchases().stream().filter(p -> p.getJournal().getId().equals(journalId)).findFirst();
        if (!purchase.isPresent()) {
            return false;
        }

        final PaymentStatusRequest request = new PaymentStatusRequest(
                String.valueOf(member.getId()),
                String.valueOf(purchase.get().getId()),
                String.valueOf(purchase.get().getJournal().getPaymentClientId()));
        final String url = String.format("%s/api/payments/status", paymentServerUrl);
        final PaymentStatusResponse response = restTemplate.postForObject(url, request, PaymentStatusResponse.class);

        return "SUCCESS".equals(response.getPaymentStatus());
    }

    public String createPayment(PurchaseRequest purchaseRequest) {
        final Member member = (Member) userService.getCurrentUser();
        final Journal journal = journalService.findById(purchaseRequest.getJournal().getId());

        if (journal.getPaymentClientId() == null) {
            throw new BadRequestException("Journal is not registered for payment services!");
        }

        Purchase purchase = new Purchase(member, journal);
        purchase = purchaseService.save(purchase);

        final PaymentRequest paymentRequest = new PaymentRequest(String.valueOf(member.getId()), String.valueOf(purchase.getId()),
                journal.getPaymentClientId(), journal.getPrice(), purchaseRequest.isSubscriptionBased());
        paymentRequest.setSuccessUrl(String.format("%s/payment-success", frontendAppUrl));
        paymentRequest.setErrorUrl(String.format("%s/payment-error", frontendAppUrl));
        final String url = String.format("%s/api/payments", paymentServerUrl);

        return restTemplate.postForObject(url, paymentRequest, String.class);
    }
}
