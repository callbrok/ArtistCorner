package com.artistcorner.controller.guicontroller.manageartworks;

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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerManageArtworks {

    @FXML
    private Button button1VP;
    @FXML
    private Button button2VP;
    @FXML
    private Button button3VP;
    @FXML
    private Button button4VP;
    @FXML
    private Button button5VP;
    @FXML
    private AnchorPane anchorParentViewDes;
    @FXML
    private AnchorPane anchorPaneFocus;
    @FXML
    private ImageView imageFocused;
    @FXML
    private TilePane tilePaneBlob;
    @FXML
    private Label labelLogOut;
    @FXML
    private SVGPath svgProfile;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Pane paneExceptionLoad;

    private double x=0;
    private double y=0;
    private Stage stage;

    private ArtistBean art;


    public void getArtist(ArtistBean loggedArtist) throws SQLException, IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        initializeTilePane(loggedArtist);
    }

    private void makeDraggable(){
        anchorParentViewDes.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParentViewDes.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void exitWindow() throws IOException {
        stage = (Stage) anchorParentViewDes.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentViewDes.getScene().getWindow();
        stage.setIconified(true);
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


    public void initialize() throws SQLException {
        anchorPaneFocus.setVisible(false);

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);

        makeDraggable();
        setTooltipMenu();
        makeLogOut();
    }

    /**
     * Centra dinamicamente l'immagine visualizzata in overlay.
     */
    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();     // Larghezza dell'imageview / larghezza dell'immagine.
            double ratioY = imageView.getFitHeight() / img.getHeight();   // Altezza dell'imageView / altezza dell'immagine.

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

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

            tilePaneBlob.setHgap(20);    // Setta i bordi orizzontali tra un tile e l'altro.
            tilePaneBlob.setVgap(10);    // Setta i bordi verticali tra un tile e l'altro.


            anchorPaneFocus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> anchorPaneFocus.setVisible(false));

            for (ArtworkBean b : listOfArtWorks){    // Scorre tutti i blob relativi all'artista.

                InputStream inputStream = b.getImmagine().getBinaryStream();

                /*1)preserveRatio:
                Indicates whether to preserve the aspect ratio of the source image when scaling to
                fit the image within the fitting bounding box.

                2)smooth:
                Indicates whether to use a better quality filtering algorithm or a faster one when transforming
                or scaling the source image to fit within the bounding box provided by fitWidth and fitHeight. */
                Image image = new Image(inputStream, 100, 100, true, false);

                ImageView imageThumb = new ImageView();
                imageThumb.setImage(image);

                // Imposta bordo all'immagine tramite un HBox
                HBox hBoxBorder = new HBox();
                hBoxBorder.setMinWidth(120);
                hBoxBorder.setMinHeight(120);
                hBoxBorder.getStyleClass().add("hBoxBorderMA");
                hBoxBorder.setAlignment(Pos.CENTER);
                hBoxBorder.getChildren().add(imageThumb);

                // Implementa eliminazione opera.
                Button buttonRemove1 = new Button();
                buttonRemove1.setText("Rimuovi");
                buttonRemove1.getStyleClass().add("loggerButton");
                VBox vBoxInfo = new VBox(hBoxBorder, buttonRemove1);
                vBoxInfo.setSpacing(10);
                vBoxInfo.setAlignment(Pos.CENTER);

                buttonRemove1.setOnAction(event2 -> {
                    vp.removeArtWork(b);

                    try {
                        SceneController.switchToSceneProfiloArtista(event2, art);
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                });


                // Crea un EventHandler per ogni imageView all'interno del tilePane.
                imageThumb.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    InputStream inputStreamFocus = null;

                    try {
                        inputStreamFocus = b.getImmagine().getBinaryStream();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Image imageFocus = new Image(inputStreamFocus);

                    imageFocused.setImage(imageFocus);   // Setta l'immagine e la rende focused.
                    centerImage(imageFocused);                     // Centra l'immagine.
                    anchorPaneFocus.setVisible(true);

                    event.consume();
                });


                tilePaneBlob.getChildren().add(vBoxInfo);   // Popola la tilePane.
            }

        } catch (ArtworkNotFoundException e) {
            // Eccezione: Nessun opera trovata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.ARTWORKNOTFOUND);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }

    /**
     * Setta i tooltip su i bottoni del menu.
     */
    public void setTooltipMenu(){
        button1VP.setTooltip(new Tooltip("Home"));
        button2VP.setTooltip(new Tooltip("Profilo"));
        button3VP.setTooltip(new Tooltip("Carica Opera"));
        button4VP.setTooltip(new Tooltip("Offerte Mostre"));
        button5VP.setTooltip(new Tooltip("Opere Vendute"));
    }


    public void switchToSceneMainArtistaFromManageD(ActionEvent event) throws IOException, SQLException {
        SceneController.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtistaFromManageD(ActionEvent event) throws SQLException, IOException {
        SceneController.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOperaFromManageD(ActionEvent event) throws IOException {
        SceneController.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVendutoFromManageD(ActionEvent event) throws IOException {
        SceneController.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostreFromManageD(ActionEvent event) throws IOException {
        SceneController.switchToSceneProfiloOfferteMostre(event, art);
    }
}
