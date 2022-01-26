package com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkBuyer;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileViewSearchArtWorkBuyer {
    @FXML
    private AnchorPane anchorMainMobBuySearch;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private ListView<HBoxCellMobile> listView;
    private double x=0;
    private double y=0;
    @FXML
    private Pane paneExceptionLoad;
    private Stage stageMobBuySearch;
    @FXML
    private TextField textField;
    BuyerBean buy;

    public void initialize(){
        makeDraggable();
    }

    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(event);
    }

    public void getBuyer(BuyerBean loggedBuyer){
        buy = loggedBuyer;      // Prendo le informazioni riguardanti l'acquirente che ha effettuato il login.
        labelUsernameDisplay.setText(buy.getNome() + " " + buy.getCognome());
    }

    public void exitWindow() {
        stageMobBuySearch = (Stage) anchorMainMobBuySearch.getScene().getWindow();
        stageMobBuySearch.close();
    }

    public void minimizeWindow() {
        stageMobBuySearch = (Stage) anchorMainMobBuySearch.getScene().getWindow();
        stageMobBuySearch.setIconified(true);
    }

    private void makeDraggable(){
        anchorMainMobBuySearch.setOnMousePressed((eventMobBuy -> {
            x=eventMobBuy.getSceneX();
            y= eventMobBuy.getSceneY();
        }));

        anchorMainMobBuySearch.setOnMouseDragged((eventMobBuy -> {
            stageMobBuySearch = (Stage) ((Node)eventMobBuy.getSource()).getScene().getWindow();
            stageMobBuySearch.setX(eventMobBuy.getScreenX() - x);
            stageMobBuySearch.setY(eventMobBuy.getScreenY() - y);
        }));
    }
    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode()== KeyCode.ENTER){
            String input= textField.getText();
            populateListView(input);
        }
    }
    public void buttonSearchOnClick() throws SQLException, IOException {
        String input= textField.getText();
        populateListView(input);
    }
    public void buttonCancOnClick(){
        textField.setText("");
    }

    public void switchToSummaryBuyer(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneBuyerSummary(actionEvent,buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneFavouritesBuyer(actionEvent,buy);
    }

    public static class HBoxCellMobile extends HBox {
        Label labelArtWorkNameMobBuy = new Label();
        Label labelArtistNameMobBuy = new Label();
        Button buttonAcquistaMobBuy = new Button();
        Button buttonPreferitiMobBuy = new Button();
        Label prezzoMobBuy = new Label();
        Label prezzoDefaultMobBuy = new Label();
        Label labelArtistNameDefaultMob = new Label();
        Label labelArtWorkDefaultMob = new Label();

        public HBoxCellMobile (String labelArtWorkMobBuy, String labelArtistMobBuy, Image imgMobBuy, int idOpera, double priceShowMob, String labelPreferitiMobBuy, int idBuyer, int idArtista, List<Integer> arrayListArtWorkIdPrefMob, String input)throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(imgMobBuy);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            labelArtWorkNameMobBuy.setText(labelArtWorkMobBuy);
            labelArtWorkNameMobBuy.setAlignment(Pos.CENTER);
            labelArtWorkNameMobBuy.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistNameMobBuy.setText(labelArtistMobBuy);
            labelArtistNameMobBuy.setAlignment(Pos.CENTER);
            labelArtistNameMobBuy.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtWorkNameMobBuy.setPrefSize(75, 25);
            labelArtistNameMobBuy.setPrefSize(75, 25);
            prezzoMobBuy.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #39A67F;");
            prezzoMobBuy.setMaxWidth(Double.MAX_VALUE);
            prezzoMobBuy.setText("€ " + Double.toString(priceShowMob));
            prezzoMobBuy.setPrefSize(50, 50);
            prezzoMobBuy.setAlignment(Pos.CENTER);
            prezzoDefaultMobBuy.setText("Prezzo Opera: ");
            prezzoDefaultMobBuy.setPrefSize(75, 50);
            prezzoDefaultMobBuy.setAlignment(Pos.CENTER);
            prezzoDefaultMobBuy.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
            VBox vBox1 = new VBox(labelArtWorkNameMobBuy, labelArtistNameMobBuy);
            vBox1.setAlignment(Pos.CENTER);vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");
            HBox.setHgrow(labelArtWorkNameMobBuy, Priority.ALWAYS);
            VBox vBox2 = new VBox(labelArtWorkDefaultMob, labelArtistNameDefaultMob);
            vBox2.setAlignment(Pos.CENTER);
            labelArtWorkDefaultMob.setText("Titolo Opera: ");
            labelArtWorkDefaultMob.setAlignment(Pos.CENTER);
            labelArtWorkDefaultMob.setPrefSize(75, 25);
            String font = "-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;";
            labelArtWorkDefaultMob.setStyle(font);
            labelArtistNameDefaultMob.setText("Nome Autore: ");
            labelArtistNameDefaultMob.setAlignment(Pos.CENTER);
            labelArtistNameDefaultMob.setPrefSize(75, 25);
            labelArtistNameDefaultMob.setStyle(font);
            buttonAcquistaMobBuy.setText("Acquista");
            buttonAcquistaMobBuy.setPrefSize(90, 25);
            buttonAcquistaMobBuy.setStyle(" -fx-font-size: 8px; -fx-background-color: #39A67F; -fx-background-radius: 0;");
            buttonPreferitiMobBuy.setText(labelPreferitiMobBuy);
            buttonPreferitiMobBuy.setPrefSize(90, 25);
            buttonPreferitiMobBuy.setStyle(" -fx-font-size: 8px; -fx-background-color: #39A67F; -fx-background-radius: 0;");
            VBox vBox = new VBox(buttonAcquistaMobBuy, buttonPreferitiMobBuy);
            vBox.setSpacing(2.5);
            vBox.setAlignment(Pos.CENTER);
            ViewSearchArtWorkBuyer sa = new ViewSearchArtWorkBuyer();
            buttonAcquistaMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    buttonPreferitiMobBuy.setText("Paga con Carte");
                    buttonAcquistaMobBuy.setText("Paga con PayPal");
                    buttonAcquistaMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            sa.finishPayment( idOpera, idBuyer);
                            buttonAcquistaMobBuy.setPrefSize(90,35);
                            buttonPreferitiMobBuy.setPrefSize(90,10);
                            buttonAcquistaMobBuy.setDisable(true);
                            buttonPreferitiMobBuy.setVisible(false);
                            buttonAcquistaMobBuy.setAlignment(Pos.BOTTOM_CENTER);
                            buttonAcquistaMobBuy.setStyle("-fx-font-size: 8px;-fx-background-color: #ffffff");
                            buttonAcquistaMobBuy.setText("Opera Acquistata!");

                        }
                    });
                    buttonPreferitiMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            sa.finishPayment( idOpera, idBuyer);
                            buttonAcquistaMobBuy.setStyle("-fx-font-size: 8px;-fx-background-color: #ffffff");
                            buttonAcquistaMobBuy.setDisable(true);
                            buttonPreferitiMobBuy.setVisible(false);
                            buttonAcquistaMobBuy.setText("Opera Acquistata!");
                            buttonAcquistaMobBuy.setAlignment(Pos.BOTTOM_CENTER);
                            buttonAcquistaMobBuy.setPrefSize(90,35);
                            buttonPreferitiMobBuy.setPrefSize(90,10);
                        }
                    });
                }

            });

            if (arrayListArtWorkIdPrefMob.contains(idOpera)){
                buttonPreferitiMobBuy.setText("Rimuovi dai Preferiti");
            }
            buttonPreferitiMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = sa.manageButtonClickFavourites(buttonPreferitiMobBuy,idOpera,idBuyer);
                    buttonPreferitiMobBuy.setText(answer);
                }
            });


            this.getChildren().addAll(imageView, vBox2, vBox1, prezzoDefaultMobBuy, prezzoMobBuy, vBox);
        }

    } public void populateListView(String input) throws SQLException, IOException {
        if (listView.getItems().size()!=0){
            listView =new ListView<>();
        }
        ViewSearchArtWorkBuyer vsb = new ViewSearchArtWorkBuyer();
        List<Integer> arrayOfArtWorkId=null;
        ArtistBean artist=null;
        Blob artWorkBlob =null;

        try{
            List<ArtWorkBean> arrayOfArtWork = vsb.retrieveSearchArtWorkByName(input);
            arrayOfArtWorkId = vsb.retrieveSearchArtWorkId(buy);
            for (ArtWorkBean artWork: arrayOfArtWork) {
                artWorkBlob = vsb.retrieveSearchArtWorkBlob(artWork.getIdOpera());
                artist = vsb.retrieveSearchArtistName(artWork);
                Image image1 = extractImage(artWorkBlob);
                listView.getItems().add(new HBoxCellMobile(artWork.getTitolo(), artist.getNome()+" "+artist.getCognome(),image1, artWork.getIdOpera(), artWork.getPrezzo(),"Aggiungi ai Preferiti", buy.getIdBuyer(), artist.getIdArtista(),arrayOfArtWorkId,input));

            }


        } catch ( ArtWorkNotFoundException throwables) {
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.ARTWORKNOTFOUND_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }
    private Image extractImage(Blob blob1){
        InputStream inputStream1 = null;
        try {
            inputStream1 = blob1.getBinaryStream();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert inputStream1 != null;
        return new Image(inputStream1, 100, 100, true, false);

    }

}


