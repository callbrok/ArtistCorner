package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.controller.guicontroller.viewsearchartworkgallery.GuiControllerViewSearchArtWorkGallery;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchArtWorkGallery {
    public void initializeListView(String input, ListView<GuiControllerViewSearchArtWorkGallery.HBoxCell> listView, ArtGalleryBean gal) throws SQLException, IOException {
        ArrayList<ArtWork> artWorkList = BuyerDAO.retrieveArtWorkByName(input);
        ArrayList<Integer> artistIdList = GalleryDAO.retrieveArtistId(gal.getGalleria());
        List<GuiControllerViewSearchArtWorkGallery.HBoxCell> list = new ArrayList<>();
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
            list.add(new GuiControllerViewSearchArtWorkGallery.HBoxCell(input,listView,gal,work.getTitolo(), artist.getNome() + " " + artist.getCognome(), image1, work.getIdOpera(),"Invia Proposta", gal.getGalleria(),work.getArtistaId(),artistIdList));
        }

        ObservableList<GuiControllerViewSearchArtWorkGallery.HBoxCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);
    }



    public String manageButtonClick(Button buttonPreferiti, int idGallery, int idArtista) throws SQLException {
        int flag =0;
        switch (buttonPreferiti.getText()){
            case "Ritira Proposta":{
                GalleryDAO.removeProposta(idGallery,idArtista);
                return "Invia Proposta";
               // break;

            }
            case "Invia Proposta":{
                GalleryDAO.addProposta(idGallery,idArtista,flag);
                return "Ritira Proposta";
             //   break;

            }
            default:
        }
        return "Ritira Proposta";
    }
}

