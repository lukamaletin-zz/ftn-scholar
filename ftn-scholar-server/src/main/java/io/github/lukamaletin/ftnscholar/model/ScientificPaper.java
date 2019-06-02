package io.github.lukamaletin.ftnscholar.model;

import io.github.lukamaletin.ftnscholar.model.constants.ScientificField;
import io.github.lukamaletin.ftnscholar.model.user.Member;
import io.github.lukamaletin.ftnscholar.model.user.UserInfo;

import javax.persistence.*;
import java.util.List;

@Entity
public class ScientificPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String doi;

    private String title;

    @ManyToOne
    private Member author;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserInfo> coauthors;

    @ElementCollection
    private List<String> keywords;

    @Column(length = 1000)
    private String paperAbstract;

    @Enumerated(EnumType.STRING)
    private ScientificField field;

    private String pdfPath;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;

    private String reviewReply;

    public ScientificPaper() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public List<UserInfo> getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(List<UserInfo> coauthors) {
        this.coauthors = coauthors;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public ScientificField getField() {
        return field;
    }

    public void setField(ScientificField field) {
        this.field = field;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getReviewReply() {
        return reviewReply;
    }

    public void setReviewReply(String reviewReply) {
        this.reviewReply = reviewReply;
    }
}
