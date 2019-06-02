package io.github.lukamaletin.ftnscholar.model;

import io.github.lukamaletin.ftnscholar.model.user.Member;

import javax.persistence.*;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Member member;

    @ManyToOne
    private Journal journal;

    public Purchase() {
    }

    public Purchase(Member member, Journal journal) {
        this.member = member;
        this.journal = journal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
}
