package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.ArtWorkDAO;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtWorkManagementProblemException;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class ViewFavouritesBuyer {


    public String manageButtonClick(String buttonPreferiti, ArtWorkBean art, BuyerBean buy ) throws FavouritesManagementProblemException {
        int idOpera= art.getIdOpera();
        int idBuyer= buy.getIdBuyer();

        String addPreferiti = "Aggiungi ai Preferiti";
        String remPreferiti = "Rimuovi dai Preferiti";
        switch (buttonPreferiti){
            case "Rimuovi dai Preferiti":{
                try {
                    ArtWorkDAO.removeArtWorkFromFavourites(idOpera, idBuyer);
                    return addPreferiti;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
            case "Aggiungi ai Preferiti":{
                try {
                    ArtWorkDAO.addArtWorkToFavourites(idOpera,idBuyer);
                    return remPreferiti;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
               break;

            }
            default:
        }
        return remPreferiti;
    }

    public void finishPayment(ArtWorkBean art, BuyerBean buy) throws BuyArtWorkManagementProblemException, FavouritesManagementProblemException {
                int idOpera = art.getIdOpera();
                int idBuyer = buy.getIdBuyer();

                try {
                    ArtWorkDAO.addArtWorkComprata(idOpera,idBuyer);
                    ArtWorkDAO.switchFlagVendibile(idOpera);
                    ArtWorkDAO.removeArtWorkFromFavourites(idOpera,idBuyer);

                } catch (SQLException  e) {
                    e.printStackTrace();

                }
    }

    public List<Integer> retrieveArtWorkId(BuyerBean buyer) throws ArtWorkNotFoundException {
        Buyer buy = new Buyer(buyer.getIdBuyer(),buyer.getNome(),buyer.getCognome());
        List<Integer> artWorkId = ArtWorkDAO.retrieveArtWorkId(buy.getIdBuyer());
        if (artWorkId.isEmpty()){
            throw new ArtWorkNotFoundException("Nessuna Preferito disponibile.");
        }
        return artWorkId;
    }

    public ArtWorkBean retrieveArtWork(int integer){
        ArtWork a = ArtWorkDAO.retrieveArtWorks(integer, 1);

        ArtWorkBean artWB = new ArtWorkBean();

        artWB.setIdOpera(a.getIdOpera());
        artWB.setTitolo(a.getTitolo());
        artWB.setPrezzo(a.getPrezzo());
        artWB.setFlagVendibile(a.getFlagVenduto());
        artWB.setArtistId(a.getArtistaId());
        artWB.setCategoria(a.getCategoria());
        artWB.setImmagine(a.getImmagine());

        return artWB;
    }


    public ArtistBean retrieveArtistName(ArtWorkBean a) {
        Artist artist = ArtistDAO.retrieveArtistFromId(a.getArtistId());
        ArtistBean artB = new ArtistBean();

        artB.setIdArtista(artist.getIdArtista());
        artB.setNome(artist.getNome());
        artB.setCognome(artist.getCognome());

        return artB;
    }
}
