package com.artistcorner.engclasses.query;

import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.Buyer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryGallery {

    private QueryGallery(){throw new IllegalStateException("Utility Query ArtGallery class");}

    public static ResultSet selectGallery(Statement stmt, String username) throws SQLException {
        String sql = "SELECT * FROM galleria WHERE username ='" + username + "';";
        return stmt.executeQuery(sql);
    }
    public static int insertGallery(Statement stmt, ArtGallery artGalleryInsert, String username) throws SQLException  {
        String insertStatement = String.format("INSERT INTO galleria (nome,descrizione,indirizzo,username) VALUES ('%s','%s','%s','%s')", artGalleryInsert.getNome(), artGalleryInsert.getDescrizione(),artGalleryInsert.getIndirizzo(), username);
        return stmt.executeUpdate(insertStatement);
    }

}
