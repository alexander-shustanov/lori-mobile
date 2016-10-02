package com.shustanov.lorimobile.data.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Unique
    private String id;
    private String email;
    private String login;
    private String firstName;
    private String lastName;

    @Generated(hash = 668018458)
    public User(String id, String email, String login, String firstName,
            String lastName) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
