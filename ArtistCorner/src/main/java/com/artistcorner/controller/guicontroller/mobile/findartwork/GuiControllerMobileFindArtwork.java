package com.artistcorner.controller.guicontroller.mobile.findartwork;
import com.artistcorner.controller.applicationcontroller.FindArtwork;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtworkManagementProblemException;
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

public class GuiControllerMobileFindArtwork {
    @FXML
    private Button buttonFavourite;
    @FXML
    private AnchorPane anchorMainMobBuySearch;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private ListView<HBoxCellMobile> listView;
    @FXML
    private ToggleButton toglleCat1;
    @FXML
    private ToggleButton toglleCat2;
    @FXML
    private ToggleButton toglleCat3;
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
        SceneControllerMobile.switchToSceneBuyerSummary(actionEvent,buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile.switchToSceneFavouritesBuyer(actionEvent,buy);
    }

    public class HBoxCellMobile extends HBox {
        Label labelArtWorkNameMobBuy = new Label();
        Label labelArtistNameMobBuy = new Label();
        Label prezzoMobBuy = new Label();
        Button buttonAcquistaMobBuy = new Button();
        Button buttonPreferitiMobBuy = new Button();
        ImageView imageView = new ImageView();

        public HBoxCellMobile (List<ArtworkBean> arrayListArtWorkIdPrefMob, ArtworkBean artWoBea, ArtistBean artB, BuyerBean buy) {
            imageView.setImage(extractImage(artWoBea.getImmagine()));
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);

            HBox hBoxBorder = new HBox(imageView);  // Imposta bordo all'immagine tramite un HBox
            hBoxBorder.setMinWidth(75);
            hBoxBorder.setMinHeight(75);
            hBoxBorder.getStyleClass().add("hBoxBorderMA");

            labelArtWorkNameMobBuy.setText(artWoBea.getTitolo());
            labelArtWorkNameMobBuy.setTextFill(Paint.valueOf("22634c"));

            labelArtistNameMobBuy.setText(artB.getNome()+" "+artB.getCognome());
            labelArtistNameMobBuy.setTextFill(Paint.valueOf("39A67F"));

            prezzoMobBuy.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #d13e0a ");
            prezzoMobBuy.setText("€ " + artWoBea.getPrezzo());

            VBox vBox1 = new VBox(labelArtWorkNameMobBuy, labelArtistNameMobBuy, prezzoMobBuy);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setMinWidth(120);
            vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");

            HBox.setHgrow(vBox1, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 10, 10, 10));
            buttonAcquistaMobBuy.setText("Acquista");
            buttonAcquistaMobBuy.setPrefSize(110, 35);
            buttonAcquistaMobBuy.getStyleClass().add("buttonBuy");


            buttonPreferitiMobBuy.setText("Aggiungi ai Preferiti");
            buttonPreferitiMobBuy.setPrefSize(110, 35);
            buttonPreferitiMobBuy.getStyleClass().add("buttonBuy");

            VBox vBox = new VBox(buttonAcquistaMobBuy, buttonPreferitiMobBuy);
            vBox.setStyle(" -fx-font-size: 8px;-fx-background-radius: 0;");
            vBox.setSpacing(2.5);
            vBox.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ");
            vBox.setAlignment(Pos.CENTER);

            if(buy.getIdBuyer() == 13){buttonAcquistaMobBuy.setDisable(true);}
            if(buy.getIdBuyer() == 13){buttonPreferitiMobBuy.setDisable(true);}

            FindArtwork sa = new FindArtwork();
            buttonAcquistaMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    buttonPreferitiMobBuy.setStyle("-fx-background-color: #60b798; -fx-text-fill: #277458;");
                    buttonPreferitiMobBuy.setText("Paga con Carte");
                    buttonAcquistaMobBuy.setText("Paga con PayPal");
                    buttonAcquistaMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            try {
                                sa.finishPayment(artWoBea, buy);
                            } catch (FavouritesManagementProblemException | BuyArtworkManagementProblemException e) {
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
                                sa.finishPayment(artWoBea, buy);
                            } catch (FavouritesManagementProblemException | BuyArtworkManagementProblemException e) {
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
            checkFavourites(arrayListArtWorkIdPrefMob,artWoBea);
            buttonPreferitiMobBuy.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = null;
                    // Controlla se nel momento del click il bottone esegue la rimozione dei preferiti oppure l'aggiunta.
                    boolean checkRemButton = buttonPreferitiMobBuy.getText().equals("Rimuovi dai Preferiti");
                    boolean checkAddButton = buttonPreferitiMobBuy.getText().equals("Aggiungi ai Preferiti");

                    try {
                        answer = sa.manageButtonClickFavourites(buttonPreferitiMobBuy.getText(),artWoBea, buy);

                        // Dopo la rimozione setta il bottone.
                        if(checkRemButton){buttonPreferitiMobBuy.setStyle("-fx-background-color: #60b798; -fx-text-fill: #277458;");}
                        if(checkAddButton){buttonPreferitiMobBuy.getStyleClass().add("buttonRemoveFav");}

                    } catch (FavouritesManagementProblemException e) {
                        e.printStackTrace();
                    }

                    buttonPreferitiMobBuy.setText(answer);
                }
            });


            this.getChildren().addAll(hBoxBorder, vBox1, vBox);
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
        public void checkFavourites(List<ArtworkBean> arrayListArtWorkIdPrefMob, ArtworkBean artworkBean){
            for(ArtworkBean art : arrayListArtWorkIdPrefMob){
                if (art.getIdOpera()==artworkBean.getIdOpera()){
                    buttonPreferitiMobBuy.setText("Rimuovi dai Preferiti");
                    buttonPreferitiMobBuy.getStyleClass().add("buttonRemoveFav");
                }
            }
        }

    }

    public void populateListView(String input) throws SQLException, IOException {
        ArtworkBean artToSearch = new ArtworkBean();

        paneExLoad.setVisible(false);

        if(toglleCat1.isSelected()){category = "impressionista";}
        if(toglleCat2.isSelected()){category = "espressionista";}
        if(toglleCat3.isSelected()){category = "stilizzato";}

        if (listView.getItems().size()!=0){
            listView =new ListView<>();
        }

        FindArtwork vsb = new FindArtwork();
        List<ArtworkBean> arrayOfArtWorkId=null;
        ArtistBean artist=null;

        artToSearch.setTitolo(input);
        artToSearch.setCategoria(category);

        try{
            List<ArtworkBean> arrayOfArtWork = vsb.retrieveSearchArtWorkByName(artToSearch);
            arrayOfArtWorkId = vsb.retrieveSearchArtWorkId(buy);

            for (ArtworkBean artWork: arrayOfArtWork) {
                artist = vsb.retrieveSearchArtistName(artWork);
                listView.getItems().add(new HBoxCellMobile(arrayOfArtWorkId,artWork,artist,buy));
            }


        } catch ( ArtworkNotFoundException throwables) {
            paneExLoad.setVisible(true);
        }
    }

}


