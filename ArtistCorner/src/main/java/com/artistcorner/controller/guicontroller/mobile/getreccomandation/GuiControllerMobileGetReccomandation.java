package com.artistcorner.controller.guicontroller.mobile.getreccomandation;

import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GuiControllerMobileGetReccomandation {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    private double x=0, y=0;
    private Stage stage;

    ArtistBean art;
    
    public void initialize(){
        makeDraggable();

    }

    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
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
}
