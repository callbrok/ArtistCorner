package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Buyer;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class SummaryBuyer {

    public void inizializeOpereComprate(ListView<String> listViewCompra, Buyer buy) {
        ArrayList<Integer> arrayOfComprate = BuyerDAO.retrieveAllComprate(buy.getIdBuyer());
        ArrayList<ArtWork> arrayFinal = new ArrayList<>();
        for (int n : arrayOfComprate) {
            ArtWork artwork = BuyerDAO.retrieveArtWorks(n, 0);
            listViewCompra.getItems().add("Titolo Opera:  " + artwork.getTitolo() + "     Prezzo di acquisto:   € " + artwork.getPrezzo());  // Popola la listView.

            arrayFinal.add(artwork);
        }

    }
}
