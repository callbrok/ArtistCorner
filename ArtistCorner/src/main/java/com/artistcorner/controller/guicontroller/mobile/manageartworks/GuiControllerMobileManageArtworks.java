package com.artistcorner.controller.guicontroller.mobile.manageartworks;

import com.artistcorner.controller.applicationcontroller.ManageArtworks;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileManageArtworks {
    @FXML
    private AnchorPane anchorMainViewM;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private TilePane tilePaneBlobM;
    @FXML
    private AnchorPane anchorPaneFocusM;
    @FXML
    private ImageView imageFocusedM;
    @FXML
    private Pane paneExceptionLoad;
    @FXML
    private ScrollPane scrollTileBlob;

    private double x=0;
    private double y=0;
    private Stage stage;

    private ArtistBean art;


    public void getArtist(ArtistBean loggedArtist) throws SQLException, IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        initializeTilePane(loggedArtist);
    }


    public void initialize(){
        anchorPaneFocusM.setVisible(false);
        scrollTileBlob.setFitToWidth(true);
        scrollTileBlob.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        labelUsernameDisplay.setAlignment(Pos.CENTER);

        makeDraggable();
    }

    /**
     * Centra dinamicamente l'immagine visualizzata in overlay.
     */
    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double wM = 0;
            double hM = 0;

            double ratioXM = imageView.getFitWidth() / img.getWidth();      // Larghezza dell'imageview / larghezza dell'immagine.
            double ratioYM = imageView.getFitHeight() / img.getHeight();    // Altezza dell'imageView / altezza dell'immagine.

            double reducCoeff = 0;
            if(ratioXM >= ratioYM) {
                reducCoeff = ratioYM;
            } else {
                reducCoeff = ratioXM;
            }

            wM = img.getWidth() * reducCoeff;
            hM = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - wM) / 2);
            imageView.setY((imageView.getFitHeight() - hM) / 2);

        }
    }

    /**
     * Inizializza la tilePane contenente tutte le opere caricate dall'artista.
     */
    private void initializeTilePane(ArtistBean art) throws SQLException, IOException {
        ManageArtworks vp = new ManageArtworks();
        List<ArtworkBean> listOfArtWorks = null;  // Prendi tutte le opere caricate dall'artista.

        try {
            listOfArtWorks = vp.retrieveAllArtWorksImage(art);

            tilePaneBlobM.setHgap(10);    // Setta i bordi orizzontali tra un tile e l'altro.
            tilePaneBlobM.setVgap(5);    // Setta i bordi verticali tra un tile e l'altro.


            anchorPaneFocusM.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> anchorPaneFocusM.setVisible(false));

            for (ArtworkBean bm : listOfArtWorks){    // Scorre tutti i blob relativi all'artista.

                InputStream inputStream = bm.getImmagine().getBinaryStream();

                /*1)preserveRatio:
                Indicates whether to preserve the aspect ratio of the source image when scaling to
                fit the image within the fitting bounding box.

                2)smooth:
                Indicates whether to use a better quality filtering algorithm or a faster one when transforming
                or scaling the source image to fit within the bounding box provided by fitWidth and fitHeight. */
                Image imageM = new Image(inputStream, 130, 130, true, false);

                ImageView imageThumb = new ImageView();
                imageThumb.setImage(imageM);


                // Implementa eliminazione opera.
                Button buttonRemove = new Button();
                buttonRemove.setText("Rimuovi");
                buttonRemove.getStyleClass().add("buttonInterface");

                VBox vBoxInfo = new VBox(imageThumb, buttonRemove);
                vBoxInfo.setAlignment(Pos.BASELINE_CENTER);

                buttonRemove.setOnAction(event2 -> {
                    vp.removeArtWork(bm);

                    try {
                        SceneControllerMobile.switchToSceneProfiloArtista(event2, art);
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                });

                imageThumb.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { // Crea un EventHandler per ogni imageView all'interno del tilePane.
                    InputStream inputStreamFocus = null;

                    try {
                        inputStreamFocus = bm.getImmagine().getBinaryStream();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Image imageFocus = new Image(inputStreamFocus);

                    imageFocusedM.setImage(imageFocus);   // Setta l'immagine e la rende focused.
                    centerImage(imageFocusedM);                     // Centra l'immagine.
                    anchorPaneFocusM.setVisible(true);

                    event.consume();
                });

                tilePaneBlobM.getChildren().add(vBoxInfo);   // Popola la tilePane.
            }

        } catch (ArtworkNotFoundException e) {
            // Eccezione: Nessun opera caricata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.ARTWORKNOTFOUND_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }



    public void exitWindow() throws IOException {
        stage = (Stage) anchorMainViewM.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorMainViewM.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMainViewM.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMainViewM.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToLogin(event);
    }

    public void switchToSceneMainArtistaFromManageArtM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtistaFromManageArtM(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOperaFromManageArtM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVendutoFromManageArtM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostreFromManageArtM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneProfiloOfferteMostre(event, art);
    }
}
