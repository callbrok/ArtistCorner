package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryUser;
import com.artistcorner.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    private UserDAO(){
        throw new IllegalStateException("Utility class");
    }

    public static User retrieveUser(User user){
        User loggedUser = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryUser.selectUser(stmt, user);

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
                String mail = rs.getString("email");

                loggedUser = new User(usr, psw, rl, mail);

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

        return loggedUser;
    }





}
