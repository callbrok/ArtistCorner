package com.artistcorner.controller.applicationcontroller.login;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.dao.UserDAO;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import com.artistcorner.model.User;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Login {
    private int nAttempts = 0;

    /**
     * Controlla le credenziali ed effettua il login.
     */
    public void credentialLogin(UserBean noLoggedUserBean, ActionEvent event, String flagInterface) throws IOException, UserNotFoundException {
        SceneController sc = new SceneController();
        SceneControllerMobile scm = new SceneControllerMobile();

        User noLoggedUser = new User(noLoggedUserBean.getUsername(), noLoggedUserBean.getPassword(), noLoggedUserBean.getRole());

        User loggedUser = UserDAO.retrieveUser(noLoggedUser);

        // Eccezione: Inserito utente non valido.
        if(loggedUser == null){
            throw new UserNotFoundException("Inserito utente non valido");
        }

        switch (loggedUser.getRole()){
            // Controlla il ruolo dell'utente, e apre l'interfaccia dedicata.
            case "artista":
                Artist loggedArtist = ArtistDAO.retrieveArtist(loggedUser);
                ArtistBean loggedArtistBean = new ArtistBean();

                loggedArtistBean.setIdArtista(loggedArtist.getIdArtista());
                loggedArtistBean.setNome(loggedArtist.getNome());
                loggedArtistBean.setCognome(loggedArtist.getCognome());

                if(flagInterface.equals("D")){sc.switchToSceneMainArtista(event, loggedArtistBean);}   // Modalità Desktop.
                if(flagInterface.equals("M")){scm.switchToSceneMainArtista(event, loggedArtistBean);}  // Modalità Mobile.

                break;

            case "galleria":
                ArtGallery loggedArtGallery = GalleryDAO.retrieveGallery(loggedUser);
                ArtGalleryBean loggedGalleryBean = new ArtGalleryBean();

                loggedGalleryBean.setGalleria(loggedArtGallery.getGalleria());
                loggedGalleryBean.setNome(loggedArtGallery.getNome());
                loggedGalleryBean.setDescrizione(loggedArtGallery.getDescrizione());
                loggedGalleryBean.setIndirizzo(loggedArtGallery.getIndirizzo());

                if(flagInterface.equals("D")){sc.switchToSceneGallerySummary(event, loggedGalleryBean);}   // Modalità Desktop.
                if(flagInterface.equals("M")){scm.switchToSceneGallerySummary(event, loggedGalleryBean);}  // Modalità Mobile.

                break;

            case "acquirente":
                Buyer loggedBuyer = BuyerDAO.retrieveBuyer(loggedUser);
                BuyerBean loggedBuyerBean = new BuyerBean();

                loggedBuyerBean.setIdBuyer(loggedBuyer.getIdBuyer());
                loggedBuyerBean.setNome(loggedBuyer.getNome());
                loggedBuyerBean.setCognome(loggedBuyer.getCognome());

                if(flagInterface.equals("D")){sc.switchToSceneBuyerSummary(event, loggedBuyerBean);}   // Modalità Desktop.
                if(flagInterface.equals("M")){scm.switchToSceneBuyerSummary(event, loggedBuyerBean);}  // Modalità Mobile.

                break;

            case "analytics":
                sc.switchToAnalytics(event);
                break;
        }
    }


}
