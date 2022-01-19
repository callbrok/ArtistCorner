package com.artistcorner.controller.guicontroller.mobile.login;

import com.artistcorner.controller.applicationcontroller.Login;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerMobileLogin {
    @FXML
    public AnchorPane anchorMainLoginMobile;
    @FXML
    public SVGPath svgLogo;
    @FXML
    public TextField textFieldUsername;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public Label labelExceptionLoginM;
    @FXML
    public Pane paneExceptionLogin;

    private double x=0;
    private double y=0;
    private Stage stage;

    Login lg = new Login();


    public void initialize(){
        makeDraggable();

        labelExceptionLoginM.setAlignment(Pos.CENTER);
        labelExceptionLoginM.setMaxWidth(320);
        paneExceptionLogin.setVisible(false);
        svgLogo.setScaleX(1.3);
        svgLogo.setScaleY(1.3);
    }

    public void exitWindow() {
        stage = (Stage) anchorMainLoginMobile.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorMainLoginMobile.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMainLoginMobile.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMainLoginMobile.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void sendData(ActionEvent actionEvent) throws IOException {
        UserBean us = new UserBean(textFieldUsername.getText(), textFieldPassword.getText());

        try {
            lg.credentialLogin(us, actionEvent, "M");   // Passa le credenziali al controller applicativo per effettuare il login.
        }catch (UserNotFoundException | SQLException e){
            labelExceptionLoginM.setText(e.getMessage());
            paneExceptionLogin.setVisible(true);
        }

    }
}
