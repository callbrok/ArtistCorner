package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.*;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSearchArtWorkGallery {

    public String manageButtonClick(String buttonProposta, ArtGalleryBean artGalBean, ArtistBean artistBean) throws SQLException {
        int idGallery=artGalBean.getGalleria();
        int idArtista=artistBean.getIdArtista();
        int flag =0;
        String  remProposta = "Ritira Proposta";
        switch (buttonProposta){
            case "Ritira Proposta":{    // se il bottone ha come label 'ritira proposta'
                try {
                    ProposalDAO.removeProposta(idGallery,idArtista); // rimuovo la proposta
                } catch (ProposalNotFoundException e) {
                    e.printStackTrace();
                }
                return "Invia Proposta";    // imposto la label del bottone su 'invia proposta'


            }
            case "Invia Proposta":{   // se il bottone ha come label 'invia proposta'
                ProposalDAO.addProposta(idGallery,idArtista,flag);   // invio la proposta con flag accettazione impostato a 0
                return remProposta; // imposto la label del bottone su 'rimuovi proposta'


            }
            default:
        }
        return remProposta;
    }


    public ArtistBean retrieveGallerySearchArtistName(ArtWorkBean a) {
        Artist artist = ArtistDAO.retrieveArtistFromId(a.getArtistId());   // faccio il retrieve dell'artista associato all'opera selezionata
        ArtistBean artBean = new ArtistBean();

        artBean.setIdArtista(artist.getIdArtista());
        artBean.setNome(artist.getNome());
        artBean.setCognome(artist.getCognome());

        return artBean;
    }

    public List<ArtWorkBean> retrieveGallerySearchArtWorkByName(ArtWorkBean artToSearch) throws ArtWorkNotFoundException {
        String category=artToSearch.getCategoria();
        String input=artToSearch.getTitolo();
        List<ArtWork> artWorkList = ArtWorkDAO.retrieveArtWorkByName(input, category);
        List<ArtWorkBean> arrayArtWorkBean = new ArrayList<>();

        if(artWorkList.isEmpty()){throw new ArtWorkNotFoundException("nessuna opera trovata");
        }

        for (ArtWork a : artWorkList) {
            ArtWorkBean artWorkBean = new ArtWorkBean();
            artWorkBean.setIdOpera(a.getIdOpera());
            artWorkBean.setTitolo(a.getTitolo());
            artWorkBean.setPrezzo(a.getPrezzo());
            artWorkBean.setFlagVendibile(a.getFlagVenduto());
            artWorkBean.setArtistId(a.getArtistaId());
            artWorkBean.setCategoria(a.getCategoria());
            artWorkBean.setImmagine(a.getImmagine());
            arrayArtWorkBean.add(artWorkBean);
        }
        return arrayArtWorkBean;
    }
    public List<ArtistBean> retrieveGallerySearchArtistId(ArtGalleryBean gallery)  {
        ArtGallery gal = new ArtGallery(gallery.getGalleria(),gallery.getNome(),gallery.getDescrizione(),gallery.getIndirizzo());
        return  ProposalDAO.retrieveArtistIdOfferta(gal.getGalleria()); // prendo gli'id degli artisti ai quali è stata inviata una proposta
    }

}


