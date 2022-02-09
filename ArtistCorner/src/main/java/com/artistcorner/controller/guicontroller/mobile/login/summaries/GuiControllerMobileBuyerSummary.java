package com.artistcorner.controller.guicontroller.mobile.login.summaries;

import com.artistcorner.controller.applicationcontroller.login.summaries.ViewBuyerSummary;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileBuyerSummary {
    @FXML
    private Button buttonLogout;
    @FXML
    private SVGPath svgLogout;
    @FXML
    private Pane paneGuest;
    @FXML
    private Button button2;
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

        if(buy.getIdBuyer() == 13){initGuest();}

        labelUsernameDisplay.setText(buy.getNome() + " " + buy.getCognome());
        listViewCompra.setStyle("-fx-font-size: 10px");
        inizializeOpereComprate(listViewCompra,buy);
    }

    public void initialize(){
        paneGuest.setVisible(false);
        makeDraggable();
    }

    public void makeLogOut(ActionEvent eventLogOut) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(eventLogOut);
    }

    public void initGuest(){
        button2.setDisable(true);
        paneGuest.setVisible(true);
        buttonLogout.setText("Accedi");
        svgLogout.setContent("M16 2c3.309 0 6 2.691 6 6s-2.691 6-6 6-6-2.691-6-6 2.691-6 6-6zm0-2c-4.418 0-8 3.582-8 8s3.582 8 8 8 8-3.582 8-8-3.582-8-8-8zm-5.405 16.4l-1.472 1.6h-3.123v2h-2v2h-2v-2.179l5.903-5.976c-.404-.559-.754-1.158-1.038-1.795l-6.865 6.95v5h6v-2h2v-2h2l2.451-2.663c-.655-.249-1.276-.562-1.856-.937zm7.405-11.4c.551 0 1 .449 1 1s-.449 1-1 1-1-.449-1-1 .449-1 1-1zm0-1c-1.104 0-2 .896-2 2s.896 2 2 2 2-.896 2-2-.896-2-2-2z");
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
        List<ArtworkBean> arrayOfComprate = vbs.retrieveAllComprate(buy);
        List<ArtworkBean> arrayFinal = new ArrayList<>();
        for (ArtworkBean n : arrayOfComprate) {
            ArtworkBean artwork = vbs.retrieveArtWorks(n.getIdOpera(), 0);
            listViewCompra.getItems().add("Titolo Opera:  " + artwork.getTitolo() + "     Prezzo di acquisto:   € " + artwork.getPrezzo());  // Popola la listView.

            arrayFinal.add(artwork);
        }
    }

}

