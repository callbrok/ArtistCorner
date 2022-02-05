package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.ProposalsManagementProblemException;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.engclasses.query.QueryGallery;
import com.artistcorner.engclasses.query.QueryProposal;
import com.artistcorner.model.Proposal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProposalDAO {



    public static void removeProposta(int idGallery, int idArtista) throws SQLException, ProposalNotFoundException {
        Statement stmt = null;
        int result;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            result = QueryProposal.removeProposal(stmt,idGallery,idArtista);

            if(result == -1){throw new ProposalsManagementProblemException("Problema nella gestione della proposta della galleria");}
            if(result == 0){throw new ProposalNotFoundException("nessuna proposta trovata.");}
        } catch (ClassNotFoundException | SQLException | ProposalsManagementProblemException e1) {
            e1.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
    }

    public static void addProposta(int idGallery, int idArtista, int flag) throws SQLException {
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryProposal.insertProposal(stmt,idGallery,idArtista,flag);
            if(result==-1){throw new ProposalsManagementProblemException("Problema nella gestione della proposta della galleria");}

        } catch (ClassNotFoundException | SQLException | ProposalsManagementProblemException e2) {
            e2.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }


    }


    public static List<Proposal> retrieveProposalFromGallery(ArtGalleryBean galleria, int flag, String lastAction){
        List<Proposal> listOfProposal = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryProposal.selectProposal(stmt, galleria.getGalleria(),flag,"");


            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int art = rs.getInt("artista");
                int idOff = rs.getInt("idOfferta");
                int galle = rs.getInt("galleria");
                Proposal prop = new Proposal(idOff,art,galle,0);
                listOfProposal.add(prop);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e4) {
            e4.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException sqle5) {
                sqle5.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle6) {
                sqle6.printStackTrace();
            }
        }

        return listOfProposal;
    }

    public static List<ArtistBean> retrieveArtistIdOfferta(int galleria) {
        List<ArtistBean> listOfArtistId = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryProposal.selectProposalArtistId(stmt, galleria);


            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int art = rs.getInt("artista");
                ArtistBean artistBean = new ArtistBean();
                artistBean.setIdArtista(art);
                listOfArtistId.add(artistBean);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException sqle3) {
                sqle3.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle4) {
                sqle4.printStackTrace();
            }
        }

        return listOfArtistId;
    }


}
