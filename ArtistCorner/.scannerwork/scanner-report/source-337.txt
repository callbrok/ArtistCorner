package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;

import java.util.ArrayList;

public class ViewProfileGallery {

    public String retrieveArtistName(int idArtista) {
        Artist name =BuyerDAO.retrieveArtistName(idArtista);
        String artistname = name.getNome()+" "+name.getCognome();
        return artistname;
    }

    public ArrayList<Proposal> retrieveProposal(ArtGalleryBean gallery,int flag){
        ArrayList<Proposal> proposal = GalleryDAO.retrieveProposal(gallery,flag);
        return proposal;
    }
}
