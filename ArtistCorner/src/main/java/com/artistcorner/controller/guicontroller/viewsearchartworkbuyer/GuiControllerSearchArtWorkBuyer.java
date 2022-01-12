package com.artistcorner.controller.guicontroller.viewsearchartworkbuyer;

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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerSearchArtWorkBuyer {
    @FXML
    private AnchorPane anchorParent,anchorPane;
    public Pane paneFound;
    public Label labelLogOut;
    public ListView<String> listView;
    public TextField textField;
    public Button buttonSearch;
    public Button buttonCanc;
    public ImageView artWorkImg;
    public Button button1;
    public Button button2;
    public Button button3;
    public Label labelArtWork;
    public Label labelArtist;
    public Label labelIdArtWork;
    public Label labelIdArtist;
    public Label labelPrice;
    public Button buttonAcquista;
    public Button buttonFavourites;
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

    public void switchToBuyerSummary(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneBuyerSummary(actionEvent,buy);
    }

    public void switchToFavouritesBuyer(MouseEvent mouseEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneFavouritesBuyer(mouseEvent,buy);
    }
    public void compraArtWork() throws SQLException {
        BuyerDAO.addArtWorkComprata(Integer.parseInt(labelIdArtWork.getText()),buy.getIdBuyer());
        BuyerDAO.switchFlagVendibile(Integer.parseInt(labelIdArtWork.getText()));
        BuyerDAO.removeArtWorkFromFavourites(Integer.parseInt(labelIdArtWork.getText()),buy.getIdBuyer());
        buttonAcquista.setText("Opera Acquistata !");

    }
    public void addArtWorkFavourites() throws SQLException {
        BuyerDAO.addArtWorkToFavourites(Integer.parseInt(labelIdArtWork.getText()),buy.getIdBuyer());
        buttonFavourites.setText(" Aggiunto ai Preferiti");
    }
    public void enterSearch(KeyEvent keyEvent){
        if(keyEvent.getCode()== KeyCode.ENTER){
            String input= textField.getText();
            anchorPane.setVisible(true);
            initializeListView(input);
        }
    }
    public void buttonSearchOnClick(){
        String input= textField.getText();
        anchorPane.setVisible(true);
        initializeListView(input);
    }
    public void buttonCancOnClick(){
        textField.setText("");
    }

    private void initializeListView(String input) {
        ArrayList<ArtWork> artWorkList = BuyerDAO.retrieveArtWorkByName(input);
        ObservableList<String> item = FXCollections.observableArrayList();
        ArrayList<String> artWorkNameList = new ArrayList<>();
        ArrayList<Integer> artistIdList = new ArrayList<>();
        ArrayList<String> artistNameList = new ArrayList<>();
        ArrayList<Integer> artWorkIdList = new ArrayList<>();
        ArrayList<Blob> imgList = new ArrayList<>();
        ArrayList<Double> priceList = new ArrayList<>();
        for (ArtWork artWork : artWorkList) {
            String artWorkName = artWork.getTitolo();
            artWorkNameList.add(artWorkName);
            int artWorkId = artWork.getIdOpera();
            artWorkIdList.add(artWorkId);
            double artWorkPrice = artWork.getPrezzo();
            priceList.add(artWorkPrice);
            listView.setItems(item);
            item.add(artWorkName);
        }
        System.out.println(artWorkIdList);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                int index = listView.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.
                Blob immagine = BuyerDAO.retrieveImage(artWorkIdList.get(index));
                int artistId = BuyerDAO.retrieveArtistId(artWorkIdList.get(index));
                String artistName = BuyerDAO.retrieveArtistName(artistId);
                InputStream inputStream = null;
                try {
                    inputStream = immagine.getBinaryStream();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                paneFound.setVisible(true);
                Image image = new Image(inputStream, 100, 100, true, false);
                artWorkImg.setImage(image);labelArtWork.setText(item.get(index));labelArtist.setText(artistName);
                labelIdArtist.setText(Integer.toString(artistId));labelIdArtWork.setText(Integer.toString(artWorkIdList.get(index)));
                buttonAcquista.setText("Acquista per €"+ priceList.get(index));buttonFavourites.setText("Aggiungi ai Preferiti");
                labelPrice.setText(Double.toString(priceList.get(index)));

            }
        });

    }
}
