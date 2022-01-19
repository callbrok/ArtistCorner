package com.artistcorner.engclasses.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryGallery {

    public static ResultSet selectGallery(Statement stmt, String username) throws SQLException {
        String sql = "SELECT * FROM galleria WHERE username ='" + username + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static int removeProposal(Statement stmt,int idGallery,int idArtista) throws SQLException {
       String sql= "DELETE FROM offerta WHERE artista = '"+idArtista+"' AND galleria = '"+idGallery+"';";
       System.out.println(sql);
       return stmt.executeUpdate(sql);
    }

    public static ResultSet selectArtistId(Statement stmt, int galleria) throws SQLException {
        String sql = "SELECT * FROM offerta WHERE galleria ='" + galleria  + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);

    }

    public static int insertProposal(Statement stmt, int idGallery, int idArtista, int flag) throws SQLException {
        String updateStatement = "INSERT INTO offerta(artista,galleria,flagAccettazione) VALUES ('"+idArtista+ "','" +idGallery+"','"+flag+"');";
        System.out.println(updateStatement);
        return stmt.executeUpdate(updateStatement);
    }

    public static ResultSet selectProposal(Statement stmt, int galleria,int flag) throws SQLException {
        String sql = "SELECT * FROM offerta WHERE galleria='"+galleria+"' AND flagAccettazione= '"+flag+"';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }
}
