package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.controller.guicontroller.viewfavouritesbuyer.GuiControllerViewFavouritesBuyer;
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

public class ViewFavouritesBuyer {

    public void initializeListView(ListView<GuiControllerViewFavouritesBuyer.HBoxCell> listView, Buyer buy) throws SQLException, IOException {
        ArrayList<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
        ArtWork artWork =null;
        Artist artist = null;
        List<GuiControllerViewFavouritesBuyer.HBoxCell> list = new ArrayList<>();
        for (Integer integer : artWorkId) {
            Blob immagine = BuyerDAO.retrieveImage(integer);
            artWork = BuyerDAO.retrieveArtWorks(integer, 1);
            artist = BuyerDAO.retrieveArtistName(artWork.getArtistaId());
            InputStream inputStream = null;
            try {
                inputStream = immagine.getBinaryStream();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Image image1 = new Image(inputStream, 100, 100, true, false);
            list.add(new GuiControllerViewFavouritesBuyer.HBoxCell("Acquista",artWork.getTitolo(), artist.getNome() + " " + artist.getCognome(), artWork.getPrezzo(), image1, artWork.getIdOpera(), "Rimuovi dai Preferiti", buy.getIdBuyer(),artWorkId));
        }

        ObservableList<GuiControllerViewFavouritesBuyer.HBoxCell> myObservableList = FXCollections.observableList(list);
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
