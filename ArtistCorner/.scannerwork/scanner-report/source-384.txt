package com.artistcorner.controller.guicontroller.mobile.login.summaries;

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
    private AnchorPane anchorMainASummaryM;
    @FXML
    private Pane paneIdeaButton;
    @FXML
    private ImageView imageGifButton;
    @FXML
    private Label labelAlgoritmo;
    @FXML
    private Label labelUsernameDisplay;

    private double x=0;
    private double y=0;
    private Stage stage;

    private ArtistBean art;


    public void initialize(){
        makeDraggable();
        makeGifPaneClickable();

        labelUsernameDisplay.setAlignment(Pos.CENTER);

    }

    public void getArtist(ArtistBean loggedArtist){
        art = loggedArtist;      // Prendo le informazioni riguardanti l'artista che ha effettuato il login.
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
    }

    public void makeGifPaneClickable(){

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                SceneControllerMobile.switchToSceneProfiloAlgoritmo(event, art);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelAlgoritmo.setTextFill(Color.rgb(209, 62, 10)));

        paneIdeaButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelAlgoritmo.setTextFill(Color.rgb(45, 132, 101)));

    }

    public void exitWindow() throws IOException {
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


    public void switchToProfiloArtistaFromArtistSumM(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile.switchToSceneProfiloArtista(actionEvent, art);
    }

    public void switchToProfiloVendutoFromArtistSumM(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile.switchToSceneProfiloVenduto(actionEvent, art);
    }

    public void switchToProfiloOfferteMostreFromArtistSumM(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile.switchToSceneProfiloOfferteMostre(actionEvent, art);
    }

    public void switchToUploadOperaFromArtistSumM(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile.switchToSceneUploadOpera(actionEvent, art);
    }




}
