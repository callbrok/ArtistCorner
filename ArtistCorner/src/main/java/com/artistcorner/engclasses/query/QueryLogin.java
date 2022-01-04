package com.artistcorner.engclasses.query;

import com.artistcorner.engclasses.bean.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryLogin {
    public static ResultSet selectUser(Statement stmt, User user) throws SQLException {
        String sql = "SELECT * FROM utente WHERE username ='" + user.getUsername() + "' AND password='" + user.getPassword() + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }
}
