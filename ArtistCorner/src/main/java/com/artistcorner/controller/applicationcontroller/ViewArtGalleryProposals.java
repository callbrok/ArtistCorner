package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.dao.ProposalDAO;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewArtGalleryProposals {

    /**
     * Genera il codice html per la visualizzazione dell'indirizzo passato.
     */
    public String makeMapHtml(String luogo, int dimWebPage){

        String htmlMap="";
        StringBuilder sb = new StringBuilder(htmlMap);

        sb.append("<div style=\"margin-left:-8px; margin-top:-8px;\"><iframe width=\"" + dimWebPage + "px\" height=\"100%\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q=")
                .append(luogo.replace(" ", "%20"))  // Sostituisce gli spazi con "%20" per rispettare la semantica dell'url
                .append("&t=&z=13&ie=UTF8&iwloc=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe></div>");

        htmlMap = sb.toString();

        return htmlMap;
    }

    public List<ProposalBean> retrieveArtGalleryProposals(ArtistBean artistBean) throws ProposalNotFoundException {
        Artist art = new Artist(artistBean.getIdArtista(), artistBean.getNome(), artistBean.getCognome());
        ArrayList<ProposalBean>  arrayOfProposalBeans = new ArrayList<>();
        ProposalBean propBean = new ProposalBean();

        List<Proposal> arrayOfProposals = ProposalDAO.retrieveArtGalleryProposals(art.getIdArtista(), "");

        if(arrayOfProposals.isEmpty()){
            throw new ProposalNotFoundException("Nessuna proposta disponibile.");
        }

        for (Proposal n : arrayOfProposals) {
            propBean.setIdOfferta(n.getIdOfferta());
            propBean.setArtista(n.getArtista());
            propBean.setGalleria(n.getGalleria());
            propBean.setFlagAccettazione(n.getFlagAccettazione());

            arrayOfProposalBeans.add(propBean);
        }


        return arrayOfProposalBeans;
    }

    public ArtGalleryBean retrieveArtGallery(ProposalBean prop){
        ArtGallery artG = GalleryDAO.retrieveArtGallery(prop.getGalleria());   // Fai un retrieve della galleria associata alla proposta.
        ArtGalleryBean artGallBean = new ArtGalleryBean();

        artGallBean.setGalleria(artG.getGalleria());
        artGallBean.setNome(artG.getNome());
        artGallBean.setDescrizione(artG.getDescrizione());
        artGallBean.setIndirizzo(artG.getIndirizzo());
        artGallBean.setUsername( artG.getUsername());

        return artGallBean;

    }

    public void acceptProposal(ProposalBean proToAccept) throws SQLException {
        ProposalDAO.updateProposal(proToAccept.getIdOfferta(), proToAccept.getFlagAccettazione());
    }


    public void rejectProposal(ProposalBean proToReject) throws SQLException {
        ProposalDAO.updateProposal(proToReject.getIdOfferta(), proToReject.getFlagAccettazione());

    }

}
