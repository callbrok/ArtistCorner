package com.example.testinterfaccia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class LoginGraphController {
    public AnchorPane anchorParent;
    public SVGPath svgLogo;

    private double x=0, y=0;
    private Stage stage;

    public void initialize(){
        makeDraggable();

        svgLogo.setScaleX(1.1);
        svgLogo.setScaleY(1.1);
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
}
