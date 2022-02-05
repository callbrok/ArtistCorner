package com.artistcorner.engclasses.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryProposal {

    public static int removeProposal(Statement stmt,int idGallery,int idArtista) throws SQLException {
        String sql= "DELETE FROM offerta WHERE artista = '"+idArtista+"' AND galleria = '"+idGallery+"';";
        return stmt.executeUpdate(sql);
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

    public static ResultSet selectProposalArtistId(Statement stmt, int galleria) throws SQLException {
        String sql = "SELECT * FROM offerta WHERE galleria ='" + galleria  + "';";
        return stmt.executeQuery(sql);

    }

}
