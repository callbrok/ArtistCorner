package com.artistcorner.controller.guicontroller.mobile.viewprofile;

import com.artistcorner.controller.applicationcontroller.ViewProfile;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileViewProfile {
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

    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double wM = 0;
            double hM = 0;

            double ratioXM = imageView.getFitWidth() / img.getWidth();
            double ratioYM = imageView.getFitHeight() / img.getHeight();

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


    private void initializeTilePane(ArtistBean art) throws SQLException, IOException {
        ViewProfile vp = new ViewProfile();
        List<ArtWorkBean> listOfArtWorks = null;  // Prendi tutte le opere caricate dall'artista.

        try {
            listOfArtWorks = vp.retrieveAllArtWorksImage(art);

            tilePaneBlobM.setHgap(10);    // Setta i bordi orizzontali tra un tile e l'altro.
            tilePaneBlobM.setVgap(5);    // Setta i bordi verticali tra un tile e l'altro.

            // Crea un EventHandler sull'imageView all'interno del tilePane.
            EventHandler<MouseEvent> mouseHandler = t -> {
                ImageView imageView = (ImageView)t.getSource();  // Prende l'imageView collegata all'evento.

                imageFocusedM.setImage(imageView.getImage());   // Setta l'immagine e la rende focused.
                centerImage(imageFocusedM);                     // Centra l'immagine.
                anchorPaneFocusM.setVisible(true);
            };

            anchorPaneFocusM.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> anchorPaneFocusM.setVisible(false));

            for (ArtWorkBean b : listOfArtWorks){    // Scorre tutti i blob relativi all'artista.

                InputStream inputStream = b.getImmagine().getBinaryStream();

                /*1)preserveRatio:
                Indicates whether to preserve the aspect ratio of the source image when scaling to
                fit the image within the fitting bounding box.

                2)smooth:
                Indicates whether to use a better quality filtering algorithm or a faster one when transforming
                or scaling the source image to fit within the bounding box provided by fitWidth and fitHeight. */
                Image image = new Image(inputStream, 130, 130, true, false);

                ImageView imageThumb = new ImageView();
                imageThumb.setImage(image);

                imageThumb.setOnMouseClicked(mouseHandler);   // Setta un mouseHandler su ogni immagine.

                // Implementa eliminazione opera.
                Button buttonRemove = new Button();
                buttonRemove.setText("Rimuovi");
                VBox vBoxInfo = new VBox(imageThumb, buttonRemove);
                vBoxInfo.setAlignment(Pos.BASELINE_CENTER);

                buttonRemove.setOnAction(event2 -> {
                    SceneControllerMobile scvpd = new SceneControllerMobile();
                    vp.removeArtWork(b);

                    try {
                        scvpd.switchToSceneProfiloArtista(event2, art);
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                });

                tilePaneBlobM.getChildren().add(vBoxInfo);   // Popola la tilePane.
            }

        } catch (ArtWorkNotFoundException e) {
            // Eccezione: Campi lasciati vuoti.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.ARTWORKNOTFOUND_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }



    public void exitWindow() throws IOException {
        SceneController.deleteSerialNodo(art.getIdArtista());

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
        SceneController.deleteSerialNodo(art.getIdArtista());
        SceneControllerMobile smvm = new SceneControllerMobile();
        smvm.switchToLogin(event);
    }

    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        SceneControllerMobile smvm = new SceneControllerMobile();
        smvm.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile smvm = new SceneControllerMobile();
        smvm.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneControllerMobile smvm = new SceneControllerMobile();
        smvm.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneControllerMobile smvm = new SceneControllerMobile();
        smvm.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneControllerMobile smvm = new SceneControllerMobile();
        smvm.switchToSceneProfiloOfferteMostre(event, art);
    }
}
