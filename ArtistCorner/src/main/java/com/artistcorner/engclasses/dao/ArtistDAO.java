package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.UploadingArtWork;
import com.artistcorner.engclasses.bean.User;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;


public class ArtistDAO {

    public static Artist retrieveArtist(User usr){
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
                Exception e = new Exception("No Artist found");
                throw e;
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                String cognomeArtista = rs.getString("cognome");
                String nomeArtista = rs.getString("nome");
                int idArtista = rs.getInt("idArtista");

                ar = new Artist(idArtista, nomeArtista, cognomeArtista);
               // System.out.println("id: " + idArtista + "  |  nome: " + nomeArtista);

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

        return ar;
    }



    public static ArrayList<ArtWork> retrieveSellArtWorks(int idUsr){
        ArrayList<ArtWork> listOfArtWork = new ArrayList<ArtWork>();
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
                Exception e = new Exception("No Sell ArtWork found");
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

                ArtWork at = new ArtWork(idOpera, titolo, prezzo, venduto);
                listOfArtWork.add(at);
                // System.out.println("id: " + idArtista + "  |  nome: " + nomeArtista);

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

        return listOfArtWork;
    }


    public static void saveArtWork(UploadingArtWork upArt) throws Exception {
        PreparedStatement prStmt = null;
        Connection conn = null;
        FileInputStream fis = null;


        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            File file = new File(upArt.getFilePath()); // Apre il file relativo al path passato.
            fis = new FileInputStream(file);
            prStmt = conn.prepareStatement(QueryArtist.insertArtWork());   // Prende la query SQL.


            // Setta i prepared statement.
            prStmt.setString(1, upArt.getTitolo());
            prStmt.setDouble(2, upArt.getPrezzo());
            prStmt.setInt(3, upArt.getFlagVendibile());
            prStmt.setBinaryStream(4, fis, (int) file.length());
            prStmt.setInt(5, upArt.getIdArtist());

            prStmt.executeUpdate();
            prStmt.close();
            fis.close();

        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (fis != null)
                fis.close();
            if (prStmt != null)
                prStmt.close();
            if (conn != null)
                conn.close();
        }
    }



    public static ArrayList<Blob> retrieveAllArtWorksImage(int idUsr){
        ArrayList<Blob> listOfArtWorksImage = new ArrayList<Blob>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllArtWorksImage(stmt, idUsr);

            if (!rs.first()){ // rs empty
                Exception e = new Exception("No ArtWork found");
                throw e;
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

        return listOfArtWorksImage;
    }


    public static ArrayList<Proposal> retrieveArtGalleryProposals(int idUsr){
        ArrayList<Proposal> listOfProposal = new ArrayList<Proposal>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllGalleryProposals(stmt, idUsr);

            if (!rs.first()){ // rs empty
                Exception e = new Exception("No Proposal found");
                throw e;
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
                // System.out.println("id: " + idArtista + "  |  nome: " + nomeArtista);

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

        return listOfProposal;
    }


    public static void updateProposal(int idOfferta, int newFlag) throws Exception {
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
                Exception e = new Exception("No Artist found");
                throw e;
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
                // System.out.println("id: " + idArtista + "  |  nome: " + nomeArtista);

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

        return artG;
    }
}
