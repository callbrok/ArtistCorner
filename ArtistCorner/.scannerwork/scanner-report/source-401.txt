package com.artistcorner.controller.applicationcontroller.login.summaries;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.ProposalDAO;
import com.artistcorner.model.*;

import java.util.ArrayList;
import java.util.List;

public class ViewGallerySummary {

    public List<ProposalBean> retrieveGalleryProposal(ArtGalleryBean gallery, int flag)  {
        List<Proposal> proposal = ProposalDAO.retrieveProposalFromGallery(gallery,flag,"LAST"); //lista delle proposte inviate
        List<ProposalBean>proposalBean= new ArrayList<>();
        ProposalBean currentProp = new ProposalBean();

        for (Proposal p: proposal) {
            currentProp.setIdOfferta(p.getIdOfferta());
            currentProp.setArtista(p.getArtista());
            currentProp.setGalleria(p.getGalleria());
            currentProp.setFlagAccettazione(p.getFlagAccettazione());

            proposalBean.add(currentProp);
        }
        return proposalBean;
    }
    public ArtistBean retrieveArtistNameGallerySum(ProposalBean a) {
        Artist artist = ArtistDAO.retrieveArtistFromId(a.getArtista()); //artista associato alla proposta
        ArtistBean artistBean = new ArtistBean();

        artistBean.setIdArtista(artist.getIdArtista());
        artistBean.setNome(artist.getNome());
        artistBean.setCognome(artist.getCognome());

        return artistBean;
    }

}
