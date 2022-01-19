package com.artistcorner.controller.guicontroller.login.summarypanel;

import com.artistcorner.controller.applicationcontroller.ViewBuyerSummary;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.Buyer;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerBuyerSummary {
    public AnchorPane anchorParent;
    public Pane paneSearch;
    public Pane paneFavourites;
    public Pane paneComprate;
    public ListView<String> listViewCompra;
    public Label labelUsernameDisplay;
    public Label labelSearch;
    public Label labelFavourites;
    public Label labelComprate;
    public Label labelLogOut;
    private double x=0, y=0;
    private Stage stage;
    public Button button1;
    public Button button2;
    public Button button3;
    public SVGPath svgProfile;
    public Buyer buy;


    public void initialize(){
        makeDraggable();
        makeLogOut();
        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }

    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void getBuyer(Buyer loggedBuyer) {
        buy = loggedBuyer;
        labelUsernameDisplay.setText(buy.getNome()+" "+buy.getCognome());
        ViewBuyerSummary bs = new ViewBuyerSummary();
        bs.inizializeOpereComprate(listViewCompra,buy);
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

    public void exitWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.setIconified(true);
    }
    public void switchToSearchArtWorkBuyer(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneSearchArtWorkBuyer(actionEvent,buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneFavouritesBuyer(actionEvent,buy);
    }

}
