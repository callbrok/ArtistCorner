package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ViewProfile {

    public List<ArtWorkBean> retrieveAllArtWorksImage(ArtistBean artBean) throws ArtWorkNotFoundException {

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());

        List<ArtWork> listOfAllArtWorks = ArtistDAO.retrieveAllArtWorks(art.getIdArtista());  // Prendi tutte le opere caricate dall'artista.
        ArrayList<ArtWorkBean> listOfAllArtWorksBean = new ArrayList<>();

        if(listOfAllArtWorks.isEmpty()){
            throw new ArtWorkNotFoundException("Nessun opera caricata");
        }

        for(ArtWork n: listOfAllArtWorks){
            listOfAllArtWorksBean.add(new ArtWorkBean(n.getIdOpera(), n.getTitolo(), n.getPrezzo(), n.getFlagVenduto(), n.getArtistaId(), n.getCategoria(), n.getImmagine()));
        }

        return listOfAllArtWorksBean;
    }


    public void removeArtWork(ArtWorkBean artWork){
        ArtWork artToRemove = new ArtWork(artWork.getIdOpera(), artWork.getTitolo(), artWork.getPrezzo(), artWork.getFlagVendibile(), artWork.getArtistId(), artWork.getCategoria(), artWork.getImmagine());

        ArtistDAO.removeArtWork(artToRemove);
    }

}
