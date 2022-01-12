package com.artistcorner.engclasses.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryBuyer {

    public static ResultSet selectBuyer(Statement stmt, String user) throws SQLException {
        String sql = "SELECT * FROM acquirente WHERE username ='" + user + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }
    public static ResultSet selectArtWorkId(Statement stmt, int idBuyer) throws SQLException {
        String sql = "SELECT opera FROM preferito WHERE acquirente ='" + idBuyer  + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectArtWork(Statement stmt, int idOpera) throws SQLException {
        String sql = "SELECT * FROM opera WHERE idOpera ='" + idOpera + "' AND flagVendibile =1;";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectImage(Statement stmt, int idBuyer) throws SQLException {
        String sql = "SELECT immagine FROM opera WHERE idOpera ='" + idBuyer + "' AND flagVendibile =1;";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllArtistId(Statement stmt, int idOpera) throws SQLException {
        String sql = "SELECT artista FROM opera WHERE idOpera ='" + idOpera + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAllArtistName(Statement stmt, int idArtista) throws SQLException {
        String sql = "SELECT nome,cognome FROM artista WHERE idArtista ='" + idArtista + "';";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }
    public static String insertOperaComprata() {
        return "INSERT INTO compra(opera,acquirente,data) VALUES (?,?,?)";
    }
    public static String switchFlagVendibile(int idOpera){
        String sql="UPDATE opera SET flagVendibile = ? WHERE idOpera ="+idOpera+";";
        System.out.println(sql);
        return sql;
    }
    public static String removeOperaFromFavourites(){
    return "DELETE FROM preferito WHERE opera = ? AND acquirente = ?;";
    }


    public static ResultSet selectArtWorkByName(Statement stmt, String input) throws SQLException {
        String sql = "SELECT * FROM opera WHERE titolo LIKE'" +input+ "' AND flagVendibile =1;";
        System.out.println(sql);
        return stmt.executeQuery(sql);

    }

    public static String insertOperaFavourites() {
        return "INSERT INTO preferito(opera,acquirente) VALUES (?, ?)";
    }
}
