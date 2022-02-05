package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtWorkDAO;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.dao.ProposalDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ViewArtistSummary {


    public List<ArtWorkBean> retrieveAllArtWorksImage(ArtistBean artBean){

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());
        List<ArtWork> listOfArtWorks = ArtWorkDAO.retrieveAllArtWorks(art.getIdArtista(), "LAST");  // Prendi tutte le opere caricate dall'artista.


        ArrayList<ArtWorkBean>  arrayOfArtWorkBeans = new ArrayList<>();

        for(ArtWork artW : listOfArtWorks){
            ArtWorkBean artWB = new ArtWorkBean();
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
