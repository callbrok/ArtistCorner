package com.artistcorner.controller.guicontroller.findartwork;

import com.artistcorner.controller.applicationcontroller.FindArtwork;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GuiControllerFindArtwork {
    @FXML
    private ToggleButton toggleImpBuy;
    @FXML
    private ToggleButton toggleEspBuy;
    @FXML
    private ToggleButton toggleStilBuy;
    @FXML
    private AnchorPane anchorParentSearchBuy;
    @FXML
    private AnchorPane anchorPaneFocus;
    @FXML
    private ImageView imageFocused;
    @FXML
    private Label labelLogOutBuyerSearch;
    @FXML
    private ListView<HBoxCell> listView;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private TextField textField;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private SVGPath svgProfileBuyerSearch;
    @FXML
    private Pane paneExceptionLoad;
    private Stage stageSearchBuy;
    private double x=0;
    private double y=0;
    private String category = "";
    BuyerBean buy;



    public void makeLogOutBuyer(){
        labelLogOutBuyerSearch.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                SceneController.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    public void initialize() throws SQLException {
        makeDraggable();
        setTooltipMenu();
        makeLogOutBuyer();

        paneExceptionLoad.setVisible(false);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleImpBuy.setToggleGroup(toggleGroup);
        toggleEspBuy.setToggleGroup(toggleGroup);
        toggleStilBuy.setToggleGroup(toggleGroup);

        svgProfileBuyerSearch.setScaleX(0.07);
        svgProfileBuyerSearch.setScaleY(0.07);
    }

    public void getBuyer(BuyerBean loggedBuyer) {
        buy = loggedBuyer;

        if(buy.getIdBuyer() == 13){initGuest();}

        labelUsernameDisplay.setText(buy.getNome()+" "+buy.getCognome());
    }

    private void makeDraggable(){
        anchorParentSearchBuy.setOnMousePressed((eventSearchBuy -> {
            x=eventSearchBuy.getSceneX();
            y= eventSearchBuy.getSceneY();
        }));

        anchorParentSearchBuy.setOnMouseDragged((eventSearchBuy -> {
            stageSearchBuy = (Stage) ((Node)eventSearchBuy.getSource()).getScene().getWindow();
            stageSearchBuy.setX(eventSearchBuy.getScreenX() - x);
            stageSearchBuy.setY(eventSearchBuy.getScreenY() - y);
        }));
    }

    public void initGuest(){
        button3.setDisable(true);
        labelLogOutBuyerSearch.setText("ACCEDI");
    }

    public void minimizeWindow() {
        stageSearchBuy = (Stage) anchorParentSearchBuy.getScene().getWindow();
        stageSearchBuy.setIconified(true);
    }

    public void exitWindow() {
        stageSearchBuy = (Stage) anchorParentSearchBuy.getScene().getWindow();
        stageSearchBuy.close();
    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Cerca Opera"));
        button3.setTooltip(new Tooltip("Preferiti"));
    }

    public void switchToBuyerSummary(ActionEvent actionEvent) throws IOException{
        SceneController.switchToSceneBuyerSummary(actionEvent,buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController.switchToSceneFavouritesBuyer(actionEvent,buy);
    }

    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException{
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

    public class HBoxCell extends HBox {
        Label labelArtWorkNameSearchBuy = new Label();
        Label labelArtistNameSearchBuy = new Label();
        Button buttonAcquistaSearchBuy = new Button();
        Button buttonPreferitiSearchBuy = new Button();
        Label prezzoSearch = new Label();

        public HBoxCell(List<ArtworkBean> arrayListArtWorkIdFav, BuyerBean buy, ArtworkBean artWorkBean, ArtistBean artistBean) throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(extractImage(artWorkBean.getImmagine()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            HBox hBox_border = new HBox(imageView);  // Imposta bordo all'immagine tramite un HBox
            hBox_border.setMinWidth(100);
            hBox_border.setMinHeight(100);
            hBox_border.getStyleClass().add("hBoxBorderMAB");

            labelArtWorkNameSearchBuy.setText(artWorkBean.getTitolo());
            labelArtWorkNameSearchBuy.setStyle("-fx-text-fill: #22634c; -fx-font-weight: bold ");
            labelArtistNameSearchBuy.setText(artistBean.getNome()+" "+artistBean.getCognome());
            labelArtistNameSearchBuy.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");

            prezzoSearch.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #d13e0a;");
            prezzoSearch.setMaxWidth(Double.MAX_VALUE);
            prezzoSearch.setText("€ " + artWorkBean.getPrezzo());
            prezzoSearch.setAlignment(Pos.CENTER);


            VBox vBox1 = new VBox(labelArtWorkNameSearchBuy, labelArtistNameSearchBuy, prezzoSearch);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");

            HBox.setHgrow(vBox1, Priority.ALWAYS);


            buttonAcquistaSearchBuy.setText("Acquista");
            buttonAcquistaSearchBuy.setPrefSize(170, 50);
            buttonAcquistaSearchBuy.setStyle(" -fx-font-size: 14px;");
            buttonPreferitiSearchBuy.setText("Aggiungi ai Preferiti");
            buttonPreferitiSearchBuy.setPrefSize(170, 50);
            buttonPreferitiSearchBuy.setStyle("-fx-font-size: 14px;");

            if(buy.getIdBuyer() == 13){buttonAcquistaSearchBuy.setDisable(true);}
            if(buy.getIdBuyer() == 13){buttonPreferitiSearchBuy.setDisable(true);}

            VBox vBox = new VBox(buttonAcquistaSearchBuy, buttonPreferitiSearchBuy);
            vBox.setAlignment(Pos.CENTER);
            FindArtwork sa = new FindArtwork();

            buttonAcquistaSearchBuy.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    buttonPreferitiSearchBuy.setText("Paga con Carte");
                    buttonAcquistaSearchBuy.setText("Paga con PayPal");

                    buttonAcquistaSearchBuy.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            try {
                                sa.finishPayment( artWorkBean, buy);
                            } catch (FavouritesManagementProblemException | BuyArtworkManagementProblemException e) {
                                e.printStackTrace();
                            }
                            buttonAcquistaSearchBuy.setDisable(true);
                            buttonPreferitiSearchBuy.setVisible(false);

                            Dialog<String> dialogButAcq = new Dialog<>();
                            ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
                            dialogButAcq.getDialogPane().getButtonTypes().add(type);

                            dialogButAcq.setTitle("Pagamento");
                            dialogButAcq.setHeaderText(null);
                            dialogButAcq.setContentText("Pagamento con Paypal");

                            dialogButAcq.showAndWait();
                            buttonAcquistaSearchBuy.setText("Opera Acquistata!");
                        }
                    });

                    buttonPreferitiSearchBuy.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {

                            try {
                                sa.finishPayment( artWorkBean, buy);
                            } catch (FavouritesManagementProblemException | BuyArtworkManagementProblemException e) {
                                e.printStackTrace();
                            }
                            buttonAcquistaSearchBuy.setDisable(true);
                            buttonPreferitiSearchBuy.setVisible(false);

                            Dialog<String> dialogButPref = new Dialog<>();
                            ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
                            dialogButPref.getDialogPane().getButtonTypes().add(type);

                            dialogButPref.setTitle("Pagamento");
                            dialogButPref.setHeaderText(null);
                            dialogButPref.setContentText("Pagamento con Carta");

                            dialogButPref.showAndWait();
                            buttonAcquistaSearchBuy.setText("Opera Acquistata!");
                        }
                    });
                }

            });


            for(ArtworkBean artW : arrayListArtWorkIdFav) {
                if (artW.getIdOpera()==artWorkBean.getIdOpera()){
                    buttonPreferitiSearchBuy.setText("Rimuovi dai Preferiti");
                }
            }
            buttonPreferitiSearchBuy.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = null;
                    try {
                        answer = sa.manageButtonClickFavourites(buttonPreferitiSearchBuy.getText(),artWorkBean,buy);
                    } catch (FavouritesManagementProblemException e) {
                        e.printStackTrace();
                    }
                    buttonPreferitiSearchBuy.setText(answer);
                }
            });

            EventHandler<MouseEvent> mouseHandler = tD -> {    // Crea un EventHandler sull'imageView all'interno del tilePane.
                ImageView imageView2 = (ImageView) tD.getSource();  // Prende l'imageView collegata all'evento.

                imageFocused.setImage(imageView2.getImage());   // Setta l'immagine e la rende focused.
                centerImage(imageFocused);                     // Centra l'immagine.
                anchorPaneFocus.setVisible(true);
            };

            anchorPaneFocus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> anchorPaneFocus.setVisible(false));

            imageView.setOnMouseClicked(mouseHandler);
            this.getChildren().addAll(hBox_border, vBox1, vBox);
        }
        private Image extractImage(Blob blob4){
            InputStream inputStream4 = null;
            try {
                inputStream4 = blob4.getBinaryStream();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            assert inputStream4 != null;
            return new Image(inputStream4, 100, 100, true, false);

        }
        public void centerImage(ImageView imageView) {
            Image img = imageView.getImage();
            if (img != null) {
                double width = 0;
                double height = 0;

                double ratX = imageView.getFitWidth() / img.getWidth();
                double ratY = imageView.getFitHeight() / img.getHeight();

                double reducCoeff = 0;
                if(ratX >= ratY) {
                    reducCoeff = ratY;
                } else {
                    reducCoeff = ratX;
                }

                width = img.getWidth() * reducCoeff;
                height = img.getHeight() * reducCoeff;

                imageView.setX((imageView.getFitWidth() - width) / 2);
                imageView.setY((imageView.getFitHeight() - height) / 2);

            }
        }

    }
    public void populateListView(String input) throws SQLException, IOException {
        ArtworkBean artToSearch = new ArtworkBean();

        paneExceptionLoad.setVisible(false);

        if(toggleImpBuy.isSelected()){category = "impressionista";}
        if(toggleEspBuy.isSelected()){category = "espressionista";}
        if(toggleStilBuy.isSelected()){category = "stilizzato";}

        if (listView.getItems().size()!=0){
              listView.getItems().clear();
        }
        FindArtwork vsb = new FindArtwork();
        List<ArtworkBean> arrayOfArtWorkIdSearchBuy = new ArrayList<>();
        ArtistBean artistSearchBuy=null;

        artToSearch.setCategoria(category);
        artToSearch.setTitolo(input);

        try{
            List<ArtworkBean> arrayOfArtWorkFound = vsb.retrieveSearchArtWorkByName(artToSearch);
            if(buy.getIdBuyer() != 13){
            arrayOfArtWorkIdSearchBuy = vsb.retrieveSearchArtWorkId(buy);
            }
            for (ArtworkBean artWorkFound: arrayOfArtWorkFound) {
                artistSearchBuy = vsb.retrieveSearchArtistName(artWorkFound);
                listView.getItems().add(new HBoxCell(arrayOfArtWorkIdSearchBuy,buy,artWorkFound,artistSearchBuy));

            }

        } catch ( ArtworkNotFoundException throwables) {
            paneExceptionLoad.setVisible(true);
        }finally {
            category="";
        }
    }



}
