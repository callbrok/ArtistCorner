package com.example.testinterfaccia.graphcontroller;

import com.example.testinterfaccia.SceneController;
import com.example.testinterfaccia.logiccontroller.ProfiloOfferteMostreLogicController;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ProfiloOfferteMostreGraphController {
    ProfiloOfferteMostreLogicController omlc = new ProfiloOfferteMostreLogicController();

    public AnchorPane anchorParent;
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public WebView webMap;
    private double x=0, y=0;
    private Stage stage;

    public Pane paneInfoLoading;

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
    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.setIconified(true);
    }


    public void initialize() throws IOException {
        makeDraggable();
        setTooltipMenu();
        setWebMap();
    }

    private void setWebMap() {
        webMap.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/mappe.css").toExternalForm()); // Tolgo scrollbar nella webView
        String luogo="via palmanova 7 ardea";

        webMap.getEngine().getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        paneInfoLoading.setVisible(false);  // A fine caricamento
                    }
                });

        paneInfoLoading.setVisible(true);   // Durante il caricamento
        webMap.getEngine().loadContent(omlc.makeMapHtml(luogo));
    }



    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Carica Opera"));
        button4.setTooltip(new Tooltip("Offerte Mostre"));
        button5.setTooltip(new Tooltip("Opere Vendute"));
    }



    public void switchToMainArtista(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneMainArtista(actionEvent);
    }

    public void switchToProfiloArtista(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloArtista(actionEvent);
    }
}
