package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.controller.guicontroller.viewsearchartworkbuyer.GuiControllerViewSearchArtWorkBuyer;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchArtWorkBuyer {


    public void initializeListView(String input, ListView<GuiControllerViewSearchArtWorkBuyer.HBoxCell> listView, Buyer buy) throws SQLException, IOException {
        ArrayList<ArtWork> artWorkList = BuyerDAO.retrieveArtWorkByName(input);
        ArrayList<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
        List<GuiControllerViewSearchArtWorkBuyer.HBoxCell> list = new ArrayList<>();
        for (ArtWork work : artWorkList) {
            Blob immagine = BuyerDAO.retrieveImage(work.getIdOpera());
            Artist artist = BuyerDAO.retrieveArtistName(work.getArtistaId());
            InputStream inputStream = null;
            try {
                inputStream = immagine.getBinaryStream();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Image image1 = new Image(inputStream, 100, 100, true, false);
            list.add(new GuiControllerViewSearchArtWorkBuyer.HBoxCell("Acquista",work.getTitolo(), artist.getNome() + " " + artist.getCognome(), work.getPrezzo(), image1, work.getIdOpera(),"Aggiungi ai Preferiti", buy.getIdBuyer(),artWorkId));
        }

        ObservableList<GuiControllerViewSearchArtWorkBuyer.HBoxCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);
    }

    public String manageButtonClick(ActionEvent arg0, Button buttonAcquista, Button buttonPreferiti, int idOpera, int idBuyer ){

        switch (buttonPreferiti.getText()){
            case "Rimuovi dai Preferiti":{
                try {
                    BuyerDAO.removeArtWorkFromFavourites(idOpera, idBuyer);
                    return "Aggiungi ai Preferiti";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
            case "Aggiungi ai Preferiti":{
                try {
                    BuyerDAO.addArtWorkToFavourites(idOpera,idBuyer);
                    return "Rimuovi dai Preferiti";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
            default:
        }
        return "Rimuovi dai Preferiti";
    }


    public void finishPayment(int idOpera, int idBuyer){

        try {
            BuyerDAO.addArtWorkComprata(idOpera,idBuyer);
            BuyerDAO.switchFlagVendibile(idOpera);
            BuyerDAO.removeArtWorkFromFavourites(idOpera,idBuyer);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

}
