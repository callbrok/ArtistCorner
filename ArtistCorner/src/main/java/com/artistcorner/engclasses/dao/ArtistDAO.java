package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.*;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ArtistDAO {

    private ArtistDAO(){
        // Utility classes, which are collections of static members, are not meant to be instantiated. Even abstract utility classes,
        // which can be extended, should not have public constructors.
        //
        // Java adds an implicit public constructor to every class which does not define at least one explicitly. Hence,
        // at least one non-public constructor should be defined.
        throw new IllegalStateException("Utility class");
    }

    public static Artist retrieveArtist(UserBean usr){
        Artist ar = null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectArtist(stmt, usr.getUsername());

            if (!rs.first()){ // rs empty
                throw new ArtistNotFoundException("Artista non trovato");
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String cognomeArtista = rs.getString("cognome");
                String nomeArtista = rs.getString("nome");
                int idArtista = rs.getInt("idArtista");

                ar = new Artist(idArtista, nomeArtista, cognomeArtista);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return ar;
    }


    public static void insertArtist(User user, Artist art) throws DuplicateUserException, SQLException {
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
            result = QueryArtist.insertArtist(stmt, art, user.getUsername());

            if(result == -1){throw new AddArtistException("Impossibile aggiungere artista");}

        } catch (SQLException | AddArtistException | ClassNotFoundException e5){
            e5.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

    }


    public static List<ArtWork> retrieveSellArtWorks(int idUsr){
        ArrayList<ArtWork> listOfArtWork = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectSellArtWork(stmt, idUsr);

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

                ArtWork at = new ArtWork(idOpera, titolo, prezzo, venduto,artistaId,categoria, immagine);
                listOfArtWork.add(at);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se3) {
                se3.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se4) {
                se4.printStackTrace();
            }
        }

        return listOfArtWork;
    }


    public static List<ArtWork> retrieveAllArtWorks(int idUsr){
        ArrayList<ArtWork> listOfArtWork = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllArtWork(stmt, idUsr);

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

                ArtWork at = new ArtWork(idOpera, titolo, prezzo, venduto,artistaId,categoria, immagine);
                listOfArtWork.add(at);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se3) {
                se3.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se4) {
                se4.printStackTrace();
            }
        }

        return listOfArtWork;
    }


    public static void saveArtWork(ArtWorkBean upArt, String filePath) throws DuplicateArtWorkException{
        Statement stmt = null;
        PreparedStatement prStmt = null;
        Connection conn = null;

        File file = new File(filePath); // Apre il file relativo al path passato.

        try(FileInputStream fis = new FileInputStream(new File(filePath))) {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // Check Duplicate ArtWork
                ResultSet rs = QueryArtist.selectArtWorkTitle(stmt);
                while (rs.next()) {
                    // lettura delle colonne "by name"
                    String artworkTitle = rs.getString("titolo");

                    if (artworkTitle.equals(upArt.getTitolo())){
                        throw new DuplicateArtWorkException("Opera attualmente già caricata.");
                    }
                }

                rs.close();
                stmt.close();

            prStmt = conn.prepareStatement(QueryArtist.insertArtWork());   // Prende la query SQL.

            // Setta i prepared statement.
            prStmt.setString(1, upArt.getTitolo());
            prStmt.setDouble(2, upArt.getPrezzo());
            prStmt.setInt(3, upArt.getFlagVendibile());
            prStmt.setBinaryStream(4, fis, (int) file.length());
            prStmt.setInt(5, upArt.getArtistId());
            prStmt.setString(6, upArt.getCategoria());

            prStmt.executeUpdate();
            prStmt.close();

        } catch ( SQLException | ClassNotFoundException | IOException e2) {
            e2.printStackTrace();
        } finally {
            if (prStmt != null) {
                try {
                    prStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeArtWork(ArtWork instance) {
        // STEP 1: dichiarazioni
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryArtist.deleteArtWork(stmt, instance);

        }catch (Exception e6){
            e6.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static List<Blob> retrieveAllArtWorksImage(int idUsr, String lastAction){
        ArrayList<Blob> listOfArtWorksImage = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllArtWorksImage(stmt, idUsr, lastAction);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                Blob immagine = rs.getBlob("immagine");
                listOfArtWorksImage.add(immagine);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se5) {
                se5.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se6) {
                se6.printStackTrace();
            }
        }

        return listOfArtWorksImage;
    }


    public static List<Proposal> retrieveArtGalleryProposals(int idUsr, String lastAction){
        ArrayList<Proposal> listOfProposal = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllGalleryProposals(stmt, idUsr, lastAction);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int idOfferta = rs.getInt("idOfferta");
                int galleria = rs.getInt("galleria");
                int flagAccettazione = rs.getInt("flagAccettazione");

                Proposal pr = new Proposal(idOfferta, idUsr, galleria, flagAccettazione);
                listOfProposal.add(pr);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e4) {
            e4.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se7) {
                se7.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se8) {
                se8.printStackTrace();
            }
        }

        return listOfProposal;
    }


    public static void updateProposal(int idOfferta, int newFlag) throws SQLException {
        // STEP 1: dichiarazioni
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryArtist.updateProposal(stmt, idOfferta, newFlag);

            if(result == -1){throw new ProposalsManagementProblemException("Problema nella gestione della proposta della galleria");}

        } catch (Exception e5){
          e5.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

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
                String username = rs.getString("username");

                artG = new ArtGallery(idGalleria, nome, descrizione, indirizzo, username);

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
            } catch (SQLException se9) {
                se9.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se10) {
                se10.printStackTrace();
            }
        }

        return artG;
    }





}
