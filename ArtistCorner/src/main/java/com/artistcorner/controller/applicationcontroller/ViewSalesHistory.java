package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.ArtWorkDAO;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.SellArtWorkNotFoundException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ViewSalesHistory {

    public List<ArtWorkBean> retrieveSellArtWorks(ArtistBean artBean) throws SellArtWorkNotFoundException {

        Artist art = new Artist(artBean.getIdArtista(), artBean.getNome(), artBean.getCognome());

        List<ArtWork> arrayOfArtwork = ArtWorkDAO.retrieveSellArtWorks(art.getIdArtista());
        ArrayList<ArtWorkBean> arrayOfArtworkBeans = new ArrayList<>();

        ArtWorkBean artwBean = new ArtWorkBean();

        if(arrayOfArtwork.isEmpty()){
            throw new SellArtWorkNotFoundException("Nessuna opera venduta.");
        }

        for (ArtWork n : arrayOfArtwork) {
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


}
