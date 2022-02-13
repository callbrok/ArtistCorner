package com.artistcorner.engclasses.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryArtwork {

    private QueryArtwork(){throw new IllegalStateException("Utility Query Artist class");}

    public static ResultSet selectArtWorkTitle(Statement stmt) throws SQLException  {
        String sql = "SELECT DISTINCT titolo FROM opera ;";
        return stmt.executeQuery(sql);
    }
    public static ResultSet selectArtWorkId(Statement stmt, int idBuyer) throws SQLException {
        String sql = "SELECT * FROM opera JOIN preferito ON opera.idOpera = preferito.opera WHERE acquirente ='" + idBuyer  + "';";
        return stmt.executeQuery(sql);
    }
    public static int switchFlagVendibile(Statement stmt, int idOpera) throws SQLException {
        String sql="UPDATE opera SET flagVendibile = 0 WHERE idOpera ="+idOpera+";";
        return stmt.executeUpdate(sql);
    }
    public static ResultSet selectArtWorkByNameGallery(Statement stmt, String input, String category) throws SQLException {
        String sql = "SELECT * FROM opera WHERE titolo LIKE'" +input+ "';";

        if(!category.equals("")){sql = sql.substring(0, sql.length() - 1) + " AND categoria ='" + category + "';";}

        return stmt.executeQuery(sql);

    }
    public static ResultSet selectArtWorkByName(Statement stmt, String input, String category) throws SQLException {
        String sql = "SELECT * FROM opera WHERE titolo LIKE'" +input+ "' AND flagVendibile =1;";

        if(!category.equals("")){sql = sql.substring(0, sql.length() - 1) + " AND categoria ='" + category + "';";}

        return stmt.executeQuery(sql);

    }
    public static  ResultSet selectOpereComprate(Statement stmt ,int idBuyer) throws SQLException {
        String sql = "SELECT * FROM opera JOIN compra ON opera.idOpera = compra.opera WHERE acquirente =" + idBuyer + ";";
        return stmt.executeQuery(sql);
    }
    public static ResultSet selectArtWork(Statement stmt, int idOpera,int flag) throws SQLException {
        String sql = "SELECT * FROM opera WHERE idOpera ='" + idOpera + "' AND flagVendibile ="+flag+";";
        return stmt.executeQuery(sql);
    }
}
