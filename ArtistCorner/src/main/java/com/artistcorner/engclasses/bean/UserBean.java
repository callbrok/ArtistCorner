package com.artistcorner.engclasses.bean;

import com.artistcorner.engclasses.exceptions.NotValidEmailException;
import org.apache.commons.validator.routines.EmailValidator;

public class UserBean {
    private String username;
    private String password;
    private String role;
    private String email;


    public void setPassword(String password) {this.password = password;}
    public void setUsername(String username) {this.username = username;}
    public void setRole(String role) {this.role = role;}

    public void setEmail(String email) throws NotValidEmailException {
        boolean valid = EmailValidator.getInstance().isValid(email);

        if(!valid){throw new NotValidEmailException("Inserita email non valida.");}

        this.email = email;
    }


    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getRole() {return role;}
    public String getEmail() {return email;}
}
