package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.others.HBoxInitializer;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewFavouritesBuyer {

    public List<HBoxInitializer> initializeListView(BuyerBean buyer) throws SQLException, IOException {
        Buyer buy = new Buyer(buyer.getIdBuyer(),buyer.getNome(),buyer.getCognome());
        ArrayList<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
        List<HBoxInitializer> list = new ArrayList<>();
        for (Integer integer : artWorkId) {
            Blob immagine = BuyerDAO.retrieveImage(integer);
            ArtWork artWork = BuyerDAO.retrieveArtWorks(integer, 1);
            Artist artist = BuyerDAO.retrieveArtistName(artWork.getArtistaId());
            InputStream inputStream = null;
            try {
                inputStream = immagine.getBinaryStream();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Image image1 = new Image(inputStream, 100, 100, true, false);
            list.add(new HBoxInitializer(artWork.getTitolo(), artist.getNome() + " " + artist.getCognome(),image1, artWork.getIdOpera(), artWork.getPrezzo(),"Invia Proposta", buyer.getIdBuyer(), artist.getIdArtista(),artWorkId,""));
        }

        return list;
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
