package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {

    private String userName;
    private String email;
    private String password;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
 

}
