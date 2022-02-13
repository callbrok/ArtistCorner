package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.ArtworkDAO;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.model.Artwork;
import com.artistcorner.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ManageArtworks {

    public List<ArtworkBean> retrieveAllArtWorksImage(ArtistBean artBean) throws ArtworkNotFoundException {

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());

        List<Artwork> listOfAllArtWorks = ArtworkDAO.retrieveAllArtWorks(art.getIdArtista(), "");  // Prendi tutte le opere caricate dall'artista.
        ArrayList<ArtworkBean> listOfAllArtWorksBean = new ArrayList<>();


        if(listOfAllArtWorks.isEmpty()){
            throw new ArtworkNotFoundException("Nessun opera caricata");
        }

        for(Artwork n: listOfAllArtWorks){
            ArtworkBean artWBean = new ArtworkBean();
            artWBean.setIdOpera(n.getIdOpera());
            artWBean.setTitolo(n.getTitolo());
            artWBean.setImmagine(n.getImmagine());

            listOfAllArtWorksBean.add(artWBean);
        }

        return listOfAllArtWorksBean;
    }


    public void removeArtWork(ArtworkBean artWork){
        Artwork artToRemove = new Artwork(artWork.getIdOpera(), artWork.getTitolo(), artWork.getPrezzo(), artWork.getFlagVendibile(), artWork.getArtistId(), artWork.getCategoria(), artWork.getImmagine());

        ArtworkDAO.removeArtWork(artToRemove);
    }

}
