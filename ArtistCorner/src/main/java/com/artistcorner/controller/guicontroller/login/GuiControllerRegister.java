package com.artistcorner.controller.guicontroller.login;

import com.artistcorner.controller.applicationcontroller.login.Register;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import com.artistcorner.engclasses.exceptions.NotValidEmailException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class GuiControllerRegister {
    @FXML
    private TextField textFieldEmailArtist;
    @FXML
    private TextField textFieldEmailGal;
    @FXML
    private TextField textFieldEmailBuy;
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

    private Register signUp = new Register();

    public void registerArtist() {
        UserBean userReg = new UserBean();

        try {
            userReg.setUsername(textFieldUserArtist.getText());
            userReg.setPassword(textFieldPassArtist.getText());
            userReg.setEmail(textFieldEmailArtist.getText());
            userReg.setRole("artista");

            ArtistBean artistReg = new ArtistBean();

            artistReg.setNome(textFieldNomeArtist.getText());
            artistReg.setCognome(textFieldCognomeArtist.getText());

            signUp.registerArtist(userReg, artistReg);

        } catch (DuplicateUserException | NotValidEmailException e) {
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
        try {
        userReg.setUsername(textFieldUserBuyer.getText());
        userReg.setPassword(textFieldUserBuyer.getText());
        userReg.setEmail(textFieldEmailBuy.getText());
        userReg.setRole("acquirente");

        BuyerBean buyerReg = new BuyerBean();

        buyerReg.setNome(textFieldNomeBuyer.getText());
        buyerReg.setCognome(textFieldCognomeBuyer.getText());


            signUp.registerBuyer(userReg, buyerReg);
        } catch (DuplicateUserException | NotValidEmailException e) {
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
        try {
        userReg.setUsername(textFieldUserGallery.getText());
        userReg.setPassword(textFieldPassGallery.getText());
        userReg.setEmail(textFieldEmailGal.getText());
        userReg.setRole("galleria");

        String address = textFieldAddressGallery.getText()+", "+textFieldNumber.getText()+", "+textFieldCap.getText()+" "+textFieldCity.getText();

        ArtGalleryBean artGalleryReg = new ArtGalleryBean();

        artGalleryReg.setNome(textFieldNomeGallery.getText());
        artGalleryReg.setDescrizione(textFieldDescriptionGallery.getText());
        artGalleryReg.setIndirizzo(address);


        signUp.registerGallery(userReg, artGalleryReg);
        } catch (DuplicateUserException | NotValidEmailException e) {
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
