package com.artistcorner.controller.guicontroller.login.summarypanel;

import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.Buyer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiControllerBuyerSummary {
    public AnchorPane anchorParent;
    public Pane paneSearch;
    public Pane paneFavourites;
    public Pane paneComprate;
    public Label labelSearch;
    public Label labelFavourites;
    public Label labelComprate;
    public Label labelLogOut;
    private double x=0, y=0;
    private Stage stage;
    public Button button1;
    public Button button2;
    public Button button3;
    Buyer buy;


    public void initialize(){
        makeDraggable();
        makeLogOut();
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
    public void switchToSearchArtWorkBuyer(MouseEvent mouseEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneSearchArtWorkBuyer(mouseEvent,buy);
    }

    public void switchToFavouritesBuyer(MouseEvent mouseEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneFavouritesBuyer(mouseEvent,buy);
    }
    public void makeSearchPaneClickable(){

        paneSearch.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToSceneSearchArtWorkBuyer(event, buy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        paneSearch.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            labelSearch.setTextFill(Color.rgb(209, 62, 10));
        });

        paneSearch.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            labelSearch.setTextFill(Color.rgb(45, 132, 101));
        });

    }
    public void makeFavouritesPaneClickable(){

        paneFavourites.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToSceneFavouritesBuyer(event, buy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        paneFavourites.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            labelFavourites.setTextFill(Color.rgb(209, 62, 10));
        });

        paneFavourites.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            labelFavourites.setTextFill(Color.rgb(45, 132, 101));
        });

    }
}
