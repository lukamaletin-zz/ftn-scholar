package io.github.lukamaletin.ftnscholar.dto;

import io.github.lukamaletin.ftnscholar.model.ScientificPaper;
import io.github.lukamaletin.ftnscholar.model.constants.ScientificField;
import io.github.lukamaletin.ftnscholar.model.user.UserInfo;

import java.util.List;

public class PaperRequest {

    private String title;

    private List<UserInfo> coauthors;

    private List<String> keywords;

    private String paperAbstract;

    private ScientificField field;

    public PaperRequest() {
    }

    public ScientificPaper createScientificPaper() {
        final ScientificPaper scientificPaper = new ScientificPaper();

        scientificPaper.setTitle(title);
        scientificPaper.setCoauthors(coauthors);
        scientificPaper.setKeywords(keywords);
        scientificPaper.setPaperAbstract(paperAbstract);
        scientificPaper.setField(field);

        return scientificPaper;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
