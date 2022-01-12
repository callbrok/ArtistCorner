package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.User;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.LoginDAO;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.Artist;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Login {


    public void credentialLogin(User noLoggedUser, ActionEvent event) throws IOException, UserNotFoundException {
        SceneController sc = new SceneController();

        User loggedUser = LoginDAO.retrieveUser(noLoggedUser);

        // Eccezione: Inserito utente non valido.
        if(loggedUser == null){
            throw new UserNotFoundException("Inserito utente non valido");
        }

        switch (loggedUser.getRole()){
            // Controlla il ruolo dell'utente, e apre l'interfaccia dedicata.
            case "artista":
                Artist loggedArtist = ArtistDAO.retrieveArtist(loggedUser);
                sc.switchToSceneMainArtista(event, loggedArtist);
                break;

            case "galleria":

                break;

            case "acquirente":

                break;
        }





    }



}
