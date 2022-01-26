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
        List<Proposal> proposal = GalleryDAO.retrieveProposal(gallery,flag);
        List<ProposalBean>proposalBean= new ArrayList<>();
        for (Proposal p: proposal) {
            proposalBean.add(new ProposalBean(p.getIdOfferta(),p.getArtista(),p.getGalleria(),p.getFlagAccettazione()));
        }
        return proposalBean;
    }
    public ArtistBean retrieveArtistNameGallerySum(int a) {
        Artist artist = BuyerDAO.retrieveArtist(a);
        return new ArtistBean(artist.getIdArtista(),artist.getNome(),artist.getCognome());
    }

}
