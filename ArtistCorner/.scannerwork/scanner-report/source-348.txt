package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.scene.control.Button;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchArtWorkBuyer {

    public String manageButtonClickFavourites(Button buttonFavourites, int idArtWork, int idBuyer ){
        String addFavourites = "Aggiungi ai Preferiti";
        String remFavourites = "Rimuovi dai Preferiti";
        switch (buttonFavourites.getText()){
            case "Rimuovi dai Preferiti":{
                try {
                    BuyerDAO.removeArtWorkFromFavourites(idArtWork, idBuyer);
                    return addFavourites;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
            case "Aggiungi ai Preferiti":{
                try {
                    BuyerDAO.addArtWorkToFavourites(idArtWork,idBuyer);
                    return remFavourites;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
            default:
        }
        return remFavourites;
    }

    public List<Integer> retrieveSearchArtWorkId(BuyerBean buyer){
        Buyer buy = new Buyer(buyer.getIdBuyer(),buyer.getNome(),buyer.getCognome());
        return BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
    }


    public Blob retrieveSearchArtWorkBlob(int n) {
       return BuyerDAO.retrieveImage(n);
    }

    public ArtistBean retrieveSearchArtistName(ArtWorkBean a) {
        Artist artist = BuyerDAO.retrieveArtist(a.getArtistId());
        return new ArtistBean(artist.getIdArtista(),artist.getNome(),artist.getCognome());
    }

    public List<ArtWorkBean> retrieveSearchArtWorkByName(String input) throws ArtWorkNotFoundException {
        List<ArtWork> artWorkList = BuyerDAO.retrieveArtWorkByName(input);
        List<ArtWorkBean> arrayArtWorkBean = new ArrayList<>();
        if (artWorkList.isEmpty()){
            throw new ArtWorkNotFoundException("Nessuna ArtWork trovata");
        }
        for (ArtWork a : artWorkList) {
            arrayArtWorkBean.add(new ArtWorkBean(a.getIdOpera(),a.getTitolo(),a.getPrezzo(),a.getFlagVenduto(),a.getArtistaId()));
        }
        return arrayArtWorkBean;
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
