package com.artistcorner.controller.guicontroller.mobile.viewfavouritesbuyer;

import com.artistcorner.controller.applicationcontroller.ViewFavouritesBuyer;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtworkManagementProblemException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileViewFavouritesBuyer {
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private AnchorPane anchorMainFavMob;
    @FXML
    private ListView<HBoxCellMobile> listView;
    @FXML
    private Stage stageFavMob;
    @FXML
    private Pane paneExceptionLoad;

    private double x=0;
    private  double y=0;
    BuyerBean buy;

    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(event);
    }

    private void makeDraggable(){

        anchorMainFavMob.setOnMouseDragged((eventDragFav -> {
            stageFavMob = (Stage) ((Node)eventDragFav.getSource()).getScene().getWindow();
            stageFavMob.setX(eventDragFav.getScreenX() - x);
            stageFavMob.setY(eventDragFav.getScreenY() - y);
        }));

        anchorMainFavMob.setOnMousePressed((eventPressFav -> {
            x=eventPressFav.getSceneX();
            y= eventPressFav.getSceneY();
        }));

    }

    public void initialize(){
        makeDraggable();
    }

    public void getBuyer(BuyerBean loggedBuyer) throws SQLException, IOException{
        buy = loggedBuyer;      // Prendo le informazioni riguardanti l'acquirente che ha effettuato il login.
        labelUsernameDisplay.setText(buy.getNome() + " " + buy.getCognome());
        populateListView();
    }

    public void exitWindow() {
        stageFavMob = (Stage) anchorMainFavMob.getScene().getWindow();
        stageFavMob.close();
    }

    public void minimizeWindow() {
        stageFavMob = (Stage) anchorMainFavMob.getScene().getWindow();
        stageFavMob.setIconified(true);
    }

    public void switchToSearchArtWorkBuyer(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneSearchArtWorkBuyer(actionEvent,buy);
    }

    public void switchToSummaryBuyer(ActionEvent actionEvent) throws IOException{
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneBuyerSummary(actionEvent,buy);
    }
    public class HBoxCellMobile extends HBox {
        Label labelArtWorkName = new Label();
        Label labelArtistName = new Label();
        Button buttonAcquista = new Button();
        Button buttonPreferiti = new Button();
        Label prezzo = new Label();
        ImageView imageView = new ImageView();

        public HBoxCellMobile(List<ArtworkBean> arrayListArtWorkId, ArtworkBean artwBean, ArtistBean artBean, BuyerBean buy){
            imageView.setImage(extractImage(artwBean.getImmagine()));
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);

            labelArtWorkName.setText(artwBean.getTitolo());
            labelArtWorkName.setTextFill(Paint.valueOf("39A67F"));

            labelArtistName.setText(artBean.getNome()+" "+artBean.getCognome());
            labelArtistName.setTextFill(Paint.valueOf("39A67F"));

            prezzo.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #39A67F ");
            prezzo.setText("€ " + artwBean.getPrezzo());
            prezzo.setAlignment(Pos.CENTER);
            prezzo.setMinWidth(100);
            prezzo.setPrefHeight(75);

            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setMinWidth(120);
            vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");

            HBox.setHgrow(labelArtWorkName, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 10, 10, 10));
            buttonAcquista.setText("Acquista");
            buttonAcquista.setPrefSize(100, 35);
            buttonAcquista.getStyleClass().add("buttonBuy");


            buttonPreferiti.setText("Aggiungi ai Preferiti");
            buttonPreferiti.setPrefSize(100, 35);
            buttonPreferiti.getStyleClass().add("buttonBuy");

            VBox vBox = new VBox(buttonAcquista, buttonPreferiti);
            vBox.setStyle(" -fx-font-size: 8px;-fx-background-radius: 0;");
            vBox.setSpacing(2.5);
            vBox.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ");
            vBox.setAlignment(Pos.CENTER);

            if(buy.getIdBuyer() == 13){buttonAcquista.setDisable(true);}
            if(buy.getIdBuyer() == 13){buttonPreferiti.setDisable(true);}

            ViewFavouritesBuyer sa = new ViewFavouritesBuyer();

            buttonAcquista.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    buttonPreferiti.setText("Paga con Carte");
                    buttonAcquista.setText("Paga con PayPal");
                    buttonAcquista.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            try {
                                sa.finishPayment(artwBean,buy,artBean);
                            } catch (BuyArtworkManagementProblemException | FavouritesManagementProblemException e) {
                                e.printStackTrace();
                            }
                            buttonAcquista.setDisable(true);
                            buttonPreferiti.setVisible(false);
                            buttonAcquista.setText("Opera Acquistata!");
                            buttonAcquista.setPrefSize(100, 35);
                            buttonAcquista.setAlignment(Pos.BOTTOM_CENTER);
                            buttonPreferiti.setPrefSize(100, 10);
                            buttonAcquista.setStyle("-fx-font-size: 8px;-fx-background-color: #ffffff");

                        }
                    });
                    buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            try {
                                sa.finishPayment(artwBean,buy,artBean);
                            } catch (BuyArtworkManagementProblemException | FavouritesManagementProblemException e) {
                                e.printStackTrace();
                            }
                            buttonAcquista.setStyle("-fx-font-size: 8px;-fx-background-color: #ffffff");
                            buttonAcquista.setDisable(true);
                            buttonPreferiti.setVisible(false);
                            buttonAcquista.setText("Opera Acquistata!");
                            buttonAcquista.setAlignment(Pos.BOTTOM_CENTER);
                            buttonAcquista.setPrefSize(100, 35);
                            buttonPreferiti.setPrefSize(100, 10);
                        }
                    });
                }

            });
            for (ArtworkBean a : arrayListArtWorkId) {
                if (a.getIdOpera() ==artwBean.getIdOpera()) {
                    buttonPreferiti.setText("Rimuovi dai Preferiti");
                }
            }
            buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = null;

                    try {
                        answer = sa.manageButtonClick(buttonPreferiti.getText(), artwBean, buy);
                    } catch (FavouritesManagementProblemException e) {
                        e.printStackTrace();
                    }

                    buttonPreferiti.setText(answer);
                }
            });


            this.getChildren().addAll(imageView, vBox1, prezzo, vBox);
        }

    }
    public void populateListView() throws SQLException, IOException {
        ViewFavouritesBuyer vfb = new ViewFavouritesBuyer();
        List<ArtworkBean> arrayOfArtWorkId;
        ArtistBean artist;
        try{
            arrayOfArtWorkId = vfb.retrieveArtWorkId(buy);
            for (ArtworkBean i: arrayOfArtWorkId) {
                ArtworkBean artWork = vfb.retrieveArtWork(i.getIdOpera());
                artist = vfb.retrieveArtistName(artWork);
                listView.getItems().add(new HBoxCellMobile(arrayOfArtWorkId, artWork, artist, buy));
            }
        }catch (ArtworkNotFoundException e) {
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;
            ev = ef.createView(ExceptionsTypeManager.ARTWORKNOTFOUND_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }

    private Image extractImage(Blob blob){
        InputStream inputStream = null;
        try {
            inputStream = blob.getBinaryStream();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert inputStream != null;
        return new Image(inputStream, 100, 100, true, false);

    }
}

