package io.github.lukamaletin.ftnscholar.dto;

import io.github.lukamaletin.ftnscholar.model.ScientificPaper;
import io.github.lukamaletin.ftnscholar.model.constants.ScientificField;

import java.util.List;

public class PaperMetadataResponse {

    private String title;

    private List<String> keywords;

    private String paperAbstract;

    private ScientificField field;

    private String reviewReply;

    public PaperMetadataResponse() {
    }

    public PaperMetadataResponse(ScientificPaper paper) {
        this.title = paper.getTitle();
        this.keywords = paper.getKeywords();
        this.paperAbstract = paper.getPaperAbstract();
        this.field = paper.getField();
        this.reviewReply = paper.getReviewReply();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getReviewReply() {
        return reviewReply;
    }

    public void setReviewReply(String reviewReply) {
        this.reviewReply = reviewReply;
    }
}
