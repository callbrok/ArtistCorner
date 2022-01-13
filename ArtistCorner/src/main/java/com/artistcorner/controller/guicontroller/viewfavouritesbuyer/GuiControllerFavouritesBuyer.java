package com.artistcorner.controller.guicontroller.viewfavouritesbuyer;

import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Buyer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerFavouritesBuyer {

        @FXML
        public AnchorPane anchorParent;
        public AnchorPane anchorPane1;
        public ImageView artWorkImg1;
        public Pane paneFound1;
        public Label labelLogOut;
        public ListView<String> listView;
        public Button button1;
        public Button button2;
        public Button button3;
        public Button payPal;
        public Button gPay;
        public Button applePay;
        public ImageView applePayImg;
        public ImageView gPayImg;
        public ImageView payPalImg;
        public Label labelCheckoutCompleted;
        public Label labelArtWork1;
        public Label labelArtist1;
        public Label labelIdOpera1;
        public Label labelIdArtist1;
        public Label labelPrice1;
        public Button buttonAcquista1;
        public Button buttonRemFavourites1;
        private double x=0, y=0;
        Stage stage;
        Buyer buy;


        public void initialize() throws SQLException {
                makeDraggable();
                setTooltipMenu();
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
                initializeListView();
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
        public void setTooltipMenu(){
                button1.setTooltip(new Tooltip("Home"));
                button2.setTooltip(new Tooltip("Cerca Opera"));
                button3.setTooltip(new Tooltip("Preferiti"));
        }

        private void initializeListView() {
                ArrayList<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
                ObservableList<String> item = FXCollections.observableArrayList();
                ArrayList<Double> priceList = new ArrayList<>();
                for (int id1 : artWorkId) {  
                        ArtWork query = BuyerDAO.retrieveArtWorks(id1);
                        String artWorkName = query.getTitolo();
                        priceList.add(query.getPrezzo());
                        listView.setItems(item);
                        item.add(artWorkName);
                }
                listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                        @Override
                        public void changed(ObservableValue observableValue, Object o, Object t1) {
                                int index = listView.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.
                                int artistId = BuyerDAO.retrieveArtistId(artWorkId.get(index));
                                String artistName = BuyerDAO.retrieveArtistName(artistId);
                                Blob immagine = BuyerDAO.retrieveImage(artWorkId.get(index));
                                InputStream inputStream = null;
                                try {
                                        inputStream = immagine.getBinaryStream();
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                                Image image1 = new Image(inputStream, 100, 100, true, false);
                                artWorkImg1.setImage(image1);labelArtWork1.setText(item.get(index));labelArtist1.setText(artistName);
                                labelIdArtist1.setText(Integer.toString(artistId));labelIdOpera1.setText(Integer.toString(artWorkId.get(index)));
                                buttonAcquista1.setText("Acquista per €"+ priceList.get(index));
                                labelPrice1.setText(Double.toString(priceList.get(index)));
                                paneFound1.setVisible(true);
                        }
                });

        }
        public void removeArtWorkFavourites() throws SQLException {
                BuyerDAO.removeArtWorkFromFavourites(Integer.parseInt(labelIdOpera1.getText()),buy.getIdBuyer());
                buttonRemFavourites1.setText("Rimosso dai Preferiti");
                initializeListView();
        }
        public void compraArtWork() {
                buttonAcquista1.setVisible(false);buttonRemFavourites1.setVisible(false);
                payPal.setVisible(true);gPay.setVisible(true);applePay.setVisible(true);

        }
        public void buttonPayOnClick() throws SQLException {
                BuyerDAO.addArtWorkComprata(Integer.parseInt(labelIdOpera1.getText()),buy.getIdBuyer());
                BuyerDAO.switchFlagVendibile(Integer.parseInt(labelIdOpera1.getText()));
                BuyerDAO.removeArtWorkFromFavourites(Integer.parseInt(labelIdOpera1.getText()),buy.getIdBuyer());
                payPal.setVisible(false);gPay.setVisible(false);applePay.setVisible(false);
                labelCheckoutCompleted.setVisible(true);
        }
        public void switchToBuyerSummary(ActionEvent actionEvent) throws IOException {
                SceneController sc = new SceneController();
                sc.switchToSceneBuyerSummary(actionEvent,buy);
        }

        public void switchToSearchArtWorkBuyer(MouseEvent mouseEvent) throws IOException {
                SceneController sc = new SceneController();
                sc.switchToSceneSearchArtWorkBuyer(mouseEvent,buy);
        }

}
