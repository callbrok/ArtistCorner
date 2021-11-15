package com.example.testinterfaccia.graphcontroller;

import com.example.testinterfaccia.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProfiloArtistaGraphController {

    @FXML
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;

    public AnchorPane anchorParent;
    public AnchorPane anchorPaneFocus;
    public ImageView imageThumb;
    public ImageView imageFocused;
    private double x=0, y=0;
    private Stage stage;


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

    public void makeThumbFocused(){

        imageThumb.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            anchorPaneFocus.setVisible(true);
        });

        anchorPaneFocus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            anchorPaneFocus.setVisible(false);
        });
    }

    public void initializeImage(){
        /*1)preserveRatio:
            Indicates whether to preserve the aspect ratio of the source image when scaling to
            fit the image within the fitting bounding box.

          2)smooth:
            Indicates whether to use a better quality filtering algorithm or a faster one when transforming
            or scaling the source image to fit within the bounding box provided by fitWidth and fitHeight. */
        Image imageTT = new Image("/munch_expand.jpeg", 100, 100, true, false);
        imageThumb.setImage(imageTT);

        Image imageFF = new Image("/munch_expand.jpeg");
        imageFocused.setImage(imageFF);
    }

    public void initialize(){
        initializeImage();
        makeThumbFocused();
        makeDraggable();
        setTooltipMenu();

        anchorPaneFocus.setVisible(false);

    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Carica Opera"));
        button4.setTooltip(new Tooltip("Offerte Mostre"));
        button5.setTooltip(new Tooltip("Opere Vendute"));
    }

    public void switchToMainArtista(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneMainArtista(actionEvent);
    }
}
