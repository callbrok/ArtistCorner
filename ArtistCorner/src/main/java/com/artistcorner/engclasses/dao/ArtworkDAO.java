package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtworkManagementProblemException;
import com.artistcorner.engclasses.exceptions.DuplicateArtworkException;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.engclasses.query.QueryArtwork;
import com.artistcorner.model.Artwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtworkDAO {

    private ArtworkDAO(){

        throw new IllegalStateException("Utility class");
    }
    public static final String BUY_PROBLEM = "Problema nella gestione dell'acquisto";
    public static final String TITOLO = "titolo";
    public static final String FLAG_VENDIBILE= "flagVendibile";
    public static final String PREZZO = "prezzo";
    public static final String ID_OPERA = "idOpera";
    public static final String ARTISTA = "artista";
    public static final String CATEGORIA = "categoria";
    public static final String IMMAGINE = "immagine";

    public static List<Artwork> retrieveSellArtWorks(int idUsr){
        ArrayList<Artwork> listOfArtWork = new ArrayList<>();
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

                String titolo = rs.getString(TITOLO);
                int venduto = rs.getInt(FLAG_VENDIBILE);
                double prezzo = rs.getDouble(PREZZO);
                int idOpera = rs.getInt(ID_OPERA);
                int artistaId = rs.getInt(ARTISTA);
                String categoria = rs.getString(CATEGORIA);
                Blob immagine = rs.getBlob(IMMAGINE);

                Artwork at = new Artwork(idOpera, titolo, prezzo, venduto,artistaId,categoria, immagine);

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
    public static List<Artwork> retrieveAllArtWorks(int idUsr, String lastAction){
        ArrayList<Artwork> listOfArtWork = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllArtWork(stmt, idUsr, lastAction);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo1 = rs.getString(TITOLO);
                int venduto1 = rs.getInt(FLAG_VENDIBILE);
                double prezzo1 = rs.getDouble(PREZZO);
                int idOpera1 = rs.getInt(ID_OPERA);
                int artistaId1 = rs.getInt(ARTISTA);
                String categoria1 = rs.getString(CATEGORIA);
                Blob immagine1 = rs.getBlob(IMMAGINE);

                Artwork at = new Artwork(idOpera1, titolo1, prezzo1, venduto1,artistaId1,categoria1, immagine1);

                listOfArtWork.add(at);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se30) {
                se30.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se40) {
                se40.printStackTrace();
            }
        }

        return listOfArtWork;
    }
    public static void saveArtWork(Artwork upArt, String filePath) throws DuplicateArtworkException {
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
            ResultSet rs = QueryArtwork.selectArtWorkTitle(stmt);
            while (rs.next()) {
                // lettura delle colonne "by name"
                String artworkTitle = rs.getString(TITOLO);

                if (artworkTitle.equals(upArt.getTitolo())){

                    throw new DuplicateArtworkException("Opera attualmente gia' caricata.");

                }
            }

            rs.close();
            stmt.close();

            prStmt = conn.prepareStatement(QueryArtist.insertArtWork());   // Prende la query SQL.

            // Setta i prepared statement.
            prStmt.setString(1, upArt.getTitolo());
            prStmt.setDouble(2, upArt.getPrezzo());
            prStmt.setInt(3, upArt.getFlagVenduto());
            prStmt.setBinaryStream(4, fis, (int) file.length());
            prStmt.setInt(5, upArt.getArtistaId());
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
    public static void removeArtWork(Artwork instance) {
        // STEP 1: dichiarazioni
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            QueryArtist.deleteArtWork(stmt, instance);

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
    public static Artwork retrieveArtWorks(int idUsr, int flag){
        Artwork artWork=null;
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtwork.selectArtWork(stmt, idUsr,flag);

            if (!rs.first()){ // rs empty
                throw new ArtworkNotFoundException("Opera non trovata");
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo2 = rs.getString(TITOLO);
                int venduto2 = rs.getInt(FLAG_VENDIBILE);
                double prezzo2 = rs.getDouble(PREZZO);
                int idOpera2 = rs.getInt(ID_OPERA);
                int artistaId2= rs.getInt(ARTISTA);
                String categoria2 = rs.getString(CATEGORIA);
                Blob immagine2 = rs.getBlob(IMMAGINE);

                artWork = new Artwork(idOpera2, titolo2, prezzo2, venduto2,artistaId2,categoria2,immagine2);



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
    public static List<Artwork> retrieveArtWorkId(int idUsr) {
        List<Artwork> listOfArtWorkId = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtwork.selectArtWorkId(stmt, idUsr);


            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo4 = rs.getString(TITOLO);
                int venduto4 = rs.getInt(FLAG_VENDIBILE);
                double prezzo4 = rs.getDouble(PREZZO);
                int idOpera4 = rs.getInt(ID_OPERA);
                int artistaId4 = rs.getInt(ARTISTA);
                String categoria4 = rs.getString(CATEGORIA);
                Blob immagine4 = rs.getBlob(IMMAGINE);

                Artwork artWork = new Artwork(idOpera4, titolo4, prezzo4, venduto4,artistaId4,categoria4,immagine4);

                listOfArtWorkId.add(artWork);

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
    public static void switchFlagVendibile(int idOpera) throws SQLException {
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryArtwork.switchFlagVendibile(stmt,idOpera);
            if (result==-1){throw new BuyArtworkManagementProblemException(BUY_PROBLEM);
            }


        } catch (ClassNotFoundException | BuyArtworkManagementProblemException ex6) {
            ex6.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

    }
    public static List<Artwork> retrieveArtWorkByNameGallery(String input, String category) {
        input= input.replace("","%");
        List<Artwork> artWork = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtwork.selectArtWorkByNameGallery(stmt, input, category);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo5 = rs.getString(TITOLO);
                int venduto5 = rs.getInt(FLAG_VENDIBILE);
                double prezzo5 = rs.getDouble(PREZZO);
                int idOpera5 = rs.getInt(ID_OPERA);
                int artistaId5 = rs.getInt(ARTISTA);
                String categoria5 = rs.getString(CATEGORIA);
                Blob immagine5 = rs.getBlob(IMMAGINE);

                Artwork art = new Artwork(idOpera5, titolo5, prezzo5, venduto5,artistaId5,categoria5,immagine5);
                artWork.add(art);


            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception ex91) {
            ex91.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se23) {
                se23.printStackTrace();
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
    public static List<Artwork> retrieveArtWorkByName(String input, String category) {
        input= input.replace("","%");
        List<Artwork> artWork = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtwork.selectArtWorkByName(stmt, input, category);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo6 = rs.getString(TITOLO);
                int venduto6 = rs.getInt(FLAG_VENDIBILE);
                double prezzo6 = rs.getDouble(PREZZO);
                int idOpera6 = rs.getInt(ID_OPERA);
                int artistaId6 = rs.getInt(ARTISTA);
                String categoria6 = rs.getString(CATEGORIA);
                Blob immagine6 = rs.getBlob(IMMAGINE);

                Artwork art = new Artwork(idOpera6, titolo6, prezzo6, venduto6,artistaId6,categoria6,immagine6);
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
    public static List<Artwork> retrieveAllComprate(int idBuyer) {
        List<Artwork> comprate = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtwork.selectOpereComprate(stmt, idBuyer);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String titolo3 = rs.getString(TITOLO);
                int venduto3 = rs.getInt(FLAG_VENDIBILE);
                double prezzo3 = rs.getDouble(PREZZO);
                int idOpera3 = rs.getInt(ID_OPERA);
                int artistaId3 = rs.getInt(ARTISTA);
                String categoria3 = rs.getString(CATEGORIA);
                Blob immagine3 = rs.getBlob(IMMAGINE);

                Artwork aw = new Artwork(idOpera3, titolo3, prezzo3, venduto3,artistaId3,categoria3,immagine3);
                comprate.add(aw);


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
