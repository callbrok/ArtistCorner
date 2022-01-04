package com.artistcorner.engclasses.query;

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

    public static ResultSet selectArtWork(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT * FROM opera WHERE artista ='" + idArtista + "' AND flagVenduto =1;";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllArtWorksImage(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT immagine FROM opera WHERE artista ='" + idArtista + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static String insertArtWork() throws SQLException {
        return "INSERT INTO opera(titolo, prezzo, flagVenduto, immagine, artista) VALUES (?, ?, ?, ?, ?)";
    }

}
