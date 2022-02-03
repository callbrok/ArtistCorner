package com.artistcorner.controller.guicontroller.viewsearchartworkbuyer;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkBuyer;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtWorkManagementProblemException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;


public class GuiControllerViewSearchArtWorkBuyer {
    @FXML
    private ToggleButton toglleCat1;
    @FXML
    private ToggleButton toglleCat2;
    @FXML
    private ToggleButton toglleCat3;
    @FXML
    private AnchorPane anchorParentSearchBuy;
    @FXML
    private AnchorPane anchorPane;
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
            SceneController sc = new SceneController();
            try {
                sc.switchToLogin(event);
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
        toglleCat1.setToggleGroup(toggleGroup);
        toglleCat2.setToggleGroup(toggleGroup);
        toglleCat3.setToggleGroup(toggleGroup);

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
        SceneController sc = new SceneController();
        sc.switchToSceneBuyerSummary(actionEvent,buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneFavouritesBuyer(actionEvent,buy);
    }

    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException{
        if(keyEvent.getCode()== KeyCode.ENTER){
            String input= textField.getText();
            anchorPane.setVisible(true);
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

    public static class HBoxCell extends HBox {
        Label labelArtWorkNameSearchBuy = new Label();
        Label labelArtistNameSearchBuy = new Label();
        Button buttonAcquistaSearchBuy = new Button();
        Button buttonPreferitiSearchBuy = new Button();
        Label prezzoSearch = new Label();

        public HBoxCell(String artWorkNameText, String artistNameText, Image imageArt, int idOperaSearch, double priceDisplay, String preferitiBuyerText, int idBuyer,int idArtista, List<Integer> arrayListArtWorkIdFav,String inputSearchBuy, BuyerBean buy, ArtWorkBean artWorkBean) throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(imageArt);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            labelArtWorkNameSearchBuy.setText(artWorkNameText);
            labelArtWorkNameSearchBuy.setStyle("-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistNameSearchBuy.setText(artistNameText);
            labelArtistNameSearchBuy.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");

            prezzoSearch.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #39A67F;");
            prezzoSearch.setMaxWidth(Double.MAX_VALUE);
            prezzoSearch.setText(Double.toString(priceDisplay) + " €");
            prezzoSearch.setAlignment(Pos.CENTER);


            VBox vBox1 = new VBox(labelArtWorkNameSearchBuy, labelArtistNameSearchBuy, prezzoSearch);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");

            HBox.setHgrow(vBox1, Priority.ALWAYS);


            buttonAcquistaSearchBuy.setText("Acquista");
            buttonAcquistaSearchBuy.setPrefSize(170, 50);
            buttonAcquistaSearchBuy.setStyle(" -fx-font-size: 14px;");
            buttonPreferitiSearchBuy.setText(preferitiBuyerText);
            buttonPreferitiSearchBuy.setPrefSize(170, 50);
            buttonPreferitiSearchBuy.setStyle("-fx-font-size: 14px;");

            if(buy.getIdBuyer() == 13){buttonAcquistaSearchBuy.setDisable(true);}
            if(buy.getIdBuyer() == 13){buttonPreferitiSearchBuy.setDisable(true);}

            VBox vBox = new VBox(buttonAcquistaSearchBuy, buttonPreferitiSearchBuy);
            vBox.setAlignment(Pos.CENTER);
            ViewSearchArtWorkBuyer sa = new ViewSearchArtWorkBuyer();

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
                            } catch (FavouritesManagementProblemException | BuyArtWorkManagementProblemException e) {
                                e.printStackTrace();
                            }
                            buttonAcquistaSearchBuy.setDisable(true);
                            buttonPreferitiSearchBuy.setVisible(false);

                            Dialog<String> dialog = new Dialog<>();
                            ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.setTitle("Pagamento");
                            dialog.setHeaderText(null);
                            dialog.setContentText("Pagamento con Paypal");

                            dialog.showAndWait();
                            buttonAcquistaSearchBuy.setText("Opera Acquistata!");
                        }
                    });

                    buttonPreferitiSearchBuy.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {

                            try {
                                sa.finishPayment( artWorkBean, buy);
                            } catch (FavouritesManagementProblemException | BuyArtWorkManagementProblemException e) {
                                e.printStackTrace();
                            }
                            buttonAcquistaSearchBuy.setDisable(true);
                            buttonPreferitiSearchBuy.setVisible(false);

                            Dialog<String> dialog = new Dialog<>();
                            ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.setTitle("Pagamento");
                            dialog.setHeaderText(null);
                            dialog.setContentText("Pagamento con Carta");

                            dialog.showAndWait();
                            buttonAcquistaSearchBuy.setText("Opera Acquistata!");
                        }
                    });
                }

            });



            if (arrayListArtWorkIdFav.contains(idOperaSearch)){
                buttonPreferitiSearchBuy.setText("Rimuovi dai Preferiti");
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


            this.getChildren().addAll(imageView, vBox1, vBox);
        }

    }
    public void populateListView(String input) throws SQLException, IOException {
        ArtWorkBean artToSearch = new ArtWorkBean();

        paneExceptionLoad.setVisible(false);

        if(toglleCat1.isSelected()){category = "impressionista";}
        if(toglleCat2.isSelected()){category = "espressionista";}
        if(toglleCat3.isSelected()){category = "stilizzato";}

        if (listView.getItems().size()!=0){
              listView.getItems().clear();
        }

        ViewSearchArtWorkBuyer vsb = new ViewSearchArtWorkBuyer();
        List<Integer> arrayOfArtWorkIdSearchBuy;
        ArtistBean artistSearchBuy=null;

        artToSearch.setCategoria(category);
        artToSearch.setTitolo(input);

        try{
            //System.out.println(input + " --- " + category);
            List<ArtWorkBean> arrayOfArtWorkFound = vsb.retrieveSearchArtWorkByName(artToSearch);
            arrayOfArtWorkIdSearchBuy = vsb.retrieveSearchArtWorkId(buy);

            for (ArtWorkBean artWorkFound: arrayOfArtWorkFound) {
                artistSearchBuy = vsb.retrieveSearchArtistName(artWorkFound);
                Image image1 = extractImage(artWorkFound.getImmagine());
                listView.getItems().add(new HBoxCell(artWorkFound.getTitolo(), artistSearchBuy.getNome()+" "+artistSearchBuy.getCognome(),image1, artWorkFound.getIdOpera(), artWorkFound.getPrezzo(),"Aggiungi ai Preferiti", buy.getIdBuyer(), artistSearchBuy.getIdArtista(),arrayOfArtWorkIdSearchBuy,input,buy,artWorkFound));

            }

        } catch ( ArtWorkNotFoundException throwables) {
            paneExceptionLoad.setVisible(true);
        }finally {
            category="";
        }
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

}
