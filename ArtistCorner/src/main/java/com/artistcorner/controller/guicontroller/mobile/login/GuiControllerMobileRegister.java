package com.artistcorner.controller.guicontroller.mobile.login;

import com.artistcorner.controller.applicationcontroller.login.Register;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import com.artistcorner.engclasses.exceptions.NotValidEmailException;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GuiControllerMobileRegister {
    @FXML
    private TextField textFieldEmailArtistMob;
    @FXML
    private TextField textFieldEmailGalMob;
    @FXML
    private TextField textFieldEmailBuyMob;
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

    private Register signUpM = new Register();

    public void registerArtistMob() {
        UserBean userReg = new UserBean();

        try {
            userReg.setUsername(textFieldUserArtistMob.getText());
            userReg.setPassword(textFieldPassArtistMob.getText());
            userReg.setEmail(textFieldEmailArtistMob.getText());
            userReg.setRole("artista");


            ArtistBean artistReg = new ArtistBean();

            artistReg.setNome(textFieldNomeArtistMob.getText());
            artistReg.setCognome(textFieldCognomeArtistMob.getText());

            signUpM.registerArtist(userReg, artistReg);

        } catch (DuplicateUserException | NotValidEmailException e) {
            labelErroSignMobile.setText(e.getMessage());
            anchorErroSignMobile.setVisible(true);
        }

        textFieldUserArtistMob.clear();
        textFieldPassArtistMob.clear();
        textFieldNomeArtistMob.clear();
        textFieldCognomeArtistMob.clear();
    }
    public void registerBuyerMob(){
        UserBean userReg = new UserBean();
        try {
        userReg.setUsername(textFieldUserBuyerMob.getText());
        userReg.setPassword(textFieldPassBuyerMob.getText());
        userReg.setEmail(textFieldEmailBuyMob.getText());
        userReg.setRole("acquirente");

        BuyerBean buyerReg = new BuyerBean();

        buyerReg.setNome(textFieldNomeBuyerMob.getText());
        buyerReg.setCognome(textFieldCognomeBuyerMob.getText());


            signUpM.registerBuyer(userReg, buyerReg);
        } catch (DuplicateUserException | NotValidEmailException e) {
            labelErroSignMobile.setText(e.getMessage());
            anchorErroSignMobile.setVisible(true);
        }
        textFieldUserBuyerMob.clear();
        textFieldPassBuyerMob.clear();
        textFieldNomeBuyerMob.clear();
        textFieldCognomeBuyerMob.clear();

    }
    public void registerGalleryMob(){
        UserBean userReg = new UserBean();
        try {
        userReg.setUsername(textFieldUserGalleryMob.getText());
        userReg.setPassword(textFieldPassGalleryMob.getText());
        userReg.setEmail(textFieldEmailGalMob.getText());
        userReg.setRole("galleria");

        String address = textFieldAddressGalleryMob.getText()+", "+textFieldNumberMob.getText()+", "+textFieldCapMob.getText()+" "+textFieldCityMob.getText();

        ArtGalleryBean artGalleryReg = new ArtGalleryBean();

        artGalleryReg.setNome(textFieldNomeGalleryMob.getText());
        artGalleryReg.setDescrizione(textFieldDescriptionGalleryMob.getText());
        artGalleryReg.setIndirizzo(address);


            signUpM.registerGallery(userReg, artGalleryReg);
        } catch (DuplicateUserException | NotValidEmailException e) {
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
        labelErroSignMobile.setAlignment(Pos.CENTER);
        anchorErroSignMobile.setVisible(false);
    }

    public void returnToLogin(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToLogin(event);
    }
}
