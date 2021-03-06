package com.artistcorner.engclasses.query;

import com.artistcorner.model.Artwork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryArtist {

    private QueryArtist(){throw new IllegalStateException("Utility Query Artist class");}

    public static final String INSERTARTWORK_QUERY = "INSERT INTO opera(titolo, prezzo, flagVendibile, immagine, artista, categoria) VALUES (?, ?, ?, ?, ?, ?)";

    public static ResultSet selectArtist(Statement stmt, String user) throws SQLException {
        String sql = "SELECT * FROM artista WHERE username ='" + user + "';";
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectUsername(Statement stmt) throws SQLException {
        String sql = "SELECT username FROM utente ;";
        return stmt.executeQuery(sql);
    }

    public static int insertUser(Statement stmt, User userInsert) throws SQLException  {
        String insertStatement = String.format("INSERT INTO utente (username, password, ruolo, email) VALUES ('%s','%s','%s', '%s')", userInsert.getUsername(), userInsert.getPassword(), userInsert.getRole(), userInsert.getEmail());
        return stmt.executeUpdate(insertStatement);
    }

    public static int insertArtist(Statement stmt, Artist artistInsert, String username) throws SQLException  {
        String insertStatement = String.format("INSERT INTO artista (nome, cognome, username) VALUES ('%s','%s','%s')", artistInsert.getNome(), artistInsert.getCognome(), username);
        return stmt.executeUpdate(insertStatement);
    }

    public static ResultSet selectSellArtWork(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT * FROM opera JOIN compra ON opera.idOpera = compra.opera WHERE artista =" + idArtista + ";";
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllArtWork(Statement stmt, int idArtista, String lastAction) throws SQLException {
        String sql = "SELECT * FROM opera WHERE artista =" + idArtista + ";";

        if(lastAction.equals("LAST")){sql = "SELECT * FROM opera WHERE artista =" + idArtista + " ORDER BY idOpera DESC LIMIT 3;";}

        return stmt.executeQuery(sql);
    }

    public static int deleteArtWork(Statement stmt, Artwork art) throws SQLException  {
        String deleteStatement = String.format("DELETE FROM  opera  WHERE idOpera = %s", art.getIdOpera());
        return stmt.executeUpdate(deleteStatement);
    }

    public static ResultSet selectAllGalleryProposals(Statement stmt, int idArtista, String lastAction) throws SQLException {
        String sql = "SELECT * FROM offerta WHERE artista ='" + idArtista + "';";

        if(lastAction.equals("LAST")){sql = "SELECT * FROM offerta WHERE artista ='" + idArtista + "' ORDER BY idOfferta DESC LIMIT 4;";}

        return stmt.executeQuery(sql);
    }

    public static int updateProposal(Statement stmt, int idOfferta, int newFlag) throws SQLException  {
        String updateStatement = String.format("UPDATE offerta SET flagAccettazione='%s' WHERE idOfferta = %s", newFlag, idOfferta);
        return stmt.executeUpdate(updateStatement);
    }

    public static String insertArtWork() {
        return INSERTARTWORK_QUERY;
    }

    public static ResultSet selectArtGallery(Statement stmt, int artGallery) throws SQLException {
        String sql = "SELECT * FROM galleria WHERE idGalleria ='" + artGallery + "';";
        return stmt.executeQuery(sql);
    }

}
