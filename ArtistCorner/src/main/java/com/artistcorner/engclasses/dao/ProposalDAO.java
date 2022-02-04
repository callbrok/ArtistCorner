package com.artistcorner.engclasses.dao;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.exceptions.ProposalsManagementProblemException;
import com.artistcorner.engclasses.others.ConnectProperties;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.engclasses.query.QueryGallery;
import com.artistcorner.model.Proposal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProposalDAO {

    public static List<Proposal> retrieveArtGalleryProposals(int idUsr, String lastAction){
        ArrayList<Proposal> listOfProposal = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,   // Creazione ed esecuzione della query
                    ResultSet.CONCUR_READ_ONLY);

            // In pratica i risultati delle query possono essere visti come un Array Associativo o un Map
            ResultSet rs = QueryArtist.selectAllGalleryProposals(stmt, idUsr, lastAction);

            if (!rs.first()){ // rs empty
                return Collections.emptyList();
            }

            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
                int idOfferta = rs.getInt("idOfferta");
                int galleria = rs.getInt("galleria");
                int flagAccettazione = rs.getInt("flagAccettazione");

                Proposal pr = new Proposal(idOfferta, idUsr, galleria, flagAccettazione);
                listOfProposal.add(pr);

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
            } catch (SQLException se7) {
                se7.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se8) {
                se8.printStackTrace();
            }
        }

        return listOfProposal;
    }


    public static void updateProposal(int idOfferta, int newFlag) throws SQLException {
        // STEP 1: dichiarazioni
        Statement stmt = null;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            int result = QueryArtist.updateProposal(stmt, idOfferta, newFlag);

            if(result == -1){throw new ProposalsManagementProblemException("Problema nella gestione della proposta della galleria");}

        } catch (Exception e5){
            e5.printStackTrace();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

    }

    public static void removeProposta(int idGallery, int idArtista) throws SQLException {
        Statement stmt = null;
        int result;
        Connection conn = null;

        try {
            Class.forName(ConnectProperties.getDriverClassName());    // Loading dinamico del driver mysql
            conn = ConnectProperties.getConnection();    // Apertura connessione

            // STEP 4.1: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            result = QueryGallery.removeProposal(stmt,idGallery,idArtista);

            if(result == -1){throw new ProposalsManagementProblemException("Problema nella gestione della proposta della galleria");}

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

            int result = QueryGallery.insertProposal(stmt,idGallery,idArtista,flag);
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
            ResultSet rs = QueryGallery.selectProposal(stmt, galleria.getGalleria(),flag,"");


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



}
