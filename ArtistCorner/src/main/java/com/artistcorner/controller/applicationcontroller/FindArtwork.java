package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.ArtworkDAO;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtworkManagementProblemException;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import com.artistcorner.model.Artwork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindArtwork {

    public String manageButtonClickFavourites(String buttonFavourites, ArtworkBean artBean, BuyerBean buy ) throws FavouritesManagementProblemException {
        int idArtWork= artBean.getIdOpera();
        int idBuyer= buy.getIdBuyer();

        String addFavourites = "Aggiungi ai Preferiti";
        String remFavourites = "Rimuovi dai Preferiti";
        switch (buttonFavourites){
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

    public List<ArtworkBean> retrieveSearchArtWorkId(BuyerBean buyer) throws ArtworkNotFoundException {
        Buyer buy = new Buyer(buyer.getIdBuyer(),buyer.getNome(),buyer.getCognome());
        List<Artwork> artWorkIdList = ArtworkDAO.retrieveArtWorkId(buy.getIdBuyer());
        List<ArtworkBean> artwBeanList = new ArrayList<>();
        if (artWorkIdList.isEmpty()){
            throw new ArtworkNotFoundException("Nessuna Opera disponibile.");
        }
        for (Artwork a : artWorkIdList){
            ArtworkBean awb= new ArtworkBean();
            awb.setIdOpera(a.getIdOpera());
            artwBeanList.add(awb);
        }
        return artwBeanList;
    }



    public ArtistBean retrieveSearchArtistName(ArtworkBean a) {
        Artist artist = ArtistDAO.retrieveArtistFromId(a.getArtistId());

        ArtistBean artBean = new ArtistBean();
        artBean.setNome(artist.getNome());
        artBean.setCognome(artist.getCognome());
        return artBean;
    }

    public List<ArtworkBean> retrieveSearchArtWorkByName(ArtworkBean artSearch) throws ArtworkNotFoundException {
        String input=artSearch.getTitolo();
        String category=artSearch.getCategoria();

        List<Artwork> artWorkList = ArtworkDAO.retrieveArtWorkByName(input, category);
        List<ArtworkBean> arrayArtWorkBean = new ArrayList<>();

        if (artWorkList.isEmpty()){
            throw new ArtworkNotFoundException("Nessuna ArtWork trovata");
        }
        for (Artwork a : artWorkList) {
            ArtworkBean artWB = new ArtworkBean();
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

    public void finishPayment(ArtworkBean artBean, BuyerBean buy) throws FavouritesManagementProblemException, BuyArtworkManagementProblemException {
        int idOpera= artBean.getIdOpera();
        int idBuyer= buy.getIdBuyer();

        try {
            BuyerDAO.addArtWorkComprata(idOpera,idBuyer);
            ArtworkDAO.switchFlagVendibile(idOpera);
            BuyerDAO.removeArtWorkFromFavourites(idOpera,idBuyer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
