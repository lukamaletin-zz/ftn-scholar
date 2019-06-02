package io.github.lukamaletin.ftnscholar.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.lukamaletin.ftnscholar.model.constants.Role;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @OneToOne
    private UserInfo userInfo;

    public BaseUser() {
        this.userInfo = new UserInfo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getFirstName() {
        return userInfo.getFirstName();
    }

    public void setFirstName(String firstName) {
        userInfo.setFirstName(firstName);
    }

    public String getLastName() {
        return userInfo.getLastName();
    }

    public void setLastName(String lastName) {
        userInfo.setLastName(lastName);
    }

    public String getEmail() {
        return userInfo.getEmail();
    }

    public void setEmail(String email) {
        userInfo.setEmail(email);
    }

    public String getCity() {
        return userInfo.getCity();
    }

    public void setCity(String city) {
        userInfo.setCity(city);
    }

    public String getCountry() {
        return userInfo.getCountry();
    }

    public void setCountry(String country) {
        userInfo.setCountry(country);
    }
}
