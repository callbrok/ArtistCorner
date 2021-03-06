package com.artistcorner.controller.guicontroller.login.summaries;

import com.artistcorner.controller.applicationcontroller.login.summaries.ViewArtistSummary;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerArtistSummary {
    @FXML
    private AnchorPane anchorParentArtSumD;
    @FXML
    private ImageView imageGifButton;
    @FXML
    private Pane paneIdeaButton;
    @FXML
    private Label labelAlgoritmo;
    @FXML
    private Label labelLogOut;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private SVGPath svgProfile;
    @FXML
    private ListView listLastProp;
    @FXML
    private TilePane tilePaneLastArt;

    private double x=0;
    private double y=0;
    private Stage stage;

    private ViewArtistSummary vps = new ViewArtistSummary();
    private ArtistBean art;

    public void initialize() throws SQLException {
        makeDraggable();
        makeGifPaneClickable();
        makeLogOut();

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }

    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;      // Prendo le informazioni riguardanti l'artista che ha effettuato il login.
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());

        initListLastProp(loggedArtist);
        initTileLastArt(loggedArtist);
    }


    public void initListLastProp(ArtistBean art){
        List<ProposalBean> arrayOfProposalsBean = vps.retrieveArtGalleryProposals(art);

        for (ProposalBean n : arrayOfProposalsBean) {
            ArtGalleryBean artG = vps.retrieveArtGallery(n);   // Fai un retrieve della galleria associata alla proposta.
            listLastProp.getItems().add(artG.getNome());  // Popola la listView.
        }

    }


    public void initTileLastArt(ArtistBean art) {
        List<ArtworkBean> listOfArtWorksImage = vps.retrieveAllArtWorksImage(art);  // Prendi tutte le opere caricate dall'artista.

        tilePaneLastArt.setHgap(10);    // Setta i bordi orizzontali tra un tile e l'altro.
        tilePaneLastArt.setVgap(5);    // Setta i bordi verticali tra un tile e l'altro.

        for (ArtworkBean b : listOfArtWorksImage){    // Scorre tutti i blob relativi all'artista.

            InputStream inputStream = null;
            HBox hBoxBorder = new HBox();  // Imposta bordo all'immagine tramite un HBox
            hBoxBorder.setMinWidth(200);
            hBoxBorder.setMinHeight(200);
            hBoxBorder.getStyleClass().add("hBoxBorder");
            hBoxBorder.setAlignment(Pos.CENTER);

            try {
                inputStream = b.getImmagine().getBinaryStream();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Image image = new Image(inputStream, 200, 200, true, false);

            ImageView imageThumb = new ImageView();
            imageThumb.setImage(image);

            hBoxBorder.getChildren().add(imageThumb);

            tilePaneLastArt.getChildren().add(hBoxBorder);   // Popola la tilePane.
        }

    }


    private void makeDraggable(){
        anchorParentArtSumD.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParentArtSumD.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void exitWindow() throws IOException {
        stage = (Stage) anchorParentArtSumD.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentArtSumD.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeGifPaneClickable(){

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                SceneController.switchToSceneProfiloAlgoritmo(event, art);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelAlgoritmo.setTextFill(Color.rgb(209, 62, 10)));

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelAlgoritmo.setTextFill(Color.rgb(45, 132, 101)));

    }

    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                SceneController.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void switchToProfiloArtistaFromArtistSumD(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController.switchToSceneProfiloArtista(actionEvent, art);
    }

    public void switchToProfiloVendutoFromArtistSumD(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneProfiloVenduto(actionEvent, art);
    }

    public void switchToProfiloOfferteMostreFromArtistSumD(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneProfiloOfferteMostre(actionEvent, art);
    }

    public void switchToUploadOperaFromArtistSumD(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneUploadOpera(actionEvent, art);
    }

}
