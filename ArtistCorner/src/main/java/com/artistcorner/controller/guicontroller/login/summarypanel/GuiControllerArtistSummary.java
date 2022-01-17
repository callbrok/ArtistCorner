package com.artistcorner.controller.guicontroller.login.summarypanel;

import com.artistcorner.controller.applicationcontroller.ViewArtistSummary;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerArtistSummary {
    public AnchorPane anchorParent;
    public ImageView imageGifButton;
    public Pane paneIdeaButton;
    public Label labelAlgoritmo;
    public Label labelLogOut;
    public Label labelUsernameDisplay;
    public SVGPath svgProfile;
    public ListView listLastProp;
    public TilePane tilePaneLastArt;
    private double x=0, y=0;
    private Stage stage;

    ViewArtistSummary vps = new ViewArtistSummary();
    ArtistBean art;

    public void initialize() throws SQLException {
        makeDraggable();
        makeGifPaneClickable();
        makeLogOut();

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }

    public void getArtist(ArtistBean loggedArtist) throws SQLException {
        art = loggedArtist;      // Prendo le informazioni riguardanti l'artista che ha effettuato il login.
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());

        initListLastProp(loggedArtist);
        initTileLastArt(loggedArtist);
    }


    public void initListLastProp(ArtistBean art){
        ArrayList<ProposalBean> arrayOfProposalsBean = vps.retrieveArtGalleryProposals(art);
        ArrayList<ArtGalleryBean> arrayOfArtGalleryOfProposal = new ArrayList<ArtGalleryBean>();

        for (ProposalBean n : arrayOfProposalsBean) {
            ArtGalleryBean artG = vps.retrieveArtGallery(n.getGalleria());   // Fai un retrieve della galleria associata alla proposta.
            listLastProp.getItems().add(artG.getNome());  // Popola la listView.
        }

    }


    public void initTileLastArt(ArtistBean art) throws SQLException {
        ArrayList<Blob> listOfArtWorksImage = vps.retrieveAllArtWorksImage(art);;  // Prendi tutte le opere caricate dall'artista.

        tilePaneLastArt.setHgap(10);    // Setta i bordi orizzontali tra un tile e l'altro.
        tilePaneLastArt.setVgap(5);    // Setta i bordi verticali tra un tile e l'altro.

        for (Blob b : listOfArtWorksImage){    // Scorre tutti i blob relativi all'artista.

            InputStream inputStream = b.getBinaryStream();

            Image image = new Image(inputStream, 200, 200, true, false);

            ImageView imageThumb = new ImageView();
            imageThumb.setImage(image);

            tilePaneLastArt.getChildren().add(imageThumb);   // Popola la tilePane.
        }

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

    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.setIconified(true);
    }

    public void makeGifPaneClickable(){

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToSceneProfiloAlgoritmo(event, art);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            labelAlgoritmo.setTextFill(Color.rgb(209, 62, 10));
        });

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            labelAlgoritmo.setTextFill(Color.rgb(45, 132, 101));
        });

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

    public void switchToProfiloArtista(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloArtista(actionEvent, art);
    }

    public void switchToProfiloVenduto(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloVenduto(actionEvent, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloOfferteMostre(actionEvent, art);
    }

    public void switchToUploadOpera(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneUploadOpera(actionEvent, art);
    }

}
