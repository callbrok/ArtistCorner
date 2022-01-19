package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.model.*;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class ViewGallerySummary {

    public void inizializeOfferteInviate(ListView<String> listViewOfferte, ArtGalleryBean gal) {
        ArrayList<Proposal> arrayOfProposal = GalleryDAO.retrieveProposal(gal,0);
        ArrayList<String> arrayFinal = new ArrayList<>();
        for (Proposal n : arrayOfProposal) {
            Artist artist = BuyerDAO.retrieveArtistName(n.getArtista());
            String artistName =  artist.getNome()+" "+artist.getCognome();
            listViewOfferte.getItems().add("Offerta inviata in attesa di risposta per Artista :  " + artistName);  // Popola la listView.

             arrayFinal.add(artistName);
        }
    }


}
