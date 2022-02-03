package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
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
        List<ArtWork> listOfArtWorks = ArtistDAO.retrieveAllArtWorks(art.getIdArtista(), "LAST");  // Prendi tutte le opere caricate dall'artista.
        ArtWorkBean artWB = new ArtWorkBean();

        ArrayList<ArtWorkBean>  arrayOfArtWorkBeans = new ArrayList<>();

        for(ArtWork artW : listOfArtWorks){
            artWB.setImmagine(artW.getImmagine());
            arrayOfArtWorkBeans.add(artWB);
        }

        return arrayOfArtWorkBeans;
    }



    public List<ProposalBean> retrieveArtGalleryProposals(ArtistBean artistBean) {
        Artist art = new Artist(artistBean.getIdArtista(), artistBean.getNome(), artistBean.getCognome());
        ArrayList<ProposalBean>  arrayOfProposalBeans = new ArrayList<>();
        ProposalBean currentPropBean = new ProposalBean();

        List<Proposal> arrayOfProposals = ArtistDAO.retrieveArtGalleryProposals(art.getIdArtista(), "LAST");

        for (Proposal n : arrayOfProposals) {
            currentPropBean.setGalleria(n.getGalleria());
            arrayOfProposalBeans.add(currentPropBean);
        }

        return arrayOfProposalBeans;
    }

    public ArtGalleryBean retrieveArtGallery(ProposalBean prop){
        ArtGallery artG = ArtistDAO.retrieveArtGallery(prop.getGalleria());   // Fai un retrieve della galleria associata alla proposta.
        ArtGalleryBean artGBean = new ArtGalleryBean();

        artGBean.setNome(artG.getNome());

        return artGBean;
    }


}
