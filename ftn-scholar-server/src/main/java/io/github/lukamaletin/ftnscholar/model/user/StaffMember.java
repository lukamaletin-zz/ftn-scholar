package io.github.lukamaletin.ftnscholar.model.user;

import io.github.lukamaletin.ftnscholar.model.constants.ScientificField;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Entity
public class StaffMember extends BaseUser {

    private String title;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ScientificField> expertise;

    public StaffMember() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ScientificField> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<ScientificField> expertise) {
        this.expertise = expertise;
    }
}
