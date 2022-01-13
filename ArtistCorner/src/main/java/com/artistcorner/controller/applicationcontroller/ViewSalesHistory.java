package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.SellArtWorkNotFoundException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;

import java.util.ArrayList;

public class ViewSalesHistory {

    public ArrayList<ArtWorkBean> retrieveSellArtWorks(ArtistBean artBean) throws SellArtWorkNotFoundException {

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());

        ArrayList<ArtWork> arrayOfArtwork = ArtistDAO.retrieveSellArtWorks(art.getIdArtista());
        ArrayList<ArtWorkBean> arrayOfArtworkBeans = new ArrayList<ArtWorkBean>();

        if(arrayOfArtwork == null){
            throw new SellArtWorkNotFoundException("Nessuna opera venduta.");
        }

        for (ArtWork n : arrayOfArtwork) {
            arrayOfArtworkBeans.add(new ArtWorkBean(n.getIdOpera(), n.getTitolo(), n.getPrezzo(), n.getFlagVenduto()));
        }

        return arrayOfArtworkBeans;
    }


}
