package com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer;

import com.artistcorner.controller.applicationcontroller.ViewBuyerSummary;
import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkBuyer;
import com.artistcorner.controller.guicontroller.viewsearchartworkbuyer.GuiControllerViewSearchArtWorkBuyer;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.others.HBoxInitializer;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileViewSearchArtWorkBuyer {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public Button button1;
    public Button button2;
    public ListView<HBoxCell> listView;
    public Button buttonSearch;
    public Button buttonCanc;
    private double x=0, y=0;
    private Stage stage;
    public TextField textField;
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

    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMain.setOnMousePressed(((event) -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMain.setOnMouseDragged(((event) -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode()== KeyCode.ENTER){
            String input= textField.getText();
            ViewSearchArtWorkBuyer sa = new ViewSearchArtWorkBuyer();
            List<HBoxInitializer> list = sa.initializeListView(input,buy);
            List<GuiControllerMobileViewSearchArtWorkBuyer.HBoxCell> list2 = new ArrayList<>();
            for (int i = 0, listSize = list.size(); i < listSize; i++) {
                HBoxInitializer e = list.get(i);
                list2.add(new GuiControllerMobileViewSearchArtWorkBuyer.HBoxCell(e.getLabelTitolo(), e.getLabelArtista(), e.getImg(), e.getIdOpera(), e.getPrice(), e.getLabelButton(), e.getIdUser(), e.getIdArtista(), e.getArrayList(),e.getInput()));
            }
            ObservableList<GuiControllerMobileViewSearchArtWorkBuyer.HBoxCell> myObservableList = FXCollections.observableList(list2);
            listView.setItems(myObservableList);
        }
    }
    public void buttonSearchOnClick() throws SQLException, IOException {
        String input= textField.getText();
        ViewSearchArtWorkBuyer sa = new ViewSearchArtWorkBuyer();
        List<HBoxInitializer> list = sa.initializeListView(input,buy);
        List<GuiControllerMobileViewSearchArtWorkBuyer.HBoxCell> list2 = new ArrayList<>();
        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HBoxInitializer e = list.get(i);
            list2.add(new GuiControllerMobileViewSearchArtWorkBuyer.HBoxCell(e.getLabelTitolo(), e.getLabelArtista(), e.getImg(), e.getIdOpera(), e.getPrice(), e.getLabelButton(), e.getIdUser(), e.getIdArtista(), e.getArrayList(),e.getInput()));
        }
        ObservableList<GuiControllerMobileViewSearchArtWorkBuyer.HBoxCell> myObservableList = FXCollections.observableList(list2);
        listView.setItems(myObservableList);

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

    public static class HBoxCell extends HBox {
        Label labelArtWorkName = new Label();
        Label labelArtistName = new Label();
        Button buttonAcquista = new Button();
        public Button buttonPreferiti = new Button();
        Label prezzo = new Label();
        Label prezzo1 = new Label();
        Label labelArtistNameDefault = new Label();
        Label labelArtWorkDefault = new Label();

        public HBoxCell (String labelText, String labelText1, Image img, int idOpera, double price, String labelPreferiti, int idBuyer,int idArtista, ArrayList<Integer> arrayListArtWorkId,String input)throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            labelArtWorkName.setText(labelText);
            labelArtWorkName.setAlignment(Pos.CENTER);
            labelArtWorkName.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistName.setText(labelText1);
            labelArtistName.setAlignment(Pos.CENTER);
            labelArtistName.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtWorkName.setPrefSize(75, 25);
            labelArtistName.setPrefSize(75, 25);
            prezzo.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #39A67F;");
            prezzo.setMaxWidth(Double.MAX_VALUE);
            prezzo.setText("€ " + Double.toString(price));prezzo.setPrefSize(50, 50);prezzo.setAlignment(Pos.CENTER);
            prezzo1.setText("Prezzo Opera: ");prezzo1.setPrefSize(75, 50);prezzo1.setAlignment(Pos.CENTER);prezzo1.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setAlignment(Pos.CENTER);vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");
            HBox.setHgrow(labelArtWorkName, Priority.ALWAYS);
            VBox vBox2 = new VBox(labelArtWorkDefault, labelArtistNameDefault);
            vBox2.setAlignment(Pos.CENTER);
            labelArtWorkDefault.setText("Titolo Opera: ");labelArtWorkDefault.setAlignment(Pos.CENTER);labelArtWorkDefault.setPrefSize(75, 25);
            labelArtWorkDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            labelArtWorkDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            labelArtistNameDefault.setText("Nome Autore: ");
            labelArtistNameDefault.setAlignment(Pos.CENTER);
            labelArtistNameDefault.setPrefSize(75, 25);
            labelArtistNameDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            buttonAcquista.setText("Acquista");
            buttonAcquista.setPrefSize(90, 25);
            buttonAcquista.setStyle(" -fx-font-size: 8px; -fx-background-color: #39A67F; -fx-background-radius: 0;");
            buttonPreferiti.setText(labelPreferiti);
            buttonPreferiti.setPrefSize(90, 25);
            buttonPreferiti.setStyle(" -fx-font-size: 8px; -fx-background-color: #39A67F; -fx-background-radius: 0;");
            VBox vBox = new VBox(buttonAcquista, buttonPreferiti);
            vBox.setSpacing(2.5);
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
                            buttonAcquista.setStyle("-fx-font-size: 8px;-fx-background-color: trasparent");buttonAcquista.setDisable(true);buttonPreferiti.setVisible(false);buttonAcquista.setText("Opera Acquistata!");
                            buttonAcquista.setAlignment(Pos.BOTTOM_CENTER);buttonAcquista.setPrefSize(90,35);buttonPreferiti.setPrefSize(90,10);
                        }
                    });
                    buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            sa.finishPayment( idOpera, idBuyer);
                            buttonAcquista.setStyle("-fx-font-size: 8px;-fx-background-color: trasparent");buttonAcquista.setDisable(true);buttonPreferiti.setVisible(false);buttonAcquista.setText("Opera Acquistata!");
                            buttonAcquista.setAlignment(Pos.BOTTOM_CENTER);buttonAcquista.setPrefSize(90,35);buttonPreferiti.setPrefSize(90,10);
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
                    String answer = sa.manageButtonClick(arg0,buttonAcquista,buttonPreferiti,idOpera,idBuyer);
                    buttonPreferiti.setText(answer);
                }
            });


            this.getChildren().addAll(imageView, vBox2, vBox1, prezzo1, prezzo, vBox);
        }

    }

}


