package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;


public class UploadArtWork {

    public void uploadImage(ArtWorkBean upArt, int idArtista, String filePath) throws Exception, EmptyFieldException{
        // Dato che uploadImage non cattura l'eccezzione EmptyFieldExceptio
        // lo metto in throws.


        if(upArt.getTitolo().equals("")) {
            throw new EmptyFieldException("Lasciati campi per l'upload vuoti.");
        }


        ArtistDAO.saveArtWork(upArt, idArtista, filePath);

    }



}
