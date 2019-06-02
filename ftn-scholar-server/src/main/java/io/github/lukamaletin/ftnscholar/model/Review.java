package io.github.lukamaletin.ftnscholar.model;

import io.github.lukamaletin.ftnscholar.model.constants.ReviewGrade;
import io.github.lukamaletin.ftnscholar.model.user.StaffMember;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 1000)
    private String content;

    @Column(length = 1000)
    private String comment;

    @Enumerated(EnumType.STRING)
    private ReviewGrade grade;

    @ManyToOne
    private StaffMember reviewer;

    public Review() {
    }

    public Review(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewGrade getGrade() {
        return grade;
    }

    public void setGrade(ReviewGrade grade) {
        this.grade = grade;
    }

    public StaffMember getReviewer() {
        return reviewer;
    }

    public void setReviewer(StaffMember reviewer) {
        this.reviewer = reviewer;
    }
}
