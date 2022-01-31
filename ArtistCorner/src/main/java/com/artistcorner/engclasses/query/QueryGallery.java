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

    public static int removeProposal(Statement stmt,int idGallery,int idArtista) throws SQLException {
       String sql= "DELETE FROM offerta WHERE artista = '"+idArtista+"' AND galleria = '"+idGallery+"';";
       return stmt.executeUpdate(sql);
    }

    public static ResultSet selectArtistId(Statement stmt, int galleria) throws SQLException {
        String sql = "SELECT * FROM offerta WHERE galleria ='" + galleria  + "';";
        return stmt.executeQuery(sql);

    }

    public static int insertProposal(Statement stmt, int idGallery, int idArtista, int flag) throws SQLException {
        String updateStatement = "INSERT INTO offerta(artista,galleria,flagAccettazione) VALUES ('"+idArtista+ "','" +idGallery+"','"+flag+"');";
        return stmt.executeUpdate(updateStatement);
    }

    public static ResultSet selectProposal(Statement stmt, int galleria,int flag,String lastAction) throws SQLException {
        String sql = "SELECT * FROM offerta WHERE galleria='"+galleria+"' AND flagAccettazione= '"+flag+"';";
        if(lastAction.equals("LAST")){sql = "SELECT * FROM offerta WHERE galleria ='" + galleria + "' ORDER BY idOfferta DESC LIMIT 2;";}
        return stmt.executeQuery(sql);
    }


}
