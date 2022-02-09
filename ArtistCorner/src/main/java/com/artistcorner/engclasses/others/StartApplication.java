package com.artistcorner.engclasses.others;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication {
    @FXML
    private SVGPath svgLogo;
    @FXML
    private AnchorPane anchorParentStart;
    @FXML
    private Circle circleMain;
    @FXML
    private Button buttonDesktop;

    private double x=0;
    private double y=0;
    private Stage stage;


    public void initialize(){
        makeDraggable();
        initDesktop();

        svgLogo.setScaleX(1.1);
        svgLogo.setScaleY(1.1);
        svgLogo.setFill(Color.rgb(57, 166, 127));
        circleMain.setFill(Color.WHITE);
        circleMain.setStroke(Color.valueOf("0x277458"));
        circleMain.setStrokeWidth(3);
    }


    private void makeDraggable(){
        anchorParentStart.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParentStart.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void exitWindow() {
        stage = (Stage) anchorParentStart.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentStart.getScene().getWindow();
        stage.setIconified(true);
    }

    public void switchToMobile(ActionEvent event) throws IOException {
        SceneControllerMobile scm = new SceneControllerMobile();
        scm.switchToLogin(event);
    }

    public void initDesktop(){
        buttonDesktop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController scld = new SceneController();
            try {
                scld.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
