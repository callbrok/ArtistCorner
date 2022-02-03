package com.artistcorner.controller.guicontroller.mobile.login.summarypanel;

import com.artistcorner.controller.applicationcontroller.ViewBuyerSummary;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import com.artistcorner.model.Buyer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileBuyerSummary {

    @FXML
    private AnchorPane anchorMainSummary;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private ListView<String> listViewCompra;
    private double x=0;
    private double y=0;
    private Stage stageSummary;

    BuyerBean buy;

    public void getBuyer(BuyerBean loggedBuyer) {
        buy = loggedBuyer;      // Prendo le informazioni riguardanti l'acquirente che ha effettuato il login.
        labelUsernameDisplay.setText(buy.getNome() + " " + buy.getCognome());
        listViewCompra.setStyle("-fx-font-size: 10px");
        inizializeOpereComprate(listViewCompra,buy);
    }

    public void initialize(){
        makeDraggable();
    }

    public void makeLogOut(ActionEvent eventLogOut) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(eventLogOut);
    }

    public void exitWindow() {
        stageSummary = (Stage) anchorMainSummary.getScene().getWindow();
        stageSummary.close();
    }
    private void makeDraggable(){
        anchorMainSummary.setOnMousePressed((eventPressScene -> {
            x=eventPressScene.getSceneX();
            y= eventPressScene.getSceneY();
        }));

        anchorMainSummary.setOnMouseDragged((eventDragScene -> {
            stageSummary = (Stage) ((Node)eventDragScene.getSource()).getScene().getWindow();
            stageSummary.setX(eventDragScene.getScreenX() - x);
            stageSummary.setY(eventDragScene.getScreenY() - y);
        }));
    }

    public void minimizeWindow() {
        stageSummary = (Stage) anchorMainSummary.getScene().getWindow();
        stageSummary.setIconified(true);
    }

    public void switchToSearchArtWorkBuyer(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneSearchArtWorkBuyer(actionEvent, buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException{
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneFavouritesBuyer(actionEvent,buy);
    }
    public void inizializeOpereComprate(ListView<String> listViewCompra, BuyerBean buy){
        ViewBuyerSummary vbs = new ViewBuyerSummary();
        List<Integer> arrayOfComprate = vbs.retrieveAllComprate(buy);
        List<ArtWorkBean> arrayFinal = new ArrayList<>();
        for (int n : arrayOfComprate) {
            ArtWorkBean artwork = vbs.retrieveArtWorks(n, 0);
            listViewCompra.getItems().add("Titolo Opera:  " + artwork.getTitolo() + "     Prezzo di acquisto:   € " + artwork.getPrezzo());  // Popola la listView.

            arrayFinal.add(artwork);
        }
    }

}

