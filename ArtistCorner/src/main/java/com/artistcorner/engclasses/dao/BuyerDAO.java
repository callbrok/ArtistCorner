package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.*;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.engclasses.query.QueryBuyer;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import com.artistcorner.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuyerDAO {

    public static final String BUY_PROBLEM = "Problema nella gestione dell'acquisto";

    private BuyerDAO(){

        throw new IllegalStateException("Utility class");
    }

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
                String usernameRetrived = rs.getString("username");

                if (usernameRetrived.equals(user.getUsername())){
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
                throw new ArtWorkNotFoundException("Opera non trovata");
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
                String categoria = rs.getString("categoria");
                Blob immagine = rs.getBlob("immagine");

                artWork = new ArtWork(idOpera, titolo, prezzo, venduto,artistaId,categoria,immagine);


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception ex1) {
            ex1.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se22) {
                se22.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }

        return artWork;
    }


    public static Artist retrieveArtist(int idUsr) {
        Artist artist = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectAllArtist(stmt, idUsr);

            if (!rs.first()){ // rs empty
               throw new ArtistNotFoundException("Artista non trovato");
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
        } catch (Exception ex3) {
            ex3.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se24) {
                se24.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se4) {
                se4.printStackTrace();
            }
        }

        return artist;

    }

    public static List<Integer> retrieveArtWorkId(int idUsr) {
        List<Integer> listOfArtWorkId = new ArrayList<>();
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
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int name = rs.getInt("opera");
                ArtWork artWork = new ArtWork(name,null,0,0,0, "",null);
                listOfArtWorkId.add(artWork.getIdOpera());

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception ex4) {
            ex4.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se25) {
                se25.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se5) {
                se5.printStackTrace();
            }
        }

        return listOfArtWorkId;
    }


    public static void addArtWorkComprata(int artWorkId, int idBuyer) throws SQLException, BuyArtWorkManagementProblemException {
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryBuyer.insertOperaComprata(stmt,artWorkId, idBuyer);
            if (result==0){throw new BuyArtWorkManagementProblemException(BUY_PROBLEM);
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
            if (result==-1){throw new BuyArtWorkManagementProblemException(BUY_PROBLEM);
            }


        } catch (ClassNotFoundException | BuyArtWorkManagementProblemException ex6) {
            ex6.printStackTrace();
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
            if (result==0){throw new FavouritesManagementProblemException("Problema nella gestione dei preferiti");
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

    public static List<ArtWork> retrieveArtWorkByName(String input, String category) {
        input= input.replace("","%");
        List<ArtWork> artWork = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryBuyer.selectArtWorkByName(stmt, input, category);

            if (!rs.first()){ // rs empty
               return Collections.emptyList();
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
                String categoria = rs.getString("categoria");
                Blob immagine = rs.getBlob("immagine");

                ArtWork art = new ArtWork(idOpera, titolo, prezzo, venduto,artistaId,categoria,immagine);
                artWork.add(art);


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception ex9) {
            ex9.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se26) {
                se26.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se6) {
                se6.printStackTrace();
            }
        }

        return artWork;
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
    public static List<Integer> retrieveAllComprate(int idBuyer) {
        List<Integer> comprate = new ArrayList<>();
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
                return Collections.emptyList();
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
        } catch (Exception ex11) {
            ex11.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se27) {
                se27.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se7) {
                se7.printStackTrace();
            }
        }

        return comprate;

    }
}

