package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Buyer;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewBuyerSummary {

    public List<Integer> retrieveAllComprate(BuyerBean buyB)  {
        return BuyerDAO.retrieveAllComprate(buyB.getIdBuyer());
    }

    public ArtWorkBean retrieveArtWorks(int integer,int flag) {
        ArtWork a = BuyerDAO.retrieveArtWorks(integer, flag);
        ArtWorkBean artWorkB = new ArtWorkBean();

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
