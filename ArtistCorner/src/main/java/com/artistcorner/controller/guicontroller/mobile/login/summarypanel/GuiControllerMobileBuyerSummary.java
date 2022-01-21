package com.artistcorner.controller.guicontroller.mobile.login.summarypanel;

import com.artistcorner.controller.applicationcontroller.ViewBuyerSummary;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import com.artistcorner.model.Buyer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileBuyerSummary {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public ListView<String> listViewCompra;
    public Button button1;
    public Button button2;
    public Label labelComprate;
    private double x=0, y=0;
    private Stage stage;
    @FXML
    private Pane paneExceptionLoad;

    BuyerBean buy;


    public void initialize(){
        makeDraggable();
    }

    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(event);
    }

    public void getBuyer(BuyerBean loggedBuyer) {
        buy = loggedBuyer;      // Prendo le informazioni riguardanti l'acquirente che ha effettuato il login.
        labelUsernameDisplay.setText(buy.getNome() + " " + buy.getCognome());
        ViewBuyerSummary bs = new ViewBuyerSummary();
        listViewCompra.setStyle("-fx-font-size: 10px");
        inizializeOpereComprate(listViewCompra,buy);
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

    public void switchToSearchArtWorkBuyer(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneSearchArtWorkBuyer(actionEvent, buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException{
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneFavouritesBuyer(actionEvent,buy);
    }
    public void inizializeOpereComprate(ListView<String> listViewCompra, BuyerBean buy){
        Buyer buyer = new Buyer(buy.getIdBuyer(),buy.getNome(),buy.getCognome());
        ViewBuyerSummary vbs = new ViewBuyerSummary();
        List<Integer> arrayOfComprate = vbs.retrieveAllComprate(buyer.getIdBuyer());
        List<ArtWorkBean> arrayFinal = new ArrayList<>();
        for (int n : arrayOfComprate) {
            ArtWorkBean artwork = vbs.retrieveArtWorks(n, 0);
            listViewCompra.getItems().add("Titolo Opera:  " + artwork.getTitolo() + "     Prezzo di acquisto:   € " + artwork.getPrezzo());  // Popola la listView.

            arrayFinal.add(artwork);
        }
    }

}

