package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.SellArtWorkNotFoundException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;

import java.util.ArrayList;

public class ViewSalesHistory {

    public ArrayList<ArtWork> retrieveSellArtWorks(Artist art) throws SellArtWorkNotFoundException {

        ArrayList<ArtWork> arrayOfArtwork = ArtistDAO.retrieveSellArtWorks(art.getIdArtista());

        if(arrayOfArtwork == null){
            throw new SellArtWorkNotFoundException("Nessuna opera venduta.");
        }

        return arrayOfArtwork;
    }


}
