package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryLogin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDAO {

    private LoginDAO(){
        throw new IllegalStateException("Utility class");
    }

    public static UserBean retrieveUser(UserBean userBean){
        UserBean loggedUserBean = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryLogin.selectUser(stmt, userBean);

            if (!rs.first()){ // rs empty
                return null;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura della colonna
                String usr = rs.getString("username");
                String psw = rs.getString("password");
                String rl = rs.getString("ruolo");

                loggedUserBean = new UserBean(usr, psw, rl);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException el) {
            el.printStackTrace();
        } catch (Exception el1) {
            el1.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2l) {
                se2l.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sel) {
                sel.printStackTrace();
            }
        }

        return loggedUserBean;
    }





}
