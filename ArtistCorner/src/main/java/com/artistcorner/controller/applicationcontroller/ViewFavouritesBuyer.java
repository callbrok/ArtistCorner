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
import java.util.List;

public class ViewFavouritesBuyer {


    public String manageButtonClick(String buttonPreferiti, ArtworkBean art, BuyerBean buy ) throws FavouritesManagementProblemException {
        int idOpera= art.getIdOpera();
        int idBuyer= buy.getIdBuyer();

        String addPreferiti = "Aggiungi ai Preferiti";
        String remPreferiti = "Rimuovi dai Preferiti";
        switch (buttonPreferiti){
            case "Rimuovi dai Preferiti":{
                try {
                    ArtworkDAO.removeArtWorkFromFavourites(idOpera, idBuyer);
                    return addPreferiti;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
            case "Aggiungi ai Preferiti":{
                try {
                    ArtworkDAO.addArtWorkToFavourites(idOpera,idBuyer);
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

    public void finishPayment(ArtworkBean art, BuyerBean buy, ArtistBean artistBean) throws BuyArtworkManagementProblemException, FavouritesManagementProblemException {
                int idOpera = art.getIdOpera();
                int idBuyer = buy.getIdBuyer();

                try {
                    ArtworkDAO.addArtWorkComprata(idOpera,idBuyer);
                    ArtworkDAO.switchFlagVendibile(idOpera);
                    ArtworkDAO.removeArtWorkFromFavourites(idOpera,idBuyer);
                    ArtistConcreteObserver concreteObs= new ArtistConcreteObserver("alessio.torroni00@gmail.com", buy.getNome()+" "+buy.getCognome(),art.getTitolo());
                    BuyerConcreteSubject concreteSub = new BuyerConcreteSubject();
                    concreteSub.add(concreteObs);
                    concreteSub.checkOut();
                } catch (SQLException  e) {
                    e.printStackTrace();
                }
    }

    public List<ArtworkBean> retrieveArtWorkId(BuyerBean buyer) throws ArtworkNotFoundException {
        Buyer buy = new Buyer(buyer.getIdBuyer(),buyer.getNome(),buyer.getCognome());
        List<ArtworkBean> artWorkId = ArtworkDAO.retrieveArtWorkId(buy.getIdBuyer());
        if (artWorkId.isEmpty()){
            throw new ArtworkNotFoundException("Nessuna Preferito disponibile.");
        }
        return artWorkId;
    }

    public ArtworkBean retrieveArtWork(int integer){
        Artwork a = ArtworkDAO.retrieveArtWorks(integer, 1);
        ArtworkBean artWB = new ArtworkBean();

        artWB.setIdOpera(a.getIdOpera());
        artWB.setTitolo(a.getTitolo());
        artWB.setPrezzo(a.getPrezzo());
        artWB.setFlagVendibile(a.getFlagVenduto());
        artWB.setArtistId(a.getArtistaId());
        artWB.setCategoria(a.getCategoria());
        artWB.setImmagine(a.getImmagine());

        return artWB;
    }


    public ArtistBean retrieveArtistName(ArtworkBean a) {
        Artist artist = ArtistDAO.retrieveArtistFromId(a.getArtistId());
        ArtistBean artB = new ArtistBean();

        artB.setIdArtista(artist.getIdArtista());
        artB.setNome(artist.getNome());
        artB.setCognome(artist.getCognome());

        return artB;
    }
}
