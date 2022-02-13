package com.artistcorner.model;

public class User {
    private String username;
    private String password;
    private String role;
    private String email;

    public User(String newUsername, String newPassword, String newEmail){
        this.username = newUsername;
        this.password = newPassword;
        this.email = newEmail;
        this.role = "NULL";
    }

    public User(String newUsername, String newPassword, String newRole, String newEmail){
        this.username = newUsername;
        this.password = newPassword;
        this.email = newEmail;
        this.role = newRole;
    }

    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getRole() {return role;}
    public String getEmail() {return email;}
}
