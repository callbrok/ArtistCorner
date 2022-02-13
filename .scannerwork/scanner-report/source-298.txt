package com.artistcorner.engclasses.bean;

public class UserBean {
    private String username;
    private String password;
    private String role;

    public UserBean(String newUsernameB, String newPasswordB){
        this.username = newUsernameB;
        this.password = newPasswordB;
        this.role = "NULL";
    }

    public UserBean(String newUsernameB, String newPasswordB, String newRoleB){
        this.username = newUsernameB;
        this.password = newPasswordB;
        this.role = newRoleB;
    }

    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getRole() {return role;}
}
