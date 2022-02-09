package com.artistcorner.controller.guicontroller.mobile.login.summaries;

import com.artistcorner.controller.applicationcontroller.login.summaries.ViewGallerySummary;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileGallerySummary {

    @FXML
    private AnchorPane anchorMainGallery;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private ListView<String> listViewOfferte;
    private double x=0;
    private double y=0;
    @FXML
    private Stage stageMobileGal;

    private ArtGalleryBean gal;


    public void initialize(){
        makeDraggable();
    }

    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(event);
    }

    public void getGallery(ArtGalleryBean loggedGallery){
        gal = loggedGallery;      // Prendo le informazioni riguardanti l'acquirente che ha effettuato il login.
        labelUsernameDisplay.setText(gal.getNome());
        listViewOfferte.setStyle("-fx-font-size: 10px");
        inizializeOfferteInviate(listViewOfferte,gal);
    }

    public void minimizeWindow() {
        stageMobileGal = (Stage) anchorMainGallery.getScene().getWindow();
        stageMobileGal.setIconified(true);
    }

    public void exitWindow() {
        stageMobileGal = (Stage) anchorMainGallery.getScene().getWindow();
        stageMobileGal.close();
    }

    private void makeDraggable(){

        anchorMainGallery.setOnMouseDragged((eventD -> {
            stageMobileGal = (Stage) ((Node)eventD.getSource()).getScene().getWindow();
            stageMobileGal.setX(eventD.getScreenX() - x);
            stageMobileGal.setY(eventD.getScreenY() - y);
        }));

        anchorMainGallery.setOnMousePressed((eventP -> {
            x=eventP.getSceneX();
            y= eventP.getSceneY();
        }));

    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
    public void switchToSentArtGalleryProposal(ActionEvent actionEvent) throws IOException, SQLException{
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneSentArtGalleryProposal(actionEvent,gal);
    }
    public void switchToProfiloGallery(ActionEvent actionEvent) throws SQLException, IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloGallery(actionEvent,gal);
    }
    private void inizializeOfferteInviate(ListView<String> listViewOfferte, ArtGalleryBean gal){
        ViewGallerySummary vgs = new ViewGallerySummary();
        List<ProposalBean> arrayOfProposal = vgs.retrieveGalleryProposal(gal, 0);
        ArrayList<String> arrayFinal = new ArrayList<>();
        for (ProposalBean n : arrayOfProposal) {
            ArtistBean artist = vgs.retrieveArtistNameGallerySum(n);
            String artistName = artist.getNome() + " " + artist.getCognome();
            listViewOfferte.getItems().add("Offerta inviata per Artista :  " + artistName);  // Popola la listView.
            arrayFinal.add(artistName);
        }
    }

}
