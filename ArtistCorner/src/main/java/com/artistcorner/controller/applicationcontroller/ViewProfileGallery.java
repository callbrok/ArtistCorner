package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.util.ArrayList;
import java.util.List;

public class ViewProfileGallery {

    public String retrieveArtistName(int idArtista) {
        Artist name =BuyerDAO.retrieveArtist(idArtista);
        return name.getNome()+" "+name.getCognome();

    }

    public List<ProposalBean> retrieveProposal(ArtGalleryBean gallery, int flag) throws SentProposalNotFoundException {
        List<Proposal> proposal = GalleryDAO.retrieveProposal(gallery,flag);
        List<ProposalBean>proposalBean= new ArrayList<>();
        if (proposal.isEmpty()){
            if(flag==1) {throw new SentProposalNotFoundException("nessuna proposta accettata trovata");}
            else {throw new SentProposalNotFoundException("nessuna proposta in attesa trovata");}
        }
        for (Proposal p: proposal) {
            proposalBean.add(new ProposalBean(p.getIdOfferta(),p.getArtista(),p.getGalleria(),p.getFlagAccettazione()));
        }
        return proposalBean;
    }
}
