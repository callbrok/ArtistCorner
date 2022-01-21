package com.artistcorner.controller.guicontroller.viewsearchartworkbuyer;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkBuyer;
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
    private AnchorPane anchorParent,anchorPane;
    public Label labelLogOut;
    public ListView<HBoxCell> listView;
    public Label labelUsernameDisplay;
    public TextField textField;
    public Button buttonSearch;
    public Button buttonCanc;
    public Button button1;
    public Button button2;
    public Button button3;
    public SVGPath svgProfile;
    private double x=0, y=0;
    @FXML
    private Pane paneExceptionLoad;
    Stage stage;
    BuyerBean buy;


    public void initialize() throws SQLException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();
        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
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

    public void getBuyer(BuyerBean loggedBuyer) {
        buy = loggedBuyer;
        labelUsernameDisplay.setText(buy.getNome()+" "+buy.getCognome());
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
        ViewSearchArtWorkBuyer sa = new ViewSearchArtWorkBuyer();
        populateListView(input);
    }
    public void buttonCancOnClick(){
        textField.setText("");
    }

    public static class HBoxCell extends HBox {
        Label labelArtWorkName = new Label();
        Label labelArtistName = new Label();
        Button buttonAcquista = new Button();
        public Button buttonPreferiti = new Button();
        Label prezzo = new Label();
        Label prezzo1 = new Label();
        Label labelArtistNameDefault = new Label();
        Label labelArtWorkDefault = new Label();

        public HBoxCell(String labelText, String labelText1, Image img, int idOpera, double price, String labelPreferiti, int idBuyer,int idArtista, List<Integer> arrayListArtWorkId,String input) throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            labelArtWorkName.setText(labelText);
            labelArtWorkName.setAlignment(Pos.CENTER);
            labelArtWorkName.setStyle("-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistName.setText(labelText1);
            labelArtistName.setAlignment(Pos.CENTER);
            labelArtistName.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtWorkName.setPrefSize(100, 50);
            labelArtistName.setPrefSize(100, 50);
            prezzo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #39A67F;");
            prezzo.setMaxWidth(Double.MAX_VALUE);
            prezzo.setText("€ " + Double.toString(price));prezzo.setPrefSize(100, 100);prezzo.setAlignment(Pos.CENTER);
            prezzo1.setText("Prezzo Opera: ");prezzo1.setPrefSize(100, 100);prezzo1.setAlignment(Pos.CENTER);prezzo1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setAlignment(Pos.CENTER);vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");
            HBox.setHgrow(labelArtWorkName, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 25, 10, 25));
            VBox vBox2 = new VBox(labelArtWorkDefault, labelArtistNameDefault);
            vBox2.setAlignment(Pos.CENTER);
            labelArtWorkDefault.setText("Titolo Opera: ");labelArtWorkDefault.setAlignment(Pos.CENTER);labelArtWorkDefault.setPrefSize(100, 50);
            labelArtWorkDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            HBox.setMargin(vBox2, new Insets(10, 25, 10, 25));
            labelArtWorkDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            labelArtistNameDefault.setText("Nome Autore: ");
            labelArtistNameDefault.setAlignment(Pos.CENTER);
            labelArtistNameDefault.setPrefSize(100, 50);
            labelArtistNameDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            buttonAcquista.setText("Acquista");
            buttonAcquista.setPrefSize(150, 50);
            buttonAcquista.setStyle(" -fx-font-size: 14px;");
            buttonPreferiti.setText(labelPreferiti);
            buttonPreferiti.setPrefSize(150, 50);
            buttonPreferiti.setStyle("-fx-font-size: 14px;");
            VBox vBox = new VBox(buttonAcquista, buttonPreferiti);
            vBox.setAlignment(Pos.CENTER);
            ViewSearchArtWorkBuyer sa = new ViewSearchArtWorkBuyer();

            buttonAcquista.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    buttonPreferiti.setText("Paga con Carte");
                    buttonAcquista.setText("Paga con PayPal");
                    buttonAcquista.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            sa.finishPayment( idOpera, idBuyer);
                            buttonAcquista.setDisable(true);buttonPreferiti.setVisible(false);buttonAcquista.setText("Opera Acquistata!");

                        }
                    });
                    buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            sa.finishPayment( idOpera, idBuyer);
                            buttonAcquista.setDisable(true);buttonPreferiti.setVisible(false);buttonAcquista.setText("Opera Acquistata!");
                        }
                    });
                }

            });



            if (arrayListArtWorkId.contains(idOpera)){
                buttonPreferiti.setText("Rimuovi dai Preferiti");
            }
            buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = sa.manageButtonClick(buttonPreferiti,idOpera,idBuyer);
                    buttonPreferiti.setText(answer);
                }
            });


            this.getChildren().addAll(imageView, vBox2, vBox1, prezzo1, prezzo, vBox);
        }

    }
    public void populateListView(String input) throws SQLException, IOException {
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
                InputStream inputStream = null;
                try {
                    inputStream = artWorkBlob.getBinaryStream();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Image image1 = new Image(inputStream, 100, 100, true, false);
                listView.getItems().add(new HBoxCell(artWork.getTitolo(), artist.getNome()+" "+artist.getCognome(),image1, artWork.getIdOpera(), artWork.getPrezzo(),"Aggiungi ai Preferiti", buy.getIdBuyer(), artist.getIdArtista(),arrayOfArtWorkId,input));

            }


        } catch ( ArtWorkNotFoundException throwables) {
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.ARTWORKNOTFOUND);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }

}
