package com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkBuyer;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtWorkManagementProblemException;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileViewSearchArtWorkBuyer {
    @FXML
    private Button buttonFavourite;
    @FXML
    private AnchorPane anchorMainMobBuySearch;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private ListView<HBoxCellMobile> listView;
    @FXML
    private ToggleButton toglleCat1,toglleCat2,toglleCat3;
    @FXML
    private Pane paneExLoad;
    @FXML
    private Stage stageMobBuySearch;
    @FXML
    private TextField textField;

    private double x=0;
    private double y=0;
    private String category = "";
    BuyerBean buy;

    public void initialize(){
        makeDraggable();
        ToggleGroup toggleGroup = new ToggleGroup();
        toglleCat1.setToggleGroup(toggleGroup);
        toglleCat2.setToggleGroup(toggleGroup);
        toglleCat3.setToggleGroup(toggleGroup);

    }


    public void getBuyer(BuyerBean loggedBuyer){
        buy = loggedBuyer;      // Prendo le informazioni riguardanti l'acquirente che ha effettuato il login.
        labelUsernameDisplay.setText(buy.getNome() + " " + buy.getCognome());

        if(buy.getIdBuyer() == 13){buttonFavourite.setDisable(true);}
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

    public class HBoxCellMobile extends HBox {
        Label labelArtWorkNameMobBuy = new Label();
        Label labelArtistNameMobBuy = new Label();
        Button buttonAcquistaMobBuy = new Button();
        Button buttonPreferitiMobBuy = new Button();
        Label prezzoMobBuy = new Label();
        ImageView imageView = new ImageView();

        public HBoxCellMobile (List<ArtWorkBean> arrayListArtWorkIdPrefMob,ArtWorkBean artWoBea, ArtistBean artB, BuyerBean buy) {
            imageView.setImage(extractImage(artWoBea.getImmagine()));
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);

            labelArtWorkNameMobBuy.setText(artWoBea.getTitolo());
            labelArtWorkNameMobBuy.setTextFill(Paint.valueOf("39A67F"));

            labelArtistNameMobBuy.setText(artB.getNome()+" "+artB.getCognome());
            labelArtistNameMobBuy.setTextFill(Paint.valueOf("39A67F"));

            prezzoMobBuy.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #39A67F ");
            prezzoMobBuy.setText("€ " + artWoBea.getPrezzo());
            prezzoMobBuy.setAlignment(Pos.CENTER);
            prezzoMobBuy.setMinWidth(100);
            prezzoMobBuy.setPrefHeight(75);

            VBox vBox1 = new VBox(labelArtWorkNameMobBuy, labelArtistNameMobBuy);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setMinWidth(120);
            vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");

            HBox.setHgrow(labelArtWorkNameMobBuy, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 10, 10, 10));
            buttonAcquistaMobBuy.setText("Acquista");
            buttonAcquistaMobBuy.setPrefSize(100, 35);
            buttonAcquistaMobBuy.getStyleClass().add("buttonBuy");


            buttonPreferitiMobBuy.setText("Aggiungi ai Preferiti");
            buttonPreferitiMobBuy.setPrefSize(100, 35);
            buttonPreferitiMobBuy.getStyleClass().add("buttonBuy");

            VBox vBox = new VBox(buttonAcquistaMobBuy, buttonPreferitiMobBuy);
            vBox.setStyle(" -fx-font-size: 8px;-fx-background-radius: 0;");
            vBox.setSpacing(2.5);
            vBox.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ");
            vBox.setAlignment(Pos.CENTER);

            if(buy.getIdBuyer() == 13){buttonAcquistaMobBuy.setDisable(true);}
            if(buy.getIdBuyer() == 13){buttonPreferitiMobBuy.setDisable(true);}

            ViewSearchArtWorkBuyer sa = new ViewSearchArtWorkBuyer();
            buttonAcquistaMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    buttonPreferitiMobBuy.setText("Paga con Carte");
                    buttonAcquistaMobBuy.setText("Paga con PayPal");
                    buttonAcquistaMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            try {
                                sa.finishPayment(artWoBea, buy,artB);
                            } catch (FavouritesManagementProblemException | BuyArtWorkManagementProblemException e) {
                                e.printStackTrace();
                            }
                            buttonAcquistaMobBuy.setPrefSize(90,35);
                            buttonPreferitiMobBuy.setPrefSize(90,10);
                            buttonAcquistaMobBuy.setDisable(true);
                            buttonPreferitiMobBuy.setVisible(false);

                            Dialog<String> dialog = new Dialog<>();
                            ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.setTitle("Pagamento");
                            dialog.setHeaderText(null);
                            dialog.setContentText("Pagamento con Paypal");

                            dialog.showAndWait();
                            buttonAcquistaMobBuy.setAlignment(Pos.BOTTOM_CENTER);
                            buttonAcquistaMobBuy.setStyle("-fx-font-size: 8px;-fx-background-color: #ffffff");
                            buttonAcquistaMobBuy.setText("Opera Acquistata!");

                        }
                    });
                    buttonPreferitiMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            try {
                                sa.finishPayment(artWoBea, buy,artB);
                            } catch (FavouritesManagementProblemException | BuyArtWorkManagementProblemException e) {
                                e.printStackTrace();
                            }
                            buttonAcquistaMobBuy.setStyle("-fx-font-size: 8px;-fx-background-color: #ffffff");
                            buttonAcquistaMobBuy.setDisable(true);
                            buttonPreferitiMobBuy.setVisible(false);

                            Dialog<String> dialog = new Dialog<>();
                            ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.setTitle("Pagamento");
                            dialog.setHeaderText(null);
                            dialog.setContentText("Pagamento con Carte");

                            dialog.showAndWait();
                            buttonAcquistaMobBuy.setText("Opera Acquistata!");
                            buttonAcquistaMobBuy.setAlignment(Pos.BOTTOM_CENTER);
                            buttonAcquistaMobBuy.setPrefSize(90,35);
                            buttonPreferitiMobBuy.setPrefSize(90,10);
                        }
                    });
                }

            });
            for(ArtWorkBean art : arrayListArtWorkIdPrefMob){
            if (art.getIdOpera()==artWoBea.getIdOpera()){
                buttonPreferitiMobBuy.setText("Rimuovi dai Preferiti");
            }
            }
            buttonPreferitiMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = null;
                    try {
                        answer = sa.manageButtonClickFavourites(buttonPreferitiMobBuy.getText(),artWoBea, buy);
                    } catch (FavouritesManagementProblemException e) {
                        e.printStackTrace();
                    }
                    buttonPreferitiMobBuy.setText(answer);
                }
            });


            this.getChildren().addAll(imageView, vBox1,prezzoMobBuy, vBox);
        }

    }

    public void populateListView(String input) throws SQLException, IOException {
        ArtWorkBean artToSearch = new ArtWorkBean();

        paneExLoad.setVisible(false);

        if(toglleCat1.isSelected()){category = "impressionista";}
        if(toglleCat2.isSelected()){category = "espressionista";}
        if(toglleCat3.isSelected()){category = "stilizzato";}

        if (listView.getItems().size()!=0){
            listView =new ListView<>();
        }

        ViewSearchArtWorkBuyer vsb = new ViewSearchArtWorkBuyer();
        List<ArtWorkBean> arrayOfArtWorkId=null;
        ArtistBean artist=null;

        artToSearch.setTitolo(input);
        artToSearch.setCategoria(category);

        try{
            List<ArtWorkBean> arrayOfArtWork = vsb.retrieveSearchArtWorkByName(artToSearch);
            arrayOfArtWorkId = vsb.retrieveSearchArtWorkId(buy);

            for (ArtWorkBean artWork: arrayOfArtWork) {
                artist = vsb.retrieveSearchArtistName(artWork);
                listView.getItems().add(new HBoxCellMobile(arrayOfArtWorkId,artWork,artist,buy));
            }


        } catch ( ArtWorkNotFoundException throwables) {
            paneExLoad.setVisible(true);
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


