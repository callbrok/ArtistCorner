package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class ViewFavouritesBuyer {


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
    public List<Integer> retrieveArtWorkId(BuyerBean buyer) throws ArtWorkNotFoundException {
        Buyer buy = new Buyer(buyer.getIdBuyer(),buyer.getNome(),buyer.getCognome());
        List<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
        if (artWorkId.isEmpty()){
            throw new ArtWorkNotFoundException("Nessuna Preferito disponibile.");
        }
        System.out.println(artWorkId);
        return artWorkId;
    }

    public ArtWorkBean retrieveArtWork(BuyerBean buyer,int integer){
        ArtWork a = BuyerDAO.retrieveArtWorks(integer, 1);
        ArtWorkBean artWorkBean = new ArtWorkBean(a.getIdOpera(),a.getTitolo(),a.getPrezzo(),a.getFlagVenduto(),a.getArtistaId());
        return artWorkBean;
    }

    public Blob retrieveArtWorkBlob(int n) {
        Blob immagine = BuyerDAO.retrieveImage(n);
        return immagine;
    }

    public ArtistBean retrieveArtistName(ArtWorkBean a) {
        Artist artist = BuyerDAO.retrieveArtist(a.getArtistId());
        return new ArtistBean(artist.getIdArtista(),artist.getNome(),artist.getCognome());
    }
}
