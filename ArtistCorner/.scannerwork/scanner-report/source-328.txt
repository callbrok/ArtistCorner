package com.artistcorner.controller.guicontroller.viewfavouritesbuyer;

import com.artistcorner.controller.applicationcontroller.ViewFavouritesBuyer;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
        private double x=0;
        private double y=0;
        @FXML
        private Pane paneExceptionLoad;
        @FXML
        private Stage stageFavDesk;

        BuyerBean buy;


        public void initialize() throws SQLException {
                makeDraggable();
                setTooltipMenu();
                makeLogOutFavDesk();
                svgProfileFavDesk.setScaleX(0.07);
                svgProfileFavDesk.setScaleY(0.07);

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
        public static class HBoxCell extends HBox {
                Label labelArtWorkNameFavDesk = new Label();
                Label labelArtistNameFavDesk = new Label();
                Button buttonAcquistaFavDesk = new Button();
                Button buttonPreferitiFavDesk = new Button();
                Label prezzoFavDesk = new Label();
                Label priceDefault = new Label();
                Label labelArtistNameDefaultFav = new Label();
                Label labelArtWorkDefaultFav = new Label();

                public HBoxCell(String artWorkFavText, String artistFavText, Image imageFav, int idOperaFav, double priceFav, String preferitiText, int idBuyer, int idArtista, List<Integer> arrayListArtWorkIdFavDesk, String input) throws SQLException, IOException {
                        super();
                        ImageView imageView = new ImageView();
                        imageView.setImage(imageFav);
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        labelArtWorkNameFavDesk.setText(artWorkFavText);
                        labelArtWorkNameFavDesk.setAlignment(Pos.CENTER);
                        labelArtWorkNameFavDesk.setStyle("-fx-text-fill: #39A67F; -fx-font-weight: bold ");
                        labelArtistNameFavDesk.setText(artistFavText);
                        labelArtistNameFavDesk.setAlignment(Pos.CENTER);
                        labelArtistNameFavDesk.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");
                        labelArtWorkNameFavDesk.setPrefSize(100, 50);
                        labelArtistNameFavDesk.setPrefSize(100, 50);
                        prezzoFavDesk.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #39A67F;");
                        prezzoFavDesk.setMaxWidth(Double.MAX_VALUE);
                        prezzoFavDesk.setText("€ " + priceFav);
                        prezzoFavDesk.setPrefSize(100, 100);
                        prezzoFavDesk.setAlignment(Pos.CENTER);
                        priceDefault.setText("Prezzo Opera: ");
                        priceDefault.setPrefSize(100, 100);
                        priceDefault.setAlignment(Pos.CENTER);
                        priceDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                        VBox vBox1 = new VBox(labelArtWorkNameFavDesk, labelArtistNameFavDesk);
                        vBox1.setAlignment(Pos.CENTER);vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");
                        HBox.setHgrow(labelArtWorkNameFavDesk, Priority.ALWAYS);
                        HBox.setMargin(vBox1, new Insets(10, 25, 10, 25));
                        VBox vBox2 = new VBox(labelArtWorkDefaultFav, labelArtistNameDefaultFav);
                        vBox2.setAlignment(Pos.CENTER);
                        labelArtWorkDefaultFav.setText("Titolo Opera: ");
                        labelArtWorkDefaultFav.setAlignment(Pos.CENTER);
                        labelArtWorkDefaultFav.setPrefSize(100, 50);
                        String fontdef = "-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;";
                        labelArtWorkDefaultFav.setStyle(fontdef);
                        HBox.setMargin(vBox2, new Insets(10, 25, 10, 25));
                        labelArtistNameDefaultFav.setText("Nome Autore: ");
                        labelArtistNameDefaultFav.setAlignment(Pos.CENTER);
                        labelArtistNameDefaultFav.setPrefSize(100, 50);
                        labelArtistNameDefaultFav.setStyle(fontdef);
                        buttonAcquistaFavDesk.setText("Acquista");
                        buttonAcquistaFavDesk.setPrefSize(150, 50);
                        buttonAcquistaFavDesk.setStyle(" -fx-font-size: 14px;");
                        buttonPreferitiFavDesk.setText(preferitiText);
                        buttonPreferitiFavDesk.setPrefSize(150, 50);
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
                                                        lv.finishPayment( idOperaFav, idBuyer);
                                                        buttonAcquistaFavDesk.setDisable(true);
                                                        buttonPreferitiFavDesk.setVisible(false);
                                                        buttonAcquistaFavDesk.setText("Opera Acquistata!");

                                                }
                                        });
                                        buttonPreferitiFavDesk.setOnAction(new EventHandler<ActionEvent>() {

                                                @Override
                                                public void handle(ActionEvent arg0) {
                                                        lv.finishPayment( idOperaFav, idBuyer);
                                                        buttonAcquistaFavDesk.setDisable(true);
                                                        buttonPreferitiFavDesk.setVisible(false);
                                                        buttonAcquistaFavDesk.setText("Opera Acquistata!");
                                                }
                                        });
                                }

                        });

                if (arrayListArtWorkIdFavDesk.contains(idOperaFav)){
                        buttonPreferitiFavDesk.setText("Rimuovi dai Preferiti");
                }
                buttonPreferitiFavDesk.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                                String answer = lv.manageButtonClick(buttonPreferitiFavDesk,idOperaFav,idBuyer);
                                buttonPreferitiFavDesk.setText(answer);
                        }
                });


                this.getChildren().addAll(imageView, vBox2, vBox1, priceDefault, prezzoFavDesk, vBox);
        }


        }
        public void populateListView() throws SQLException, IOException {
                ViewFavouritesBuyer vfb = new ViewFavouritesBuyer();
                List<Integer> arrayOfArtWorkIdFav;
                ArtistBean artistFav=null;
                Blob artWorkBlobFav =null;

                try{
                        arrayOfArtWorkIdFav = vfb.retrieveArtWorkId(buy);
                        for (Integer i: arrayOfArtWorkIdFav) {
                                ArtWorkBean artWorkFav = vfb.retrieveArtWork(i);
                                artWorkBlobFav = vfb.retrieveArtWorkBlob(i);
                                artistFav = vfb.retrieveArtistName(artWorkFav);
                                Image image1 = extractImage(artWorkBlobFav);
                                listView.getItems().add(new HBoxCell(artWorkFav.getTitolo(), artistFav.getNome()+" "+artistFav.getCognome(),image1, artWorkFav.getIdOpera(), artWorkFav.getPrezzo(),"Aggiungi ai Preferiti", buy.getIdBuyer(), artistFav.getIdArtista(),arrayOfArtWorkIdFav,""));

                        }


                }catch (ArtWorkNotFoundException e) {
                ExceptionsFactory ef = ExceptionsFactory.getInstance();
                ExceptionView ev;
                ev = ef.createView(ExceptionsTypeMenager.ARTWORKNOTFOUND);
                paneExceptionLoad.getChildren().add(ev.getExceptionPane());
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
