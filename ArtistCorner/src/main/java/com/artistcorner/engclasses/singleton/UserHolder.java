package com.artistcorner.engclasses.singleton;

import com.artistcorner.engclasses.bean.User;

public final class UserHolder {

    private User user;
    private static UserHolder instance = null;

    private UserHolder() {}

    public static UserHolder getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new UserHolder();
        }
        return instance;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public User getUser() {
        return this.user;
    }
}
