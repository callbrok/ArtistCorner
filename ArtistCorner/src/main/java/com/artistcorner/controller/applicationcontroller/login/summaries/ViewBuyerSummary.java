package com.artistcorner.controller.applicationcontroller.login.summaries;

import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.ArtworkDAO;
import com.artistcorner.model.Artwork;

import java.util.List;

public class ViewBuyerSummary {

    public List<ArtworkBean> retrieveAllComprate(BuyerBean buyB)  {
        return ArtworkDAO.retrieveAllComprate(buyB.getIdBuyer());
    }

    public ArtworkBean retrieveArtWorks(int integer, int flag) {
        Artwork a = ArtworkDAO.retrieveArtWorks(integer, flag);
        ArtworkBean artWorkB = new ArtworkBean();

        artWorkB.setIdOpera(a.getIdOpera());
        artWorkB.setTitolo(a.getTitolo());
        artWorkB.setPrezzo(a.getPrezzo());
        artWorkB.setFlagVendibile(a.getFlagVenduto());
        artWorkB.setArtistId(a.getArtistaId());
        artWorkB.setCategoria(a.getCategoria());
        artWorkB.setImmagine(a.getImmagine());

        return artWorkB;
    }
}
