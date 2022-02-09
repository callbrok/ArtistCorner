package com.artistcorner.controller.guicontroller.mobile.viewsoldartworks;

import com.artistcorner.controller.applicationcontroller.ViewSoldArtworks;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.SellArtworkNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileViewSoldArtworks {
    @FXML
    private AnchorPane anchorMain;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private ListView listViewSale;
    @FXML
    private Pane paneExceptionLoad;

    private double x=0;
    private double y=0;
    private Stage stage;

    private ArtistBean art;

    public void initialize(){
        labelUsernameDisplay.setAlignment(Pos.CENTER);
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
        ViewSoldArtworks vsh = new ViewSoldArtworks();
        List<ArtworkBean> arrayOfArtwork = null;

        try {
            arrayOfArtwork = vsh.retrieveSellArtWorks(art);

            for (ArtworkBean n : arrayOfArtwork) {
                BuyerBean currentBuyer = vsh.retrieveBuyerName(n);
                String buyerName = "Acquistata da: " + currentBuyer.getNome() + " " + currentBuyer.getCognome();

                listViewSale.getItems().add(new HBoxCell(n.getTitolo(), String.valueOf(n.getPrezzo()), buyerName));  // Popola la listView.
            }


        } catch (SellArtworkNotFoundException e) {
            // Eccezione: Nessun opera venduta.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.SELLARTNOTFOUND_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }


    public void exitWindow() throws IOException {
        SceneController.deleteSerialNodo(art.getIdArtista());

        stage = (Stage) anchorMain.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMain.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMain.setOnMouseDragged((event -> {
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
