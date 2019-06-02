package io.github.lukamaletin.ftnscholar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.lukamaletin.ftnscholar.model.constants.PaymentOption;
import io.github.lukamaletin.ftnscholar.model.constants.ScientificField;
import io.github.lukamaletin.ftnscholar.model.user.StaffMember;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String issn;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ScientificField> scientificFields;

    @Enumerated(EnumType.STRING)
    private PaymentOption paymentOption;

    @OneToOne
    private StaffMember chiefEditor;

    @OneToMany
    private List<StaffMember> fieldEditors;

    @ManyToMany
    private List<StaffMember> reviewers;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Publication> publications;

    @JsonIgnore
    private String paymentClientId;

    private boolean paymentsEnabled;

    private BigDecimal price;

    public Journal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public List<ScientificField> getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(List<ScientificField> scientificFields) {
        this.scientificFields = scientificFields;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }

    public StaffMember getChiefEditor() {
        return chiefEditor;
    }

    public void setChiefEditor(StaffMember chiefEditor) {
        this.chiefEditor = chiefEditor;
    }

    public List<StaffMember> getFieldEditors() {
        return fieldEditors;
    }

    public void setFieldEditors(List<StaffMember> fieldEditors) {
        this.fieldEditors = fieldEditors;
    }

    public List<StaffMember> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<StaffMember> reviewers) {
        this.reviewers = reviewers;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public String getPaymentClientId() {
        return paymentClientId;
    }

    public void setPaymentClientId(String paymentClientId) {
        this.paymentClientId = paymentClientId;
    }

    public boolean isPaymentsEnabled() {
        return paymentsEnabled;
    }

    public void setPaymentsEnabled(boolean paymentsEnabled) {
        this.paymentsEnabled = paymentsEnabled;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
