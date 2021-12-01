package com.example.testinterfaccia.graphcontroller;

import com.example.testinterfaccia.SceneController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainArtistaGraphController {
    public AnchorPane anchorParent;
    public ImageView imageGifButton;
    public Pane paneIdeaButton;
    public Label labelAlgoritmo;
    private double x=0, y=0;
    private Stage stage;

    public void initialize(){
        makeDraggable();
        makeGifPaneClickable();

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
                sc.switchToSceneProfiloAlgoritmo(event);
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

    public void switchToProfiloArtista(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloArtista(actionEvent);
    }

    public void switchToProfiloVenduto(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloVenduto(actionEvent);
    }
}
