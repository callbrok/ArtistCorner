package com.artistcorner.controller.guicontroller.login;

import com.artistcorner.controller.applicationcontroller.Login;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class GuiControllerLogin {
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private Button buttonMenuLogIn;
    @FXML
    private AnchorPane anchorSignUp;
    @FXML
    private AnchorPane anchorSummarySignUp;
    @FXML
    private AnchorPane anchorParentLoginD;
    @FXML
    private SVGPath svgLogo;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private Pane paneExceptionLogin;
    @FXML
    private Label labelExceptionLogin;

    private double x=0;
    private double y=0;
    private Stage stage;

    private Login lg = new Login();

    public void initialize(){
        makeDraggable();
        reloadLogin();

        labelExceptionLogin.setAlignment(Pos.CENTER);
        labelExceptionLogin.setMaxWidth(259);
        paneExceptionLogin.setVisible(false);
        svgLogo.setScaleX(1.1);
        svgLogo.setScaleY(1.1);

        anchorSummarySignUp.setVisible(false);
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

    public void reloadLogin() {
        buttonMenuLogIn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController scld = new SceneController();
            try {
                scld.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void changeToRegister(){
        anchorSummarySignUp.setVisible(true);
    }

    public void goToArtistSignUp() throws IOException {
        anchorSummarySignUp.setVisible(false);

        AnchorPane newLoadedPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login/SignUpArtistView.fxml")));
        anchorSignUp.getChildren().add(newLoadedPane);
    }
}
