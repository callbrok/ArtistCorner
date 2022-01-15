package com.artistcorner.controller.guicontroller.mobile.login;

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

public class GuiControllerMobileLogin {
    public AnchorPane anchorMain;
    public SVGPath svgLogo;
    public TextField textFieldUsername;
    public TextField textFieldPassword;
    public Label labelExceptionLogin;
    public Pane paneExceptionLogin;
    private double x=0, y=0;
    private Stage stage;

    Login lg = new Login();


    public void initialize(){
        makeDraggable();

        labelExceptionLogin.setAlignment(Pos.CENTER);
        labelExceptionLogin.setMaxWidth(320);
        paneExceptionLogin.setVisible(false);
        svgLogo.setScaleX(1.3);
        svgLogo.setScaleY(1.3);
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

    public void sendData(ActionEvent actionEvent) throws IOException {
        UserBean us = new UserBean(textFieldUsername.getText(), textFieldPassword.getText());

        try {
            lg.credentialLogin(us, actionEvent, "M");   // Passa le credenziali al controller applicativo per effettuare il login.
        }catch (UserNotFoundException e){
            labelExceptionLogin.setText(e.getMessage());
            paneExceptionLogin.setVisible(true);
        }

    }
}
