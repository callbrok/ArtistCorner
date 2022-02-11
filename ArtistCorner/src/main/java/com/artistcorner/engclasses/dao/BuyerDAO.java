package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.exceptions.*;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.engclasses.query.QueryBuyer;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import com.artistcorner.model.User;

import java.sql.*;

public class BuyerDAO {

    private BuyerDAO(){

        throw new IllegalStateException("Utility class");
    }
    public static final String BUY_PROBLEM = "Problema nella gestione dell'acquisto";

    public static Buyer retrieveBuyer(User usr){
        Buyer bu = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectBuyer(stmt, usr.getUsername());

            if (!rs.first()){ // rs empty
                throw new BuyerNotFoundException("Acquirente non trovato");
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String cognomeBuyer = rs.getString("cognome");
                String nomeBuyer = rs.getString("nome");
                int idBuyer = rs.getInt("idCompratore");

                bu = new Buyer(idBuyer,nomeBuyer, cognomeBuyer);


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se21) {
                se21.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se1) {
                se1.printStackTrace();
            }
        }

        return bu;
    }
    public static void insertBuyer(User user, Buyer buyer) throws DuplicateUserException, SQLException {
        // STEP 1: dichiarazioni
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);


            // Check Duplicate User
            ResultSet rs = QueryArtist.selectUsername(stmt);
            while (rs.next()) {
                // lettura delle colonne "by name"
                String usernameRetrivedB = rs.getString("username");

                if (usernameRetrivedB.equals(user.getUsername())){
                    throw new DuplicateUserException("Username attualmente già in uso.");
                }
            }

            int result = QueryArtist.insertUser(stmt, user);
            result = QueryBuyer.insertBuyer(stmt,buyer, user.getUsername());

            if(result == -1){throw new AddBuyerException("Impossibile aggiungere acquirente");}

        } catch (SQLException | AddBuyerException | ClassNotFoundException e2){
            e2.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }


    }
    public static void addArtWorkToFavourites(int artWorkId, int idBuyer) throws SQLException, FavouritesManagementProblemException {
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryBuyer.insertOperaFavourites(stmt,artWorkId, idBuyer);
            if (result==-1){throw new FavouritesManagementProblemException("Problema nella gestione dei preferiti");
            }

        } catch (ClassNotFoundException  ex10) {
            ex10.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

    }
    public static void removeArtWorkFromFavourites(int idOpera, int idBuyer) throws SQLException, FavouritesManagementProblemException {
        Statement stmt = null;
        int result;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            result = QueryBuyer.removeOperaFromFavourites(stmt,idOpera, idBuyer);
            if (result==-1){throw new FavouritesManagementProblemException("Problema nella gestione dei preferiti");
            }
        } catch (ClassNotFoundException ex7) {
            ex7.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
    }
    public static void addArtWorkComprata(int artWorkId, int idBuyer) throws SQLException, BuyArtworkManagementProblemException {
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryBuyer.insertOperaComprata(stmt,artWorkId, idBuyer);
            if (result==-1){throw new BuyArtworkManagementProblemException(BUY_PROBLEM);
            }

        } catch (ClassNotFoundException ex5) {
            ex5.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

    }




    public static Buyer retrieveBuyerByArtworkId(int artworkId){
        Buyer buyer = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectBuyerFromArtworkId(stmt, artworkId);


            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int idBuyer = rs.getInt("idCompratore");
                String cognome = rs.getString("cognome");
                String nome = rs.getString("nome");

                buyer = new Buyer(idBuyer,nome,cognome);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception ex2) {
            ex2.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se4) {
                se4.printStackTrace();
            }
        }

        return buyer;

    }

}

