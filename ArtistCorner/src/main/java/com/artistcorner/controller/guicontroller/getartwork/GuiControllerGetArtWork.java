package com.artistcorner.controller.guicontroller.getartwork;

import com.artistcorner.controller.guicontroller.viewsearchartworkbuyer.GuiControllerViewSearchArtWorkBuyer;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class GuiControllerGetArtWork {
    @FXML
    private ImageView imageRand;
    @FXML
    private AnchorPane anchorParentSearchBuy;
    @FXML
    private Label labelLogOutBuyerSearch;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private SVGPath svgProfileBuyerSearch;
    @FXML
    private Stage stageSearchBuy;

    private double x=0;
    private double y=0;
    private String category = "";
    BuyerBean buy;


    public void makeLogOutBuyer(){
        labelLogOutBuyerSearch.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void initialize() throws SQLException {
        makeDraggable();
        setTooltipMenu();
        makeLogOutBuyer();
        svgProfileBuyerSearch.setScaleX(0.07);
        svgProfileBuyerSearch.setScaleY(0.07);
    }

    public void getBuyer(BuyerBean loggedBuyer) {
        buy = loggedBuyer;
        labelUsernameDisplay.setText(buy.getNome()+" "+buy.getCognome());
    }

    private void makeDraggable(){
        anchorParentSearchBuy.setOnMousePressed((eventSearchBuy -> {
            x=eventSearchBuy.getSceneX();
            y= eventSearchBuy.getSceneY();
        }));

        anchorParentSearchBuy.setOnMouseDragged((eventSearchBuy -> {
            stageSearchBuy = (Stage) ((Node)eventSearchBuy.getSource()).getScene().getWindow();
            stageSearchBuy.setX(eventSearchBuy.getScreenX() - x);
            stageSearchBuy.setY(eventSearchBuy.getScreenY() - y);
        }));
    }

    public void minimizeWindow() {
        stageSearchBuy = (Stage) anchorParentSearchBuy.getScene().getWindow();
        stageSearchBuy.setIconified(true);
    }

    public void exitWindow() {
        stageSearchBuy = (Stage) anchorParentSearchBuy.getScene().getWindow();
        stageSearchBuy.close();
    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Cerca Opera"));
        button3.setTooltip(new Tooltip("Preferiti"));
    }

    public void goToNextImage(ActionEvent event) {
        InputStream inputStream = null;



        Image image = new Image(inputStream, 200, 200, true, false);
        imageRand.setImage(image);
    }

    public void addFavourite(ActionEvent event) {
    }
}
