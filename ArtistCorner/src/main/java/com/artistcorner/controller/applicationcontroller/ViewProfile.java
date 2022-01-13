package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.model.Artist;

import java.sql.Blob;
import java.util.ArrayList;

public class ViewProfile {

    public ArrayList<Blob> retrieveAllArtWorksImage(ArtistBean artBean) throws ArtWorkNotFoundException {

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());

        ArrayList<Blob> listOfArtWorksImage = ArtistDAO.retrieveAllArtWorksImage(art.getIdArtista());  // Prendi tutte le opere caricate dall'artista.

        if(listOfArtWorksImage == null){
            throw new ArtWorkNotFoundException("Nessun opera caricata");
        }

        return listOfArtWorksImage;

    }

}
