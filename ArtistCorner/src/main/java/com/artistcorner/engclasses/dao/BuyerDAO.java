package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryBuyer;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import java.sql.*;
import java.util.ArrayList;

public class BuyerDAO {

    public static Buyer retrieveBuyer(UserBean usr){
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
    public static ArtWork retrieveArtWorks(int idUsr,int flag){
        ArtWork artWork=null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectArtWork(stmt, idUsr,flag);

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
                int artistaId = rs.getInt("artista");

                artWork = new ArtWork(idOpera, titolo, prezzo, venduto,artistaId);


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




    public static Artist retrieveArtistName(int idUsr) {
        Artist artist = null;
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
                artist = new Artist(idUsr,name,cognome);


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

        return artist;

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
                Exception e = new Exception("No ArtWork ID  found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int name = rs.getInt("opera");
                ArtWork artWork = new ArtWork(name,null,0,0,0);
                listOfArtWorkId.add(artWork.getIdOpera());

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


    public static void addArtWorkComprata(int artWorkId, int idBuyer) throws SQLException {
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryBuyer.insertOperaComprata(stmt,artWorkId, idBuyer);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }


    }

    public static void switchFlagVendibile(int idOpera) throws SQLException {
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryBuyer.switchFlagVendibile(stmt,idOpera);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }


    }

    public static void removeArtWorkFromFavourites(int idOpera, int idBuyer) throws SQLException {
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryBuyer.removeOperaFromFavourites(stmt,idOpera, idBuyer);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
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
                int artistaId = rs.getInt("artista");

                ArtWork art = new ArtWork(idOpera, titolo, prezzo, venduto,artistaId);
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
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryBuyer.insertOperaFavourites(stmt,artWorkId, idBuyer);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }


    }

    public static ArrayList<Integer> retrieveAllComprate(int idBuyer) {
        ArrayList<Integer> comprate = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectOpereComprate(stmt, idBuyer);

            if (!rs.first()){ // rs empty
                Exception e = new Exception("No Buyer found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int operaId = rs.getInt("opera");

                comprate.add(operaId);


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

        return comprate;

    }
}

