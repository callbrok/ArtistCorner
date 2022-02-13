package com.artistcorner.engclasses.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryBuyer {

    private  QueryBuyer(){throw new IllegalStateException("Utility Query Buyer class");}

    public static ResultSet selectBuyer(Statement stmt, String user) throws SQLException {
        String sql = "SELECT * FROM acquirente WHERE username ='" + user + "';";
        return stmt.executeQuery(sql);
    }
    public static ResultSet selectArtWorkId(Statement stmt, int idBuyer) throws SQLException {
        String sql = "SELECT * FROM preferito WHERE acquirente ='" + idBuyer  + "';";
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectArtWork(Statement stmt, int idOpera,int flag) throws SQLException {
        String sql = "SELECT * FROM opera WHERE idOpera ='" + idOpera + "' AND flagVendibile ="+flag+";";
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectImage(Statement stmt, int idBuyer) throws SQLException {
        String sql = "SELECT immagine FROM opera WHERE idOpera ='" + idBuyer + "' AND flagVendibile =1;";
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllArtist(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT * FROM artista WHERE idArtista ='" + idArtista + "';";
        return stmt.executeQuery(sql);
    }
    public static int insertOperaComprata(Statement stmt, int idOpera, int idBuyer) throws SQLException {
        String updateStatement = "INSERT INTO compra(opera,acquirente) VALUES ('"+idOpera+"','"+idBuyer+"');";
        return stmt.executeUpdate(updateStatement);
    }
    public static int switchFlagVendibile(Statement stmt, int idOpera) throws SQLException {
        String sql="UPDATE opera SET flagVendibile = 0 WHERE idOpera ="+idOpera+";";
        return stmt.executeUpdate(sql);
    }
    public static int removeOperaFromFavourites(Statement stmt, int idOpera, int idBuyer) throws SQLException {
    String sql = "DELETE FROM preferito WHERE opera ='"+idOpera+ "'AND acquirente = "+idBuyer+";";
        return stmt.executeUpdate(sql);
    }

    public static ResultSet selectArtWorkByName(Statement stmt, String input, String category) throws SQLException {
        String sql = "SELECT * FROM opera WHERE titolo LIKE'" +input+ "' AND flagVendibile =1;";

        if(!category.equals("")){sql = sql.substring(0, sql.length() - 1) + " AND categoria ='" + category + "';";}

        return stmt.executeQuery(sql);

    }

    public static int insertOperaFavourites(Statement stmt, int idOpera, int idBuyer) throws SQLException {
        String updateStatement = "INSERT INTO preferito(opera,acquirente) VALUES ('"+idOpera+ "','" +idBuyer+"');";
        return stmt.executeUpdate(updateStatement);
    }

    public static  ResultSet selectOpereComprate(Statement stmt ,int idBuyer) throws SQLException {
        String sql = "SELECT * FROM compra WHERE acquirente = "+idBuyer+";";
        return stmt.executeQuery(sql);
    }

}
