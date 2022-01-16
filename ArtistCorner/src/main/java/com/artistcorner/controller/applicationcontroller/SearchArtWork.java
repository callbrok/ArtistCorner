package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.controller.guicontroller.listviewinizializer.GuiControllerListViewInizializer;
import com.artistcorner.controller.guicontroller.viewsearchartworkbuyer.GuiControllerSearchArtWorkBuyer;
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

public class SearchArtWork {


    public void initializeListView(String input, ListView<GuiControllerListViewInizializer.HBoxCell> listView, Buyer buy) throws SQLException, IOException {
        ArrayList<ArtWork> artWorkList = BuyerDAO.retrieveArtWorkByName(input);
        ArrayList<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
        List<GuiControllerListViewInizializer.HBoxCell> list = new ArrayList<>();
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
            list.add(new GuiControllerListViewInizializer.HBoxCell("Acquista",work.getTitolo(), artist.getNome() + " " + artist.getCognome(), work.getPrezzo(), image1, work.getIdOpera(),"Aggiungi ai Preferiti", buy.getIdBuyer(),artWorkId));
        }

        ObservableList<GuiControllerListViewInizializer.HBoxCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);
    }
}
