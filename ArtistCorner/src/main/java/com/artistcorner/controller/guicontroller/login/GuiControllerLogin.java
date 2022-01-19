package com.artistcorner.controller.guicontroller.login;

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

public class GuiControllerLogin {
    @FXML
    public AnchorPane anchorParentLoginD;
    @FXML
    public SVGPath svgLogo;
    @FXML
    public TextField textFieldUsername;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public Pane paneExceptionLogin;
    @FXML
    public Label labelExceptionLogin;

    private double x=0;
    private double y=0;
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
        anchorParentLoginD.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParentLoginD.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void exitWindow() {
        stage = (Stage) anchorParentLoginD.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentLoginD.getScene().getWindow();
        stage.setIconified(true);
    }

    public void sendData(ActionEvent actionEvent) throws IOException {
        UserBean us = new UserBean(textFieldUsername.getText(), textFieldPassword.getText());

        try {
            lg.credentialLogin(us, actionEvent, "D");   // Passa le credenziali al controller applicativo per effettuare il login.
        }catch (UserNotFoundException | SQLException e){
            labelExceptionLogin.setText(e.getMessage());
            paneExceptionLogin.setVisible(true);
        }

    }
}
