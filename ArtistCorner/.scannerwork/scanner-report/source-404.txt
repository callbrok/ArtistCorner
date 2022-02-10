package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.dao.ArtworkDAO;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtworkManagementProblemException;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import com.artistcorner.engclasses.observer.ArtistConcreteObserver;
import com.artistcorner.engclasses.observer.BuyerConcreteSubject;
import com.artistcorner.model.Artwork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchArtWorkBuyer {

    public String manageButtonClickFavourites(String buttonFavourites, ArtworkBean artBean, BuyerBean buy ) throws FavouritesManagementProblemException {
        int idArtWork= artBean.getIdOpera();
        int idBuyer= buy.getIdBuyer();

        String addFavourites = "Aggiungi ai Preferiti";
        String remFavourites = "Rimuovi dai Preferiti";
        switch (buttonFavourites){
            case "Rimuovi dai Preferiti":{
                try {
                    ArtworkDAO.removeArtWorkFromFavourites(idArtWork, idBuyer);
                    return addFavourites;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
            case "Aggiungi ai Preferiti":{
                try {
                    ArtworkDAO.addArtWorkToFavourites(idArtWork,idBuyer);
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

    public List<ArtworkBean> retrieveSearchArtWorkId(BuyerBean buyer){
        Buyer buy = new Buyer(buyer.getIdBuyer(),buyer.getNome(),buyer.getCognome());
        return ArtworkDAO.retrieveArtWorkId(buy.getIdBuyer());
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

    public void finishPayment(ArtworkBean artBean, BuyerBean buy, ArtistBean artistBean) throws FavouritesManagementProblemException, BuyArtworkManagementProblemException {
        int idOpera= artBean.getIdOpera();
        int idBuyer= buy.getIdBuyer();

        try {
            ArtworkDAO.addArtWorkComprata(idOpera,idBuyer);
            ArtworkDAO.switchFlagVendibile(idOpera);
            ArtworkDAO.removeArtWorkFromFavourites(idOpera,idBuyer);
            ArtistConcreteObserver concreteObserver= new ArtistConcreteObserver("alessio.torroni00@gmail.com", buy.getNome()+" "+buy.getCognome(),artBean.getTitolo());
            BuyerConcreteSubject concreteSubject = new BuyerConcreteSubject();
            concreteSubject.add(concreteObserver);
            concreteSubject.checkOut();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
