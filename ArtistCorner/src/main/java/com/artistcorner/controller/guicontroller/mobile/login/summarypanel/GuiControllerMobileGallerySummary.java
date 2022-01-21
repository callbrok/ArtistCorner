package com.artistcorner.controller.guicontroller.mobile.login.summarypanel;

import com.artistcorner.controller.applicationcontroller.ViewBuyerSummary;
import com.artistcorner.controller.applicationcontroller.ViewGallerySummary;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileGallerySummary {
    @FXML
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public ListView<String> listViewOfferte;
    public Button button1;
    public Button button2;
    public Label labelComprate;
    private double x=0, y=0;
    @FXML
    private Pane paneExceptionLoad;
    private Stage stage;

   ArtGalleryBean gal;


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
        ViewGallerySummary bs = new ViewGallerySummary();
        listViewOfferte.setStyle("-fx-font-size: 10px");
        inizializeOfferteInviate(listViewOfferte,gal);
    }

    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMain.setOnMousePressed(((event) -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMain.setOnMouseDragged(((event) -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException{
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloGallery(actionEvent,gal);
    }
    private void inizializeOfferteInviate(ListView<String> listViewOfferte, ArtGalleryBean gal){
        ViewGallerySummary vgs = new ViewGallerySummary();


        List<ProposalBean> arrayOfProposal = vgs.retrieveGalleryProposal(gal, 0);
        ArrayList<String> arrayFinal = new ArrayList<>();
        for (ProposalBean n : arrayOfProposal) {
            ArtistBean artist = vgs.retrieveArtistNameGallerySum(n.getArtista());
            String artistName = artist.getNome() + " " + artist.getCognome();
            listViewOfferte.getItems().add("Offerta inviata per Artista :  " + artistName);  // Popola la listView.

            arrayFinal.add(artistName);
        }
    }

}
