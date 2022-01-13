package com.artistcorner.controller.guicontroller.login.summarypanel;

import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.Artist;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerArtistSummary {
    public AnchorPane anchorParent;
    public ImageView imageGifButton;
    public Pane paneIdeaButton;
    public Label labelAlgoritmo;
    public Label labelLogOut;
    public Label labelUsernameDisplay;
    public SVGPath svgProfile;
    private double x=0, y=0;
    private Stage stage;

    ArtistBean art;

    public void initialize(){
        makeDraggable();
        makeGifPaneClickable();
        makeLogOut();

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }

    public void getArtist(ArtistBean loggedArtist){
        art = loggedArtist;      // Prendo le informazioni riguardanti l'artista che ha effettuato il login.
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
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
