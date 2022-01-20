package com.artistcorner.controller.guicontroller.mobile.login;

import com.artistcorner.controller.applicationcontroller.SignUp;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class GuiControllerMobileSignUp {
    @FXML
    private TextField textFieldUserArtistMob;
    @FXML
    private TextField textFieldPassArtistMob;
    @FXML
    private TextField textFieldNomeArtistMob;
    @FXML
    private TextField textFieldCognomeArtistMob;

    private SignUp signUpM = new SignUp();

    public void registerArtistMob() {
        UserBean userReg = new UserBean(textFieldUserArtistMob.getText(), textFieldPassArtistMob.getText(), "artista");
        ArtistBean artistReg = new ArtistBean(textFieldNomeArtistMob.getText(), textFieldCognomeArtistMob.getText());

        signUpM.registerArtist(userReg, artistReg);

        textFieldUserArtistMob.clear();
        textFieldPassArtistMob.clear();
        textFieldNomeArtistMob.clear();
        textFieldCognomeArtistMob.clear();
    }


    public void returnToLogin(ActionEvent event) throws IOException {
        SceneControllerMobile scngp = new SceneControllerMobile();
        scngp.switchToLogin(event);
    }
}
