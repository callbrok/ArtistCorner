package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.controller.guicontroller.listviewinizializer.GuiControllerListViewInizializer;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavouritesBuyer {

    public void initializeListView(ListView<GuiControllerListViewInizializer.HBoxCell> listView, Buyer buy) throws SQLException, IOException {
        ArrayList<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
        ArtWork artWork =null;
        Artist artist = null;
        List<GuiControllerListViewInizializer.HBoxCell> list = new ArrayList<>();
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
            list.add(new GuiControllerListViewInizializer.HBoxCell("Acquista",artWork.getTitolo(), artist.getNome() + " " + artist.getCognome(), artWork.getPrezzo(), image1, artWork.getIdOpera(), "Rimuovi dai Preferiti", buy.getIdBuyer(),artWorkId));
        }

        ObservableList<GuiControllerListViewInizializer.HBoxCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);

    }
}
