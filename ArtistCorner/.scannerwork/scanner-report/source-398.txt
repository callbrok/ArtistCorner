package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.ArtworkDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.exceptions.SellArtworkNotFoundException;
import com.artistcorner.model.Artwork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;

import java.util.ArrayList;
import java.util.List;

public class ViewSoldArtworks {

    public List<ArtworkBean> retrieveSellArtWorks(ArtistBean artBean) throws SellArtworkNotFoundException {

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());

        List<Artwork> arrayOfArtwork = ArtworkDAO.retrieveSellArtWorks(art.getIdArtista());
        ArrayList<ArtworkBean> arrayOfArtworkBeans = new ArrayList<>();



        if(arrayOfArtwork.isEmpty()){
            throw new SellArtworkNotFoundException("Nessuna opera venduta.");
        }

        for (Artwork n : arrayOfArtwork) {
            ArtworkBean artwBean = new ArtworkBean();
            artwBean.setIdOpera(n.getIdOpera());
            artwBean.setTitolo(n.getTitolo());
            artwBean.setPrezzo(n.getPrezzo());
            artwBean.setFlagVendibile(n.getFlagVenduto());
            artwBean.setArtistId(n.getArtistaId());
            artwBean.setCategoria(n.getCategoria());
            artwBean.setImmagine(n.getImmagine());

            arrayOfArtworkBeans.add(artwBean);
        }

        return arrayOfArtworkBeans;
    }

    public BuyerBean retrieveBuyerName(ArtworkBean artwork){
        BuyerBean buyerOfArtwork = new BuyerBean();

        Buyer retrievedBuyer = BuyerDAO.retrieveBuyerByArtworkId(artwork.getIdOpera());

        buyerOfArtwork.setNome(retrievedBuyer.getNome());
        buyerOfArtwork.setCognome(retrievedBuyer.getCognome());

        return buyerOfArtwork;
    }


}
