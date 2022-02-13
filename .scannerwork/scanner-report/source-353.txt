package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Buyer;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewBuyerSummary {

    public List<Integer> retrieveAllComprate(int idBuyer)  {
        return BuyerDAO.retrieveAllComprate(idBuyer);
    }

    public ArtWorkBean retrieveArtWorks(int integer,int flag) {
        ArtWork a = BuyerDAO.retrieveArtWorks(integer, flag);
        return new ArtWorkBean(a.getIdOpera(),a.getTitolo(),a.getPrezzo(),a.getFlagVenduto(),a.getArtistaId(),a.getCategoria());
    }
}
