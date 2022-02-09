package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.dao.ArtworkDAO;
import com.artistcorner.engclasses.exceptions.DuplicateArtworkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.EmptyPathException;
import com.artistcorner.model.Artwork;


public class UploadArtwork {

    public void uploadImage(ArtworkBean upArt, String filePath) throws DuplicateArtworkException, EmptyPathException, EmptyFieldException {
        // Dato che uploadImage non cattura l'eccezzione EmptyFieldExceptio
        // lo metto in throws.


        if(upArt.getTitolo().equals("")) {
            throw new EmptyFieldException("Lasciati campi per l'upload vuoti.");
        }

        if(filePath.equals("")){
            throw new EmptyPathException("Nessun file selezionato.");
        }

        Artwork artToUpload = new Artwork(upArt.getTitolo(), upArt.getPrezzo(), upArt.getFlagVendibile(), upArt.getArtistId(), upArt.getCategoria());

        ArtworkDAO.saveArtWork(artToUpload, filePath);
    }



}
