package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtWorkManagementProblemException;
import com.artistcorner.engclasses.exceptions.DuplicateArtWorkException;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.engclasses.query.QueryBuyer;
import com.artistcorner.model.ArtWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtWorkDAO {
    public static final String BUY_PROBLEM = "Problema nella gestione dell'acquisto";

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


    public static List<ArtWork> retrieveAllArtWorks(int idUsr, String lastAction){
        ArrayList<ArtWork> listOfArtWork = new ArrayList<>();
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


    public static void saveArtWork(ArtWork upArt, String filePath) throws DuplicateArtWorkException {
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
