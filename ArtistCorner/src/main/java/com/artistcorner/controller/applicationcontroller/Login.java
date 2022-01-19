package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.dao.LoginDAO;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class Login {


    public void credentialLogin(UserBean noLoggedUserBean, ActionEvent event, String flagInterface) throws IOException, UserNotFoundException, SQLException {
        SceneController sc = new SceneController();
        SceneControllerMobile scm = new SceneControllerMobile();

        UserBean loggedUserBean = LoginDAO.retrieveUser(noLoggedUserBean);

        // Eccezione: Inserito utente non valido.
        if(loggedUserBean == null){
            throw new UserNotFoundException("Inserito utente non valido");
        }

        switch (loggedUserBean.getRole()){
            // Controlla il ruolo dell'utente, e apre l'interfaccia dedicata.
            case "artista":
                Artist loggedArtist = ArtistDAO.retrieveArtist(loggedUserBean);
                ArtistBean loggedArtistBean = new ArtistBean(loggedArtist.getIdArtista(), loggedArtist.getNome(), loggedArtist.getCognome());

                if(flagInterface.equals("D")){sc.switchToSceneMainArtista(event, loggedArtistBean);}   // Modalità Desktop.
                if(flagInterface.equals("M")){scm.switchToSceneMainArtista(event, loggedArtistBean);}  // Modalità Mobile.

                break;

            case "galleria":
                ArtGalleryBean loggedGalleryBean = GalleryDAO.retrieveGallery(loggedUserBean);
                sc.switchToSceneGallerySummary(event, loggedGalleryBean);
                break;

            case "acquirente":
                Buyer loggedBuyer = BuyerDAO.retrieveBuyer(loggedUserBean);
                sc.switchToSceneBuyerSummary(event, loggedBuyer);
                break;

            case "analytics":
                sc.switchToAnalytics(event);
                break;
        }

    }



}
