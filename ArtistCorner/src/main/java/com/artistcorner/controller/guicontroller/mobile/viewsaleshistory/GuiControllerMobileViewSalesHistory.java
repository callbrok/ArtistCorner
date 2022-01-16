package com.artistcorner.controller.guicontroller.mobile.viewsaleshistory;

import com.artistcorner.controller.applicationcontroller.ViewSalesHistory;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.SellArtWorkNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerMobileViewSalesHistory {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public ListView listViewSale;
    public Pane paneExceptionLoad;
    private double x=0, y=0;
    private Stage stage;

    ArtistBean art;

    public void initialize(){
        makeDraggable();

    }

    public void getArtist(ArtistBean loggedArtist) throws IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        populateListView(loggedArtist);
    }



    // Riprogetta la riga della ListView.
    public static class HBoxCell extends HBox {
        Label labelTitolo = new Label();
        Label labelPrezzo = new Label();
        Label labelAcquirente = new Label();

        HBoxCell(String labelNewTitolo, String labelNewPrezzo, String labelNewAcquirente) {
            super();

            labelTitolo.setText(labelNewTitolo);
            labelTitolo.setStyle("-fx-font-weight: bold");
            labelTitolo.setTextFill(Color.rgb(39, 116, 88));
            labelTitolo.setMaxWidth(Double.MAX_VALUE);

            labelPrezzo.setText(labelNewPrezzo + " \u20ac");
            labelPrezzo.setMaxWidth(Double.MAX_VALUE);

            labelAcquirente.setText(labelNewAcquirente);
            labelAcquirente.setMaxWidth(Double.MAX_VALUE);

            VBox vBoxInfo1 = new VBox(labelTitolo, labelAcquirente);
            vBoxInfo1.setSpacing(10);
            vBoxInfo1.setAlignment(Pos.TOP_LEFT);


            HBox.setHgrow(vBoxInfo1, Priority.ALWAYS);

            this.getChildren().addAll(vBoxInfo1, labelPrezzo);
        }
    }



    public void populateListView(ArtistBean art) throws IOException {
        ViewSalesHistory vsh = new ViewSalesHistory();
        ArrayList<ArtWorkBean> arrayOfArtwork = null;

        try {
            arrayOfArtwork = vsh.retrieveSellArtWorks(art);

            for (ArtWorkBean n : arrayOfArtwork) {
                listViewSale.getItems().add(new HBoxCell(n.getTitolo(), String.valueOf(n.getPrezzo()), "acquirente"));  // Popola la listView.
            }


        } catch (SellArtWorkNotFoundException e) {
            // Eccezione: Nessun opera venduta.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.SELLARTNOTFOUND);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
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


    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }
}