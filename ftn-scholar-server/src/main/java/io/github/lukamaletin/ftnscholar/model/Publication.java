package io.github.lukamaletin.ftnscholar.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int volume;

    private int issue;

    private Date date;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ScientificPaper> articles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ScientificPaper> getArticles() {
        return articles;
    }

    public void setArticles(List<ScientificPaper> articles) {
        this.articles = articles;
    }
}
