package com.artistcorner.engclasses.bean;

public class UserBean {
    String username;
    String password;
    String role;

    public UserBean(String newUsername, String newPassword){
        this.username = newUsername;
        this.password = newPassword;
        this.role = "NULL";
    }

    public UserBean(String newUsername, String newPassword, String newRole){
        this.username = newUsername;
        this.password = newPassword;
        this.role = newRole;
    }

    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getRole() {return role;}
}
