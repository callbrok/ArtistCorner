package com.artistcorner.controller.guicontroller.login;

import com.artistcorner.controller.applicationcontroller.Login;
import com.artistcorner.engclasses.bean.User;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiControllerLogin {
    public AnchorPane anchorParent;
    public SVGPath svgLogo;
    public TextField textFieldUsername;
    public TextField textFieldPassword;

    private double x=0, y=0;
    private Stage stage;

    Login lg = new Login();

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

    public void sendData(ActionEvent actionEvent) throws IOException {
        User us = new User(textFieldUsername.getText(), textFieldPassword.getText());
        lg.credentialLogin(us, actionEvent);   // Passa le credenziali al controller applicativo per effettuare il login.
    }
}
