package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import javafx.scene.control.Button;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchArtWorkGallery {

    public String manageButtonClick(Button buttonProposta, int idGallery, int idArtista) throws SQLException {
        int flag =0;
        String  remProposta = "Ritira Proposta";
        switch (buttonProposta.getText()){
            case "Ritira Proposta":{    // se il bottone ha come label 'ritira proposta'
                GalleryDAO.removeProposta(idGallery,idArtista); // rimuovo la proposta
                return "Invia Proposta";    // imposto la label del bottone su 'invia proposta'


            }
            case "Invia Proposta":{   // se il bottone ha come label 'invia proposta'
                GalleryDAO.addProposta(idGallery,idArtista,flag);   // invio la proposta con flag accettazione impostato a 0
                return remProposta; // imposto la label del bottone su 'rimuovi proposta'


            }
            default:
        }
        return remProposta;
    }

    public Blob retrieveGallerySearchArtWorkBlob(int n) {
       return BuyerDAO.retrieveImage(n);    // faccio il retrieve del blob dell'opera in questione
    }

    public ArtistBean retrieveGallerySearchArtistName(ArtWorkBean a) {
        Artist artist = BuyerDAO.retrieveArtist(a.getArtistId());   // faccio il retrieve dell'artista associato all'opera selezionata
        return new ArtistBean(artist.getIdArtista(),artist.getNome(),artist.getCognome());
    }

    public List<ArtWorkBean> retrieveGallerySearchArtWorkByName(String input,String category) throws ArtWorkNotFoundException {

        List<ArtWork> artWorkList = BuyerDAO.retrieveArtWorkByName(input, category);
        List<ArtWorkBean> arrayArtWorkBean = new ArrayList<>();
        if(artWorkList.isEmpty()){throw new ArtWorkNotFoundException("nessuna opera trovata");
        }
        for (ArtWork a : artWorkList) {
            arrayArtWorkBean.add(new ArtWorkBean(a.getIdOpera(),a.getTitolo(),a.getPrezzo(),a.getFlagVenduto(),a.getArtistaId(), a.getCategoria()));
        }
        return arrayArtWorkBean;
    }

    public List<Integer> retrieveGallerySearchArtistId(ArtGalleryBean gallery)  {
        ArtGallery gal = new ArtGallery(gallery.getGalleria(),gallery.getNome(),gallery.getDescrizione(),gallery.getIndirizzo(),gallery.getUsername());
        return  GalleryDAO.retrieveArtistId(gal.getGalleria()); // prendo gli'id degli artisti ai quali è stata inviata una proposta
    }

}


