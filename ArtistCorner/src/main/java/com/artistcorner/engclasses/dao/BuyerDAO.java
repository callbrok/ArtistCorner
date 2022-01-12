package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.User;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryBuyer;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Buyer;
import java.sql.*;
import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BuyerDAO {

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
                Exception e = new Exception("No Buyer found");
                throw e;
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return bu;
    }
    public static ArtWork retrieveArtWorks(int idUsr){
        ArtWork artWork=null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectArtWork(stmt, idUsr);

            if (!rs.first()){ // rs empty
                Exception e = new Exception("No ArtWork found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo = rs.getString("titolo");
                int venduto = rs.getInt("flagVendibile");
                double prezzo = rs.getDouble("prezzo");
                int idOpera = rs.getInt("idOpera");

                artWork = new ArtWork(idOpera, titolo, prezzo, venduto);


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return artWork;
    }

    public static Blob retrieveImage(int idArtWork){
        Blob immagine =null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectImage(stmt, idArtWork);

            if (!rs.first()){ // rs empty
                Exception e = new Exception("No Blob found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                immagine = rs.getBlob("immagine");


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return immagine;
    }




    public static String retrieveArtistName(int idUsr) {
        String artistName="";
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectAllArtistName(stmt, idUsr);

            if (!rs.first()){ // rs empty
                Exception e = new Exception("No Artist Name found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String name = rs.getString("nome");
                String cognome = rs.getString("cognome");
                artistName = name+" "+cognome;


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return artistName;

    }

    public static ArrayList<Integer> retrieveArtWorkId(int idUsr) {
        ArrayList<Integer> listOfArtWorkId = new ArrayList<Integer>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectArtWorkId(stmt, idUsr);


            if (!rs.first()){ // rs empty
                Exception e = new Exception("No ArtWork found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int name = rs.getInt("opera");

                listOfArtWorkId.add(name);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return listOfArtWorkId;
    }

    public static int retrieveArtistId(int idOpera) {
        int artistId= 0;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectAllArtistId(stmt, idOpera);

            if (!rs.first()){ // rs empty
                Exception e = new Exception("No ArtistId found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                artistId = rs.getInt("artista");

               // listOfArtistId.add(name);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return artistId;
    }

    public static void addArtWorkComprata(int artWorkId, int idBuyer) throws SQLException {
        PreparedStatement prStmt = null;
        Connection conn = null;
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        //LocalDateTime now = LocalDateTime.now();

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            prStmt = conn.prepareStatement(QueryBuyer.insertOperaComprata());   // Prende la query SQL.

            // Setta i prepared statement.
            prStmt.setInt(1, artWorkId);
            prStmt.setInt(2, idBuyer);
            //prStmt.setString(3,dtf.format(now));

            prStmt.executeUpdate();
            prStmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (prStmt != null)
                prStmt.close();
            if (conn != null)
                conn.close();
        }
    }

    public static void switchFlagVendibile(int idOpera) throws SQLException {
        PreparedStatement prStmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            prStmt = conn.prepareStatement(QueryBuyer.switchFlagVendibile(idOpera));   // Prende la query SQL.

            // Setta i prepared statement.

            prStmt.setInt(1,0);

            prStmt.executeUpdate();
            prStmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (prStmt != null)
                prStmt.close();
            if (conn != null)
                conn.close();
        }
    }

    public static void removeArtWorkFromFavourites(int idOpera, int idBuyer) throws SQLException {
        PreparedStatement prStmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            prStmt = conn.prepareStatement(QueryBuyer.removeOperaFromFavourites());   // Prende la query SQL.

            // Setta i prepared statement.

            prStmt.setInt(1,idOpera);
            prStmt.setInt(2,idBuyer);
            prStmt.executeUpdate();
            prStmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (prStmt != null)
                prStmt.close();
            if (conn != null)
                conn.close();
        }
    }

    public static ArrayList<ArtWork> retrieveArtWorkByName(String input) {
        input= input.replaceAll("","$0%");
        ArrayList<ArtWork> artWork = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectArtWorkByName(stmt, input);

            if (!rs.first()){ // rs empty
                Exception e = new Exception("No ArtWork found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo = rs.getString("titolo");
                int venduto = rs.getInt("flagVendibile");
                double prezzo = rs.getDouble("prezzo");
                int idOpera = rs.getInt("idOpera");
                ArtWork art = new ArtWork(idOpera, titolo, prezzo, venduto);
                artWork.add(art);


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return artWork;
    }

    public static void addArtWorkToFavourites(int artWorkId, int idBuyer) throws SQLException {
        PreparedStatement prStmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            prStmt = conn.prepareStatement(QueryBuyer.insertOperaFavourites());   // Prende la query SQL.

            // Setta i prepared statement.
            prStmt.setInt(1, artWorkId);
            prStmt.setInt(2, idBuyer);

            prStmt.executeUpdate();
            prStmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (prStmt != null)
                prStmt.close();
            if (conn != null)
                conn.close();
        }
    }
}

