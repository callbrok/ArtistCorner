package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.LoginDAO;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Login {


    public void credentialLogin(UserBean noLoggedUserBean, ActionEvent event) throws IOException, UserNotFoundException {
        SceneController sc = new SceneController();

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
                sc.switchToSceneMainArtista(event, loggedArtistBean);
                break;

            case "galleria":

                break;

            case "acquirente":
                Buyer loggedBuyer = BuyerDAO.retrieveBuyer(loggedUserBean);
                sc.switchToSceneBuyerSummary(event, loggedBuyer);
                break;
        }

    }



}
