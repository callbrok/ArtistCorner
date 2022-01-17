package com.artistcorner.controller.guicontroller.mobile.viewprofile;

import com.artistcorner.controller.applicationcontroller.ViewProfile;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerMobileViewProfile {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public TilePane tilePaneBlob;
    public AnchorPane anchorPaneFocus;
    public ImageView imageFocused;
    public Pane paneExceptionLoad;
    public ScrollPane scrollTileBlob;
    private double x=0, y=0;
    private Stage stage;

    ArtistBean art;


    public void getArtist(ArtistBean loggedArtist) throws SQLException, IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        initializeTilePane(loggedArtist);
    }


    public void initialize(){
        anchorPaneFocus.setVisible(false);
        scrollTileBlob.setFitToWidth(true);
        scrollTileBlob.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        makeDraggable();
    }

    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

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


    private void initializeTilePane(ArtistBean art) throws SQLException, IOException {
        ViewProfile vp = new ViewProfile();
        ArrayList<Blob> listOfArtWorksImage = null;  // Prendi tutte le opere caricate dall'artista.

        try {
            listOfArtWorksImage = vp.retrieveAllArtWorksImage(art);

            tilePaneBlob.setHgap(10);    // Setta i bordi orizzontali tra un tile e l'altro.
            tilePaneBlob.setVgap(5);    // Setta i bordi verticali tra un tile e l'altro.

            EventHandler<MouseEvent> mouseHandler = new EventHandler<>() {    // Crea un EventHandler sull'imageView all'interno del tilePane.
                @Override
                public void handle(MouseEvent t) {
                    ImageView imageView = (ImageView) t.getSource();  // Prende l'imageView collegata all'evento.

                    imageFocused.setImage(imageView.getImage());   // Setta l'immagine e la rende focused.
                    centerImage(imageFocused);                     // Centra l'immagine.
                    anchorPaneFocus.setVisible(true);
                }
            };

            anchorPaneFocus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                anchorPaneFocus.setVisible(false);
            });

            for (Blob b : listOfArtWorksImage){    // Scorre tutti i blob relativi all'artista.

                InputStream inputStream = b.getBinaryStream();

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
                tilePaneBlob.getChildren().add(imageThumb);   // Popola la tilePane.
            }

        } catch (ArtWorkNotFoundException e) {
            // Eccezione: Campi lasciati vuoti.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.ARTWORKNOTFOUND_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
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

    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(event);
    }

    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }
}
