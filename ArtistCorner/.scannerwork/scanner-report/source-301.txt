package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.exceptions.AddGalleryException;
import com.artistcorner.engclasses.exceptions.ArtGalleryNotFoundException;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.engclasses.query.QueryGallery;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GalleryDAO {

    private GalleryDAO(){
        throw new IllegalStateException("Utility class");
    }

    public static ArtGallery retrieveGallery(User loggedUser) {
        ArtGallery gal = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryGallery.selectGallery(stmt, loggedUser.getUsername());

            if (!rs.first()){ // rs empty
                throw new ArtGalleryNotFoundException("ArtGallery non trovata");
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int galleria = rs.getInt("idGalleria");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                String indirizzo = rs.getString("indirizzo");
                gal = new ArtGallery(galleria,nome,descrizione,indirizzo);


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException sqle2) {
                sqle2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return gal;
    }

    public static ArtGallery retrieveArtGallery(int idGallery){
        ArtGallery artG = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectArtGallery(stmt, idGallery);

            if (!rs.first()){ // rs empty
                throw new ArtGalleryNotFoundException("Nessuna galleria trovata");
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int idGalleria = rs.getInt("idGalleria");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                String indirizzo = rs.getString("indirizzo");

                artG = new ArtGallery(idGalleria, nome, descrizione, indirizzo);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e6) {
            e6.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se91) {
                se91.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se101) {
                se101.printStackTrace();
            }
        }

        return artG;
    }




    public static void insertGallery(User user, ArtGallery artGallery) throws DuplicateUserException,SQLException {
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
                String usernameRetrivedG = rs.getString("username");

                if (usernameRetrivedG.equals(user.getUsername())){
                    throw new DuplicateUserException("Username attualmente gia' in uso.");
                }
            }

            int result = QueryArtist.insertUser(stmt, user);
            result = QueryGallery.insertGallery(stmt,artGallery, user.getUsername());

            if(result == -1){throw new AddGalleryException("Impossibile aggiungere galleria ");}

        } catch (SQLException | AddGalleryException | ClassNotFoundException e4){
            e4.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }



    }

}
