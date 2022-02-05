package com.artistcorner.controller.guicontroller.login;

import com.artistcorner.controller.applicationcontroller.SignUp;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GuiControllerSignUp {
    @FXML
    private AnchorPane anchorDuplicateUser;
    @FXML
    private Label labelExcepDuplicate;
    @FXML
    private TextField textFieldUserArtist;
    @FXML
    private TextField textFieldPassArtist;
    @FXML
    private TextField textFieldCognomeArtist;
    @FXML
    private TextField textFieldNomeArtist;
    @FXML
    private TextField textFieldUserBuyer;
    @FXML
    private TextField textFieldPassBuyer;
    @FXML
    private TextField textFieldCognomeBuyer;
    @FXML
    private TextField textFieldNomeBuyer;
    @FXML
    private TextField textFieldUserGallery;
    @FXML
    private TextField textFieldPassGallery;
    @FXML
    private TextField textFieldAddressGallery;
    @FXML
    private TextField textFieldNomeGallery;
    @FXML
    private TextField textFieldDescriptionGallery;
    @FXML
    private TextField textFieldNumber;
    @FXML
    private TextField textFieldCap;
    @FXML
    private TextField textFieldCity;

    private SignUp signUp = new SignUp();

    public void registerArtist() {
        UserBean userReg = new UserBean();

        userReg.setUsername(textFieldUserArtist.getText());
        userReg.setPassword(textFieldPassArtist.getText());
        userReg.setRole("artista");

        ArtistBean artistReg = new ArtistBean();

        artistReg.setNome(textFieldNomeArtist.getText());
        artistReg.setCognome(textFieldCognomeArtist.getText());

        try {
            signUp.registerArtist(userReg, artistReg);
        } catch (DuplicateUserException e) {
            labelExcepDuplicate.setText(e.getMessage());
            anchorDuplicateUser.setVisible(true);
        }

        textFieldUserArtist.clear();
        textFieldPassArtist.clear();
        textFieldNomeArtist.clear();
        textFieldCognomeArtist.clear();
    }
    public void registerBuyer(){
        UserBean userReg = new UserBean();

        userReg.setUsername(textFieldUserBuyer.getText());
        userReg.setPassword(textFieldUserBuyer.getText());
        userReg.setRole("acquirente");

        BuyerBean buyerReg = new BuyerBean();

        buyerReg.setNome(textFieldNomeBuyer.getText());
        buyerReg.setCognome(textFieldCognomeBuyer.getText());

        try {
            signUp.registerBuyer(userReg, buyerReg);
        } catch (DuplicateUserException e) {
            labelExcepDuplicate.setText(e.getMessage());
            anchorDuplicateUser.setVisible(true);
        }
        textFieldUserBuyer.clear();
        textFieldPassBuyer.clear();
        textFieldNomeBuyer.clear();
        textFieldCognomeBuyer.clear();

    }
    public void registerGallery(){
        UserBean userReg = new UserBean();

        userReg.setUsername(textFieldUserGallery.getText());
        userReg.setPassword(textFieldPassGallery.getText());
        userReg.setRole("galleria");

        String address = textFieldAddressGallery.getText()+", "+textFieldNumber.getText()+", "+textFieldCap.getText()+" "+textFieldCity.getText();

        ArtGalleryBean artGalleryReg = new ArtGalleryBean();

        artGalleryReg.setNome(textFieldNomeGallery.getText());
        artGalleryReg.setDescrizione(textFieldDescriptionGallery.getText());
        artGalleryReg.setIndirizzo(address);


        try {
            signUp.registerGallery(userReg, artGalleryReg);
        } catch (DuplicateUserException e) {
            labelExcepDuplicate.setText(e.getMessage());
            anchorDuplicateUser.setVisible(true);
        }
        textFieldUserGallery.clear();
        textFieldPassGallery.clear();
        textFieldNomeGallery.clear();
        textFieldAddressGallery.clear();
        textFieldDescriptionGallery.clear();
        textFieldNumber.clear();
        textFieldCity.clear();
        textFieldCap.clear();
    }


    public void initialize(){
       labelExcepDuplicate.setMaxWidth(299);
       labelExcepDuplicate.setWrapText(true);
       anchorDuplicateUser.setVisible(false);
    }

}
