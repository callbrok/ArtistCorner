package com.artistcorner.engclasses.query;
import com.artistcorner.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryUser {

    private QueryUser(){throw new IllegalStateException("Utility Query Login class");}

    public static ResultSet selectUser(Statement stmt, User user) throws SQLException {
        String sql = "SELECT * FROM utente WHERE username ='" + user.getUsername() + "' AND password='" + user.getPassword() + "';";
        return stmt.executeQuery(sql);
    }
}
