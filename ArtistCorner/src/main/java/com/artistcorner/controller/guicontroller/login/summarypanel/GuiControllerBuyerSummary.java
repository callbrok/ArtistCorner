package com.artistcorner.controller.guicontroller.login.summarypanel;

import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerBuyerSummary {
    public AnchorPane anchorParent;
    public Pane paneSearch;
    public Pane paneFavourites;
    public Pane paneComprate;
    public ListView<String> listViewCompra;
    public Label labelSearch;
    public Label labelFavourites;
    public Label labelComprate;
    public Label labelLogOut;
    private double x=0, y=0;
    private Stage stage;
    public Button button1;
    public Button button2;
    public Button button3;
    public Buyer buy;


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
        inizializeOpereComprate();
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

    public void inizializeOpereComprate() {
        ArrayList<Integer> arrayOfComprate = BuyerDAO.retrieveAllComprate(buy.getIdBuyer());
        System.out.println(arrayOfComprate);
        ArrayList<ArtWork> arrayFinal = new ArrayList<>();
        for (int n : arrayOfComprate) {
            ArtWork artwork = BuyerDAO.retrieveArtWorks(n, 0);
            listViewCompra.getItems().add("Titolo Opera:  " + artwork.getTitolo() + "     Prezzo di acquisto:   € " + artwork.getPrezzo());  // Popola la listView.

            arrayFinal.add(artwork); // Popola l'array con tutte le gallerie relative alle proposte dell'utente.
        }

        // listViewCompra.getSelectionModel().selectedItemProperty().addListener( {

    }

}
