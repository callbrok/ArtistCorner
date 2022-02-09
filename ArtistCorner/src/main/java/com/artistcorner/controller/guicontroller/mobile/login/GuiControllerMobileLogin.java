package com.artistcorner.controller.guicontroller.mobile.login;

import com.artistcorner.controller.applicationcontroller.login.Login;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GuiControllerMobileLogin {
    @FXML
    private SVGPath svgGoogle;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private AnchorPane anchorSwitchSignUp;
    @FXML
    private AnchorPane paneRegister;
    @FXML
    private AnchorPane anchorMainLoginMobile;
    @FXML
    private SVGPath svgLogo;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private Label labelExceptionLoginM;
    @FXML
    private Pane paneExceptionLogin;

    private double x=0;
    private double y=0;
    private Stage stage;

    private Login lg = new Login();


    public void initialize(){
        makeDraggable();

        paneRegister.setVisible(false);

        labelExceptionLoginM.setAlignment(Pos.CENTER);
        labelExceptionLoginM.setMaxWidth(320);
        paneExceptionLogin.setVisible(false);
        svgLogo.setScaleX(1.2);
        svgLogo.setScaleY(1.2);

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
        UserBean us = new UserBean();

        us.setUsername(textFieldUsername.getText());
        us.setPassword(textFieldPassword.getText());

        try {
            lg.credentialLogin(us, actionEvent, "M");   // Passa le credenziali al controller applicativo per effettuare il login.
        }catch (UserNotFoundException e){
            labelExceptionLoginM.setText(e.getMessage());
            paneExceptionLogin.setVisible(true);
        }

    }

    public void openRegisterSummary() {
        paneRegister.setVisible(true);
    }

    public void switchToArtistSignUp() throws IOException {
        paneRegister.setVisible(false);

        AnchorPane newLoadedPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mobile/login/RegisterArtistMobileView.fxml")));
        anchorSwitchSignUp.getChildren().add(newLoadedPane);
    }
    public void switchToGallerySignUp() throws IOException {
        paneRegister.setVisible(false);

        AnchorPane newLoadedPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mobile/login/RegisterGalleryMobileView.fxml")));
        anchorSwitchSignUp.getChildren().add(newLoadedPane);
    }
    public void switchToBuyerSignUp() throws IOException {
        paneRegister.setVisible(false);

        AnchorPane newLoadedPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mobile/login/RegisterBuyerMobileView.fxml")));
        anchorSwitchSignUp.getChildren().add(newLoadedPane);
    }

    public void openDialogLoginFbMobile() {
        Dialog<String> dialog = new Dialog<>();
        ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);

        dialog.setTitle("Login");
        dialog.setHeaderText(null);
        dialog.setContentText("Login con Facebook");

        dialog.showAndWait();
    }

    public void goToGuest(ActionEvent event) {
        UserBean us = new UserBean();

        us.setUsername("acquirenteGUEST");
        us.setPassword("ispw21");

        try {
            lg.credentialLogin(us, event, "M");
        }catch (UserNotFoundException | IOException e){
            labelExceptionLoginM.setText(e.getMessage());
            paneExceptionLogin.setVisible(true);
        }
    }
}
