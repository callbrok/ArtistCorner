package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.dao.ProposalDAO;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageFollowedArtist {

    public void removeProposta(ArtGalleryBean gallery, int idArtista) throws SQLException, ProposalNotFoundException {
        ProposalDAO.removeProposta(gallery.getGalleria(), idArtista); // rimuovo la proposta
    }

    public List<ProposalBean> retrieveProposal(ArtGalleryBean gallery, int flag) throws SentProposalNotFoundException {
        ArtGallery gal= new ArtGallery(gallery.getGalleria(), gallery.getNome(),gallery.getDescrizione(), gallery.getIndirizzo());
        List<Proposal> proposalProf = ProposalDAO.retrieveProposalFromGallery(gal,flag,"");    //lista delle proposte inviate
        List<ProposalBean>proposalBeanProf= new ArrayList<>();

        if (proposalProf.isEmpty()){
            if(flag==1) {throw new SentProposalNotFoundException("nessuna proposta accettata trovata");}
            else {throw new SentProposalNotFoundException("nessuna proposta in attesa trovata");}
        }
        for (Proposal p: proposalProf) {
            ProposalBean propBean = new ProposalBean();
            propBean.setIdOfferta(p.getIdOfferta());
            propBean.setArtista(p.getArtista());
            propBean.setGalleria(p.getGalleria());
            propBean.setFlagAccettazione(p.getFlagAccettazione());

            proposalBeanProf.add(propBean);
        }
        return proposalBeanProf;
    }

    public String retrieveArtistName(ProposalBean prop) {
        Artist name = ArtistDAO.retrieveArtistFromId(prop.getArtista());
        return name.getNome()+" "+name.getCognome(); // ritorno nome e cognome dell'artista associato all'opera

    }
}
