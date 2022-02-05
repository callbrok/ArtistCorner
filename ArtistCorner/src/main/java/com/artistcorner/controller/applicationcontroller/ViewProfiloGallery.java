package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.dao.ProposalDAO;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewProfiloGallery {

    public String retrieveArtistName(ProposalBean prop) {
        Artist name = ArtistDAO.retrieveArtistFromId(prop.getArtista());
        return name.getNome()+" "+name.getCognome(); // ritorno nome e cognome dell'artista associato all'opera

    }

    public List<ProposalBean> retrieveProposal(ArtGalleryBean gallery, int flag) throws SentProposalNotFoundException {
        List<Proposal> proposal = ProposalDAO.retrieveProposalFromGallery(gallery,flag,"");    //lista delle proposte inviate
        List<ProposalBean>proposalBean= new ArrayList<>();
        ProposalBean propBean = new ProposalBean();

        if (proposal.isEmpty()){
            if(flag==1) {throw new SentProposalNotFoundException("nessuna proposta accettata trovata");}
            else {throw new SentProposalNotFoundException("nessuna proposta in attesa trovata");}
        }
        for (Proposal p: proposal) {
            propBean.setIdOfferta(p.getIdOfferta());
            propBean.setArtista(p.getArtista());
            propBean.setGalleria(p.getGalleria());
            propBean.setFlagAccettazione(p.getFlagAccettazione());

            proposalBean.add(propBean);
        }
        return proposalBean;
    }
    public void removeProposta(ArtGalleryBean gallery, int idArtista) throws SQLException, ProposalNotFoundException {
        ProposalDAO.removeProposta(gallery.getGalleria(), idArtista); // rimuovo la proposta
    }
}
