package com.artistcorner.controller.guicontroller.login.summarypanel;

import com.artistcorner.controller.applicationcontroller.ViewGallerySummary;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerGallerySummary {
    public AnchorPane anchorParent;
    public Pane paneSearch;
    public Pane paneFavourites;
    public Pane paneComprate;
    public ListView<String> listViewOfferte;
    @FXML
    Label labelUsernameDisplay;
    public Label labelLogOut;
    private double x=0, y=0;
    private Stage stage;
    public SVGPath svgProfile;
    public Button button1;
    public Button button2;
    public Button button3;
    @FXML
    private Pane paneExceptionLoad;
    public ArtGalleryBean gal;


    public void initialize(){
        makeDraggable();
        makeLogOut();
        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }

    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void getGallery(ArtGalleryBean loggedGallery){
        gal = loggedGallery;
        labelUsernameDisplay.setText(gal.getNome());
        ViewGallerySummary bs = new ViewGallerySummary();
        inizializeOfferteInviate(listViewOfferte,gal);
        paneExceptionLoad.setPrefSize(708,250);
    }


    private void makeDraggable(){
        anchorParent.setOnMousePressed(((event) -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParent.setOnMouseDragged(((event) -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void exitWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.setIconified(true);
    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloGallery(actionEvent,gal);
    }

    private void inizializeOfferteInviate(ListView<String> listViewOfferte, ArtGalleryBean gal) {
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
