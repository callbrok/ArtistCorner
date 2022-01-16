package com.artistcorner.controller.guicontroller.viewfavouritesbuyer;

import com.artistcorner.controller.applicationcontroller.ListViewInizializer;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerFavouritesBuyer {

        @FXML
        public AnchorPane anchorParent;
        public AnchorPane anchorPane1;
        public Label labelLogOut;
        public ListView<ListViewInizializer.HBoxCell> listView;
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
        public Label labelIdOpera1;
        private double x=0, y=0;
        Stage stage;
        public Buyer buy;


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

        public void getBuyer(Buyer loggedBuyer) throws SQLException, IOException {
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

        public void initializeListView() throws SQLException, IOException {
                ArrayList<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
                ArtWork artWork =null;
                Artist artist = null;
                List<ListViewInizializer.HBoxCell> list = new ArrayList<>();
                for (Integer integer : artWorkId) {
                        Blob immagine = BuyerDAO.retrieveImage(integer);
                        artWork = BuyerDAO.retrieveArtWorks(integer, 1);
                        artist = BuyerDAO.retrieveArtistName(artWork.getArtistaId());
                        InputStream inputStream = null;
                        try {
                                inputStream = immagine.getBinaryStream();
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }
                        Image image1 = new Image(inputStream, 100, 100, true, false);
                        list.add(new ListViewInizializer.HBoxCell(artWork.getTitolo(), artist.getNome() + " " + artist.getCognome(), artWork.getPrezzo(), image1, artWork.getIdOpera(), "Rimuovi dai Preferiti", buy.getIdBuyer(),artWorkId));
                }

                ObservableList<ListViewInizializer.HBoxCell> myObservableList = FXCollections.observableList(list);
                listView.setItems(myObservableList);

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

        public void switchToSearchArtWorkBuyer(ActionEvent actionEvent) throws IOException {
                SceneController sc = new SceneController();
                sc.switchToSceneSearchArtWorkBuyer(actionEvent,buy);
        }

}
