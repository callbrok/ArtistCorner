package com.artistcorner.engclasses.query;

import com.artistcorner.model.Proposal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryArtist {

    public static ResultSet selectArtist(Statement stmt, String user) throws SQLException {
        String sql = "SELECT * FROM artista WHERE username ='" + user + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectSellArtWork(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT * FROM opera JOIN compra ON opera.idOpera = compra.opera WHERE artista =" + idArtista + ";";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllArtWorksImage(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT immagine FROM opera WHERE artista ='" + idArtista + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllGalleryProposals(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT * FROM offerta WHERE artista ='" + idArtista + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static int updateProposal(Statement stmt, int idOfferta, int newFlag) throws SQLException  {
        String updateStatement = String.format("UPDATE offerta SET flagAccettazione='%s' WHERE idOfferta = %s", newFlag, idOfferta);
        System.out.println(updateStatement);
        return stmt.executeUpdate(updateStatement);
    }

    public static String insertArtWork() throws SQLException {
        return "INSERT INTO opera(titolo, prezzo, flagVendibile, immagine, artista) VALUES (?, ?, ?, ?, ?)";
    }

    public static ResultSet selectArtGallery(Statement stmt, int artGallery) throws SQLException {
        String sql = "SELECT * FROM galleria WHERE idGalleria ='" + artGallery + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }


}
