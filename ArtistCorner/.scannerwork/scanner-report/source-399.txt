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

public class ViewPendingProposals {

    public String retrieveArtistName(ProposalBean prop) {
        int idArtista= prop.getArtista();

        Artist name = ArtistDAO.retrieveArtistFromId(idArtista);
        return name.getNome()+" "+name.getCognome(); // ritorno nome e cognome dell'artista associato all'opera

    }

    public List<ProposalBean> retrieveProposal(ArtGalleryBean gallery, int flag) throws SentProposalNotFoundException {
        ArtGallery ag = new ArtGallery(gallery.getGalleria(),gallery.getNome(),gallery.getDescrizione(),gallery.getIndirizzo());
        List<Proposal> proposal = ProposalDAO.retrieveProposalFromGallery(ag,flag,"");    //lista delle proposte inviate
        List<ProposalBean>proposalBean= new ArrayList<>();


        if (proposal.isEmpty()){
            if(flag==1) {throw new SentProposalNotFoundException("nessuna proposta accettata trovata");}
            else {throw new SentProposalNotFoundException("nessuna proposta in attesa trovata");}
        }

        for (Proposal p: proposal) {
            ProposalBean propBeanSent = new ProposalBean();
            propBeanSent.setIdOfferta(p.getIdOfferta());
            propBeanSent.setArtista(p.getArtista());
            propBeanSent.setGalleria(p.getGalleria());
            propBeanSent.setFlagAccettazione(p.getFlagAccettazione());

            proposalBean.add(propBeanSent);
        }
        return proposalBean;
    }
    public void  removeProposta(ArtGalleryBean gallery, int idArtista) throws SQLException, ProposalNotFoundException {
        ProposalDAO.removeProposta(gallery.getGalleria(),idArtista); // rimuovo la proposta
    }
}
