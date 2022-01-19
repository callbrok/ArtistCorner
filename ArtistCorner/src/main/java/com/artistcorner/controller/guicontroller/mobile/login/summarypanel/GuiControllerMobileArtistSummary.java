package com.artistcorner.controller.guicontroller.mobile.login.summarypanel;

import com.artistcorner.controller.applicationcontroller.Login;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerMobileArtistSummary {
    @FXML
    public AnchorPane anchorMainASummaryM;
    @FXML
    public Pane paneIdeaButton;
    @FXML
    public ImageView imageGifButton;
    @FXML
    public Label labelAlgoritmo;
    @FXML
    public Label labelUsernameDisplay;

    private double x=0;
    private double y=0;
    private Stage stage;

    ArtistBean art;


    public void initialize(){
        makeDraggable();
        makeGifPaneClickable();

    }

    public void getArtist(ArtistBean loggedArtist){
        art = loggedArtist;      // Prendo le informazioni riguardanti l'artista che ha effettuato il login.
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
    }

    public void makeGifPaneClickable(){

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneControllerMobile sc = new SceneControllerMobile();
            try {
                sc.switchToSceneProfiloAlgoritmo(event, art);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelAlgoritmo.setTextFill(Color.rgb(209, 62, 10)));

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelAlgoritmo.setTextFill(Color.rgb(45, 132, 101)));

    }

    public void exitWindow() {
        stage = (Stage) anchorMainASummaryM.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorMainASummaryM.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMainASummaryM.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMainASummaryM.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }


    public void switchToProfiloArtista(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloArtista(actionEvent, art);
    }

    public void switchToProfiloVenduto(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloVenduto(actionEvent, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloOfferteMostre(actionEvent, art);
    }

    public void switchToUploadOpera(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneUploadOpera(actionEvent, art);
    }




}
