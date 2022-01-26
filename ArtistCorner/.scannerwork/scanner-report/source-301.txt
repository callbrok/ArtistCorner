package com.artistcorner.engclasses.query;

import com.artistcorner.engclasses.bean.UserBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryLogin {

    private QueryLogin(){throw new IllegalStateException("Utility Query Login class");}

    public static ResultSet selectUser(Statement stmt, UserBean userBean) throws SQLException {
        String sql = "SELECT * FROM utente WHERE username ='" + userBean.getUsername() + "' AND password='" + userBean.getPassword() + "';";
        return stmt.executeQuery(sql);
    }
}
