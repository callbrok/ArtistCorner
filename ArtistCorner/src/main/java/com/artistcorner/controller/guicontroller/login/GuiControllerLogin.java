package com.artistcorner.controller.guicontroller.login;

import com.artistcorner.controller.applicationcontroller.Login;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiControllerLogin {
    public AnchorPane anchorParent;
    public SVGPath svgLogo;
    public TextField textFieldUsername;
    public TextField textFieldPassword;
    public Pane paneExceptionLogin;
    public Label labelExceptionLogin;

    private double x=0, y=0;
    private Stage stage;

    Login lg = new Login();

    public void initialize(){
        makeDraggable();

        labelExceptionLogin.setAlignment(Pos.CENTER);
        labelExceptionLogin.setMaxWidth(259);
        paneExceptionLogin.setVisible(false);
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
        UserBean us = new UserBean(textFieldUsername.getText(), textFieldPassword.getText());

        try {
            lg.credentialLogin(us, actionEvent);   // Passa le credenziali al controller applicativo per effettuare il login.
        }catch (UserNotFoundException e){
            labelExceptionLogin.setText(e.getMessage());
            paneExceptionLogin.setVisible(true);
        }

    }
}
