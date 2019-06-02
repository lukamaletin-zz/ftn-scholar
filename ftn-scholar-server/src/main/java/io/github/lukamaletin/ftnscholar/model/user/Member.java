package io.github.lukamaletin.ftnscholar.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.lukamaletin.ftnscholar.model.Purchase;
import io.github.lukamaletin.ftnscholar.model.constants.Role;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Member extends BaseUser {

    public Member() {
        super();
        setRole(Role.AUTHOR);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Purchase> purchases;

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
