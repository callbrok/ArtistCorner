package com.artistcorner.controller.guicontroller.mobile.login;

import com.artistcorner.controller.applicationcontroller.SignUp;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GuiControllerMobileSignUp {
    @FXML
    private AnchorPane anchorErroSignMobile;
    @FXML
    private Label labelErroSignMobile;
    @FXML
    private TextField textFieldUserArtistMob;
    @FXML
    private TextField textFieldPassArtistMob;
    @FXML
    private TextField textFieldNomeArtistMob;
    @FXML
    private TextField textFieldCognomeArtistMob;
    @FXML
    private TextField textFieldUserGalleryMob;
    @FXML
    private TextField textFieldPassGalleryMob;
    @FXML
    private TextField textFieldAddressGalleryMob;
    @FXML
    private TextField textFieldNomeGalleryMob;
    @FXML
    private TextField textFieldDescriptionGalleryMob;
    @FXML
    private TextField textFieldNumberMob;
    @FXML
    private TextField textFieldCapMob;
    @FXML
    private TextField textFieldCityMob;
    @FXML
    private TextField textFieldUserBuyerMob;
    @FXML
    private TextField textFieldPassBuyerMob;
    @FXML
    private TextField textFieldCognomeBuyerMob;
    @FXML
    private TextField textFieldNomeBuyerMob;

    private SignUp signUpM = new SignUp();

    public void registerArtistMob() {
        UserBean userReg = new UserBean(textFieldUserArtistMob.getText(), textFieldPassArtistMob.getText(), "artista");
        ArtistBean artistReg = new ArtistBean(textFieldNomeArtistMob.getText(), textFieldCognomeArtistMob.getText());

        try {
            signUpM.registerArtist(userReg, artistReg);
        } catch (DuplicateUserException e) {
            labelErroSignMobile.setText(e.getMessage());
            anchorErroSignMobile.setVisible(true);
        }

        textFieldUserArtistMob.clear();
        textFieldPassArtistMob.clear();
        textFieldNomeArtistMob.clear();
        textFieldCognomeArtistMob.clear();
    }
    public void registerBuyerMob(){
        UserBean userReg = new UserBean(textFieldUserBuyerMob.getText(), textFieldPassBuyerMob.getText(), "acquirente");
        BuyerBean buyerReg = new BuyerBean(textFieldNomeBuyerMob.getText(),textFieldCognomeBuyerMob.getText());

        try {
            signUpM.registerBuyer(userReg, buyerReg);
        } catch (DuplicateUserException e) {
            labelErroSignMobile.setText(e.getMessage());
            anchorErroSignMobile.setVisible(true);
        }
        textFieldUserBuyerMob.clear();
        textFieldPassBuyerMob.clear();
        textFieldNomeBuyerMob.clear();
        textFieldCognomeBuyerMob.clear();

    }
    public void registerGalleryMob(){
        UserBean userReg = new UserBean(textFieldUserGalleryMob.getText(), textFieldPassGalleryMob.getText(), "galleria");
        String address = textFieldAddressGalleryMob.getText()+", "+textFieldNumberMob.getText()+", "+textFieldCapMob.getText()+" "+textFieldCityMob.getText();
        ArtGalleryBean artGalleryReg = new ArtGalleryBean(textFieldNomeGalleryMob.getText(),textFieldDescriptionGalleryMob.getText(),address,textFieldUserGalleryMob.getText());
        try {
            signUpM.registerGallery(userReg, artGalleryReg);
        } catch (DuplicateUserException e) {
            labelErroSignMobile.setText(e.getMessage());
            anchorErroSignMobile.setVisible(true);
        }
        textFieldUserGalleryMob.clear();
        textFieldPassGalleryMob.clear();
        textFieldNomeGalleryMob.clear();
        textFieldAddressGalleryMob.clear();
        textFieldDescriptionGalleryMob.clear();
        textFieldNumberMob.clear();
        textFieldCityMob.clear();
        textFieldCapMob.clear();
    }
    public void initialize(){
        labelErroSignMobile.setMaxWidth(274);
        labelErroSignMobile.setWrapText(true);
        anchorErroSignMobile.setVisible(false);
    }

    public void returnToLogin(ActionEvent event) throws IOException {
        SceneControllerMobile scngp = new SceneControllerMobile();
        scngp.switchToLogin(event);
    }
}
