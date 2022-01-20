package com.artistcorner.controller.guicontroller.login;

import com.artistcorner.controller.applicationcontroller.SignUp;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GuiControllerSignUp {
    @FXML
    private TextField textFieldUserArtist;
    @FXML
    private TextField textFieldPassArtist;
    @FXML
    private TextField textFieldCognomeArtist;
    @FXML
    private TextField textFieldNomeArtist;


    private SignUp signUp = new SignUp();

    public void registerArtist() {
        UserBean userReg = new UserBean(textFieldUserArtist.getText(), textFieldPassArtist.getText(), "artista");
        ArtistBean artistReg = new ArtistBean(textFieldNomeArtist.getText(), textFieldCognomeArtist.getText());

        signUp.registerArtist(userReg, artistReg);

        textFieldUserArtist.clear();
        textFieldPassArtist.clear();
        textFieldNomeArtist.clear();
        textFieldCognomeArtist.clear();
    }

}
