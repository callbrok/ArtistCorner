package com.artistcorner.controller.guicontroller.viewartgalleryproposals;

import com.artistcorner.controller.applicationcontroller.ViewArtGalleryProposals;
import com.artistcorner.engclasses.others.SceneController;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiControllerViewArtGalleryProposals {
    ViewArtGalleryProposals omlc = new ViewArtGalleryProposals();

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

    /**
     * A seconda dell'indirizzo mostra a video una mappa Google Maps interattiva.
     */
    private void setWebMap() {
        webMap.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/artist/webViewMap.css").toExternalForm());  // Elimina la scrollbar nella webView
        String luogo="via palmanova 7 ardea";

        /**
         * L'html nella WebView viene caricato ed interpretato da un thread in background il cui progresso
         * rintracciabile tramite il metodo getLoadWorker().
         */
        webMap.getEngine().getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {       // Identifica il completamento del thread
                        paneInfoLoading.setVisible(false);          // A thread concluso disabilita il messaggio di caricamento
                    }
                });

        paneInfoLoading.setVisible(true);              // Durante l'esecuzione del thread visualizza un messaggio di caricamento
        webMap.getEngine().loadContent(omlc.makeMapHtml(luogo));      // Carica l'html della pagina
    }

    /**
     * Setta i tooltip su i bottoni del menu.
     */
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
