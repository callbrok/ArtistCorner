package com.artistcorner.controller.applicationcontroller.login.summaries;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtworkDAO;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.Artwork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.util.ArrayList;
import java.util.List;

public class ViewArtistSummary {


    public List<ArtworkBean> retrieveAllArtWorksImage(ArtistBean artBean){

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());
        List<Artwork> listOfArtWorks = ArtworkDAO.retrieveAllArtWorks(art.getIdArtista(), "LAST");  // Prendi tutte le opere caricate dall'artista.


        ArrayList<ArtworkBean>  arrayOfArtWorkBeans = new ArrayList<>();

        for(Artwork artW : listOfArtWorks){
            ArtworkBean artWB = new ArtworkBean();
            artWB.setImmagine(artW.getImmagine());
            arrayOfArtWorkBeans.add(artWB);
        }

        return arrayOfArtWorkBeans;
    }



    public List<ProposalBean> retrieveArtGalleryProposals(ArtistBean artistBean) {
        Artist art = new Artist(artistBean.getIdArtista(), artistBean.getNome(), artistBean.getCognome());
        ArrayList<ProposalBean>  arrayOfProposalBeans = new ArrayList<>();

        List<Proposal> arrayOfProposals = ArtistDAO.retrieveArtGalleryProposals(art.getIdArtista(), "LAST");

        for (Proposal n : arrayOfProposals) {
            ProposalBean currentPropBean = new ProposalBean();
            currentPropBean.setGalleria(n.getGalleria());
            arrayOfProposalBeans.add(currentPropBean);
        }

        return arrayOfProposalBeans;
    }

    public ArtGalleryBean retrieveArtGallery(ProposalBean prop){
        ArtGallery artG = GalleryDAO.retrieveArtGallery(prop.getGalleria());   // Fai un retrieve della galleria associata alla proposta.
        ArtGalleryBean artGBean = new ArtGalleryBean();

        artGBean.setNome(artG.getNome());

        return artGBean;
    }


}
