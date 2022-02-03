package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.model.*;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewGallerySummary {

    public List<ProposalBean> retrieveGalleryProposal(ArtGalleryBean gallery, int flag)  {
        List<Proposal> proposal = GalleryDAO.retrieveProposal(gallery,flag,"LAST"); //lista delle proposte inviate
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
        Artist artist = BuyerDAO.retrieveArtist(a.getArtista()); //artista associato alla proposta
        ArtistBean artistBean = new ArtistBean();

        artistBean.setIdArtista(artist.getIdArtista());
        artistBean.setNome(artist.getNome());
        artistBean.setCognome(artist.getCognome());

        return artistBean;
    }

}
