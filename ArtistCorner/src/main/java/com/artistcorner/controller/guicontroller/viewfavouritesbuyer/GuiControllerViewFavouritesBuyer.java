package com.artistcorner.controller.guicontroller.viewfavouritesbuyer;

import com.artistcorner.controller.applicationcontroller.ViewFavouritesBuyer;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtworkManagementProblemException;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerViewFavouritesBuyer {
        @FXML
        private AnchorPane paneException;
        @FXML
        private AnchorPane anchorParentFavDesk;
        @FXML
        private Label labelLogOutFavDesk;
        @FXML
        private ListView<HBoxCell> listView;
        @FXML
        private Label labelUsernameDisplay;
        @FXML
        private SVGPath svgProfileFavDesk;
        @FXML
        private Button button1;
        @FXML
        private Button button2;
        @FXML
        private Button button3;
        @FXML
        private Pane paneExceptionLoad;
        @FXML
        private Stage stageFavDesk;

        private double x=0;
        private double y=0;
        BuyerBean buy;


        public void initialize() throws SQLException {
                makeDraggable();
                setTooltipMenu();
                makeLogOutFavDesk();
                svgProfileFavDesk.setScaleX(0.07);
                svgProfileFavDesk.setScaleY(0.07);
                paneException.setVisible(false);

        }
        public void makeLogOutFavDesk(){
                labelLogOutFavDesk.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        SceneController sc = new SceneController();
                        try {
                                sc.switchToLogin(event);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                });

        }

        public void getBuyer(BuyerBean loggedBuyer) throws SQLException, IOException {
                buy = loggedBuyer;
                labelUsernameDisplay.setText(buy.getNome()+" "+buy.getCognome());
                populateListView();
        }

        private void makeDraggable(){
                anchorParentFavDesk.setOnMousePressed((eventFav -> {
                        x=eventFav.getSceneX();
                        y= eventFav.getSceneY();
                }));

                anchorParentFavDesk.setOnMouseDragged((eventFav -> {
                        stageFavDesk = (Stage) ((Node)eventFav.getSource()).getScene().getWindow();
                        stageFavDesk.setX(eventFav.getScreenX() - x);
                        stageFavDesk.setY(eventFav.getScreenY() - y);
                }));
        }
        public void minimizeWindow() {
                stageFavDesk = (Stage) anchorParentFavDesk.getScene().getWindow();
                stageFavDesk.setIconified(true);
        }

        public void exitWindow() {
                stageFavDesk = (Stage) anchorParentFavDesk.getScene().getWindow();
                stageFavDesk.close();
        }

        public void setTooltipMenu(){
                button1.setTooltip(new Tooltip("Home"));
                button2.setTooltip(new Tooltip("Cerca Opera"));
                button3.setTooltip(new Tooltip("Preferiti"));
        }

        public void switchToBuyerSummary(ActionEvent actionEvent) throws IOException {
                SceneController sc = new SceneController();
                sc.switchToSceneBuyerSummary(actionEvent,buy);
        }

        public void switchToSearchArtWorkBuyer(ActionEvent actionEvent) throws IOException {
                SceneController sc = new SceneController();
                sc.switchToSceneSearchArtWorkBuyer(actionEvent,buy);
        }

        public class HBoxCell extends HBox {

                Label labelArtWorkNameFavDesk = new Label();
                Label labelArtistNameFavDesk = new Label();
                Button buttonAcquistaFavDesk = new Button();
                Button buttonPreferitiFavDesk = new Button();
                Label prezzoFavDesk = new Label();
                ImageView imageView = new ImageView();

                public HBoxCell(List<ArtworkBean> arrayListArtWorkIdFavDesk, BuyerBean buy, ArtworkBean artWorkBean, ArtistBean artistBean) throws SQLException, IOException {

                        imageView.setImage(extractImage(artWorkBean.getImmagine()));
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);

                        labelArtWorkNameFavDesk.setText(artWorkBean.getTitolo());
                        labelArtWorkNameFavDesk.setAlignment(Pos.CENTER);
                        labelArtWorkNameFavDesk.setStyle("-fx-text-fill: #39A67F; -fx-font-weight: bold ");

                        labelArtistNameFavDesk.setText(artistBean.getNome()+" "+artistBean.getCognome());
                        labelArtistNameFavDesk.setAlignment(Pos.CENTER);
                        labelArtistNameFavDesk.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");

                        prezzoFavDesk.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #39A67F;");
                        prezzoFavDesk.setMaxWidth(Double.MAX_VALUE);
                        prezzoFavDesk.setText("€ " + artWorkBean.getPrezzo());
                        prezzoFavDesk.setAlignment(Pos.CENTER);

                        VBox vBox1 = new VBox(labelArtWorkNameFavDesk, labelArtistNameFavDesk, prezzoFavDesk);
                        vBox1.setAlignment(Pos.CENTER);vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");

                        HBox.setHgrow(vBox1, Priority.ALWAYS);

                        buttonAcquistaFavDesk.setText("Acquista");
                        buttonAcquistaFavDesk.setPrefSize(170, 50);
                        buttonAcquistaFavDesk.setStyle(" -fx-font-size: 14px;");
                        buttonPreferitiFavDesk.setText("Aggiungi ai Preferiti");
                        buttonPreferitiFavDesk.setPrefSize(170, 50);
                        buttonPreferitiFavDesk.setStyle("-fx-font-size: 14px;");

                        VBox vBox = new VBox(buttonAcquistaFavDesk, buttonPreferitiFavDesk);
                        vBox.setAlignment(Pos.CENTER);
                        ViewFavouritesBuyer lv = new ViewFavouritesBuyer();

                        buttonAcquistaFavDesk.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent arg0) {
                                        buttonPreferitiFavDesk.setText("Paga con Carte");
                                        buttonAcquistaFavDesk.setText("Paga con PayPal");
                                        buttonAcquistaFavDesk.setOnAction(new EventHandler<ActionEvent>() {

                                                @Override
                                                public void handle(ActionEvent arg0) {
                                                        try {
                                                                lv.finishPayment( artWorkBean, buy,artistBean);
                                                        } catch (BuyArtworkManagementProblemException | FavouritesManagementProblemException e) {
                                                                e.printStackTrace();
                                                        }
                                                        buttonAcquistaFavDesk.setDisable(true);
                                                        buttonPreferitiFavDesk.setVisible(false);
                                                        buttonAcquistaFavDesk.setText("Opera Acquistata!");

                                                }
                                        });
                                        buttonPreferitiFavDesk.setOnAction(new EventHandler<ActionEvent>() {

                                                @Override
                                                public void handle(ActionEvent arg0) {
                                                        try {
                                                                lv.finishPayment( artWorkBean, buy,artistBean);
                                                        } catch (BuyArtworkManagementProblemException | FavouritesManagementProblemException e) {
                                                                e.printStackTrace();
                                                        }
                                                        buttonAcquistaFavDesk.setDisable(true);
                                                        buttonPreferitiFavDesk.setVisible(false);
                                                        buttonAcquistaFavDesk.setText("Opera Acquistata!");
                                                }
                                        });
                                }

                        });
                for(ArtworkBean aw : arrayListArtWorkIdFavDesk) {
                        if (aw.getIdOpera()==artWorkBean.getIdOpera()) {
                                buttonPreferitiFavDesk.setText("Rimuovi dai Preferiti");
                        }
                }
                buttonPreferitiFavDesk.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                                String prefString = buttonPreferitiFavDesk.getText();
                                String answer = null;
                                try {
                                        answer = lv.manageButtonClick(prefString,artWorkBean,buy);
                                } catch (FavouritesManagementProblemException e) {
                                        e.printStackTrace();
                                }
                                buttonPreferitiFavDesk.setText(answer);

                                if(answer.equals("Aggiungi ai Preferiti")){
                                        SceneController sc = new SceneController();
                                        try {
                                                sc.switchToSceneFavouritesBuyer(arg0, buy);
                                        } catch (IOException | SQLException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }
                });


                this.getChildren().addAll(imageView, vBox1, vBox);
        }
        }


        public void populateListView() throws SQLException, IOException {
                ViewFavouritesBuyer vfb = new ViewFavouritesBuyer();
                List<ArtworkBean> arrayOfArtWorkIdFav;
                ArtistBean artistFav=null;
                ArtworkBean artWorkFav = null;

                try{
                        arrayOfArtWorkIdFav = vfb.retrieveArtWorkId(buy);
                        for (ArtworkBean i: arrayOfArtWorkIdFav) {
                                artWorkFav = vfb.retrieveArtWork(i.getIdOpera());
                                artistFav = vfb.retrieveArtistName(artWorkFav);

                                listView.getItems().add(new HBoxCell(arrayOfArtWorkIdFav, buy, artWorkFav,artistFav));

                        }


                }catch (ArtworkNotFoundException e) {
                paneException.setVisible(true);
                }
        }

        private Image extractImage(Blob blob3){
                InputStream inputStream3 = null;
                try {
                        inputStream3 = blob3.getBinaryStream();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                assert inputStream3 != null;
                return new Image(inputStream3, 100, 100, true, false);

        }

}
