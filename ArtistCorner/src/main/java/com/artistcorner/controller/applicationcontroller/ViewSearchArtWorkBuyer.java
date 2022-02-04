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
import javafx.scene.control.Button;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchArtWorkBuyer {

    public String manageButtonClickFavourites(String buttonFavourites, ArtWorkBean artBean, BuyerBean buy ) throws FavouritesManagementProblemException {
        int idArtWork= artBean.getIdOpera();
        int idBuyer= buy.getIdBuyer();

        String addFavourites = "Aggiungi ai Preferiti";
        String remFavourites = "Rimuovi dai Preferiti";
        switch (buttonFavourites){
            case "Rimuovi dai Preferiti":{
                try {
                    ArtWorkDAO.removeArtWorkFromFavourites(idArtWork, idBuyer);
                    return addFavourites;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
            case "Aggiungi ai Preferiti":{
                try {
                    ArtWorkDAO.addArtWorkToFavourites(idArtWork,idBuyer);
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
        return ArtWorkDAO.retrieveArtWorkId(buy.getIdBuyer());
    }



    public ArtistBean retrieveSearchArtistName(ArtWorkBean a) {
        Artist artist = ArtistDAO.retrieveArtistFromId(a.getArtistId());

        ArtistBean artBean = new ArtistBean();
        artBean.setNome(artist.getNome());

        return artBean;
    }

    public List<ArtWorkBean> retrieveSearchArtWorkByName(ArtWorkBean artSearch) throws ArtWorkNotFoundException {
        String input=artSearch.getTitolo();
        String category=artSearch.getCategoria();

        List<ArtWork> artWorkList = ArtWorkDAO.retrieveArtWorkByName(input, category);
        List<ArtWorkBean> arrayArtWorkBean = new ArrayList<>();
        ArtWorkBean artWB = new ArtWorkBean();

        if (artWorkList.isEmpty()){
            throw new ArtWorkNotFoundException("Nessuna ArtWork trovata");
        }
        for (ArtWork a : artWorkList) {
            artWB.setIdOpera(a.getIdOpera());
            artWB.setTitolo(a.getTitolo());
            artWB.setPrezzo(a.getPrezzo());
            artWB.setFlagVendibile(a.getFlagVenduto());
            artWB.setArtistId(a.getArtistaId());
            artWB.setCategoria(a.getCategoria());
            artWB.setImmagine(a.getImmagine());

            arrayArtWorkBean.add(artWB);
        }
        return arrayArtWorkBean;
    }

    public void finishPayment(ArtWorkBean artBean, BuyerBean buy) throws FavouritesManagementProblemException, BuyArtWorkManagementProblemException {
        int idOpera= artBean.getIdOpera();
        int idBuyer= buy.getIdBuyer();

        try {

            ArtWorkDAO.addArtWorkComprata(idOpera,idBuyer);
            ArtWorkDAO.switchFlagVendibile(idOpera);
            ArtWorkDAO.removeArtWorkFromFavourites(idOpera,idBuyer);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
