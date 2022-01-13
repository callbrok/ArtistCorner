package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.util.ArrayList;

public class ViewArtGalleryProposals {

    /**
     * Genera il codice html per la visualizzazione dell'indirizzo passato.
     */
    public String makeMapHtml(String luogo){

        String htmlMap="";
        StringBuilder sb = new StringBuilder(htmlMap);

        sb.append("<div style=\"margin-left:-8px; margin-top:-8px;\"><iframe width=\"422px\" height=\"100%\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q=")
                .append(luogo.replaceAll(" ", "%20"))  // Sostituisce gli spazi con "%20" per rispettare la semantica dell'url
                .append("&t=&z=13&ie=UTF8&iwloc=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe></div>");

        htmlMap = sb.toString();

        return htmlMap;
    }

    public ArrayList<Proposal> retrieveArtGalleryProposals(Artist art) throws ProposalNotFoundException {

        ArrayList<Proposal> arrayOfProposals = ArtistDAO.retrieveArtGalleryProposals(art.getIdArtista());

        if(arrayOfProposals == null){
            throw new ProposalNotFoundException("Nessuna proposta disponibile.");
        }

        return arrayOfProposals;
    }

}
