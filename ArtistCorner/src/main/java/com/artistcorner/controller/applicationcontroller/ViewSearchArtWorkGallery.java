package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import javafx.scene.control.Button;


import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchArtWorkGallery {

    public String manageButtonClick(Button buttonPreferiti, int idGallery, int idArtista) throws SQLException {
        int flag =0;
        switch (buttonPreferiti.getText()){
            case "Ritira Proposta":{
                GalleryDAO.removeProposta(idGallery,idArtista);
                return "Invia Proposta";


            }
            case "Invia Proposta":{
                GalleryDAO.addProposta(idGallery,idArtista,flag);
                return "Ritira Proposta";


            }
            default:
        }
        return "Ritira Proposta";
    }

    public Blob retrieveGallerySearchArtWorkBlob(int n) {
        Blob immagine = BuyerDAO.retrieveImage(n);
        return immagine;
    }

    public ArtistBean retrieveGallerySearchArtistName(ArtWorkBean a) {
        Artist artist = BuyerDAO.retrieveArtist(a.getArtistId());
        return new ArtistBean(artist.getIdArtista(),artist.getNome(),artist.getCognome());
    }

    public List<ArtWorkBean> retrieveGallerySearchArtWorkByName(String input) throws ArtWorkNotFoundException {
        List<ArtWork> artWorkList = BuyerDAO.retrieveArtWorkByName(input);
        ArrayList<ArtWorkBean> arrayArtWorkBean = new ArrayList<>();
        if(artWorkList.isEmpty()){throw new ArtWorkNotFoundException("nessuna opera trovata");
        }
        for (ArtWork a : artWorkList) {
            arrayArtWorkBean.add(new ArtWorkBean(a.getIdOpera(),a.getTitolo(),a.getPrezzo(),a.getFlagVenduto(),a.getArtistaId()));
        }
        return arrayArtWorkBean;
    }

    public List<Integer> retrieveGallerySearchArtistId(ArtGalleryBean gallery)  {
            ArtGallery gal = new ArtGallery(gallery.getGalleria(),gallery.getNome(),gallery.getDescrizione(),gallery.getIndirizzo(),gallery.getUsername());
            List<Integer> artistId = GalleryDAO.retrieveArtistId(gal.getGalleria());
        return artistId;
    }

}


