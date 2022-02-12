package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.dao.ArtworkDAO;
import com.artistcorner.engclasses.exceptions.DuplicateArtworkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.EmptyPathException;
import com.artistcorner.engclasses.exceptions.ImageTooLargeException;
import com.artistcorner.model.Artwork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class UploadArtwork {

    public void uploadImage(ArtworkBean upArt, String filePath) throws DuplicateArtworkException, EmptyPathException, EmptyFieldException, ImageTooLargeException {

        if(upArt.getTitolo().equals("")) {
            throw new EmptyFieldException("Lasciati campi per l'upload vuoti.");
        }

        if(filePath.equals("")){
            throw new EmptyPathException("Nessun file selezionato.");
        }

        try {
            long imageBytesSize = Files.size(Path.of(filePath));
            if(imageBytesSize > 16777216){throw new ImageTooLargeException("Caricata immagine troppo grande (MAX. 16MB).");}
        } catch (IOException e) {
            System.out.println("Impossibile controllare dimensione dell'immagine.");
        }


        Artwork artToUpload = new Artwork(upArt.getTitolo(), upArt.getPrezzo(), upArt.getFlagVendibile(), upArt.getArtistId(), upArt.getCategoria());

        ArtworkDAO.saveArtWork(artToUpload, filePath);
    }



}
