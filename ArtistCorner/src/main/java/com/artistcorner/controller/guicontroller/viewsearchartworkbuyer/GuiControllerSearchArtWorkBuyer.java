package com.artistcorner.controller.guicontroller.viewsearchartworkbuyer;

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
import java.util.List;

public class GuiControllerSearchArtWorkBuyer {
    @FXML
    private AnchorPane anchorParent,anchorPane;
    public Pane paneFound;
    public Label labelLogOut;
    public ListView<ListViewInizializer.HBoxCell> listView;
    public TextField textField;
    public Button buttonSearch;
    public Button buttonCanc;
    public Button button1;
    public Button button2;
    public Button button3;
    public Label labelIdArtWork;
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

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneFavouritesBuyer(actionEvent,buy);
    }
    public void compraArtWork() {
   //     buttonAcquista.setVisible(false);buttonFavourites.setVisible(false);
  //      payPal.setVisible(true);gPay.setVisible(true);applePay.setVisible(true);

    }
    public void addArtWorkFavourites() throws SQLException {
        BuyerDAO.addArtWorkToFavourites(Integer.parseInt(labelIdArtWork.getText()),buy.getIdBuyer());
        buttonFavourites.setText(" Aggiunto ai Preferiti");
    }
    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode()== KeyCode.ENTER){
            String input= textField.getText();
            anchorPane.setVisible(true);
            initializeListView(input);
        }
    }
    public void buttonSearchOnClick() throws SQLException, IOException {
        String input= textField.getText();
        anchorPane.setVisible(true);
        paneFound.setVisible(false);
        initializeListView(input);
    }
    public void buttonCancOnClick(){
        textField.setText("");
    }

    public void initializeListView(String input) throws SQLException, IOException {
        ArrayList<ArtWork> artWorkList = BuyerDAO.retrieveArtWorkByName(input);
        ArrayList<Integer> artWorkId = BuyerDAO.retrieveArtWorkId(buy.getIdBuyer());
        List<ListViewInizializer.HBoxCell> list = new ArrayList<>();
        for (ArtWork work : artWorkList) {
            Blob immagine = BuyerDAO.retrieveImage(work.getIdOpera());
            Artist artist = BuyerDAO.retrieveArtistName(work.getArtistaId());
            InputStream inputStream = null;
            try {
                inputStream = immagine.getBinaryStream();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Image image1 = new Image(inputStream, 100, 100, true, false);
            String aW = work.getTitolo(), ar = artist.getNome()+""+artist.getCognome(),pr = String.valueOf(work.getPrezzo());
            list.add(new ListViewInizializer.HBoxCell(work.getTitolo(), artist.getNome() + " " + artist.getCognome(), work.getPrezzo(), image1, work.getIdOpera(),"Aggiungi ai Preferiti", buy.getIdBuyer(),artWorkId));
        }

        ObservableList<ListViewInizializer.HBoxCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);
    }


}
