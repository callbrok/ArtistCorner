package com.artistcorner.controller.guicontroller.mobile.viewsearchartworkgallery;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkGallery;
import com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer.GuiControllerMobileViewSearchArtWorkBuyer;
import com.artistcorner.controller.guicontroller.viewsearchartworkgallery.GuiControllerViewSearchArtWorkGallery;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
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

public class GuiControllerMobileViewSearchArtWorkGallery {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public Button button1;
    public Button button2;
    public ListView<HBoxCellMobile> listView;
    public Button buttonSearch;
    public Button buttonCanc;
    private double x = 0, y = 0;
    private Stage stage;
    public TextField textField;
    ArtGalleryBean gal;

    public void initialize() {
        makeDraggable();
    }

    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(event);
    }

    public void getGallery(ArtGalleryBean loggedGallery) {
        gal = loggedGallery;      // Prendo le informazioni riguardanti la galleria che ha effettuato il login.
        labelUsernameDisplay.setText(gal.getNome());
    }

    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable() {
        anchorMain.setOnMousePressed(((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        }));

        anchorMain.setOnMouseDragged(((event) -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String input = textField.getText();
            ViewSearchArtWorkGallery sa = new ViewSearchArtWorkGallery();
            List<HBoxInitializer> list = sa.initializeListView(input, gal);
            List<HBoxCellMobile> list2 = new ArrayList<>();
            for (int i = 0, listSize = list.size(); i < listSize; i++) {
                HBoxInitializer e = list.get(i);
                list2.add(new HBoxCellMobile(e.getLabelTitolo(), e.getLabelArtista(), e.getImg(), e.getIdOpera(), e.getPrice(), e.getLabelButton(), e.getIdUser(), e.getIdArtista(), e.getArrayList(),e.getInput()));
            }
            ObservableList<HBoxCellMobile> myObservableList = FXCollections.observableList(list2);
            listView.setItems(myObservableList);
        }
    }

    public void buttonSearchOnClick() throws SQLException, IOException {
        String input = textField.getText();
        ViewSearchArtWorkGallery sa = new ViewSearchArtWorkGallery();
        List<HBoxInitializer> list = sa.initializeListView(input, gal);
        List<HBoxCellMobile> list2 = new ArrayList<>();
        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HBoxInitializer e = list.get(i);
            list2.add(new HBoxCellMobile(e.getLabelTitolo(), e.getLabelArtista(), e.getImg(), e.getIdOpera(), e.getPrice(), e.getLabelButton(), e.getIdUser(), e.getIdArtista(), e.getArrayList(),e.getInput()));
        }
        ObservableList<HBoxCellMobile> myObservableList = FXCollections.observableList(list2);
        listView.setItems(myObservableList);
    }

    public void buttonCancOnClick() {
        textField.setText("");
    }

    public void switchToSummaryGallery(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneGallerySummary(actionEvent, gal);
    }

    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloGallery(actionEvent, gal);
    }

    public class HBoxCellMobile extends HBox {
        Label labelArtWorkName = new Label();
        Label labelArtistName = new Label();
        public Button buttonOfferta = new Button();
        Label labelArtistNameDefault = new Label();
        Label labelArtWorkDefault = new Label();

        public HBoxCellMobile(String labelTitolo, String labelArtista, Image img, int idOpera, double price, String labelButton, int idGallery, int idArtista, ArrayList<Integer> arrayListProposte,String input) throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);
            labelArtWorkName.setText(labelTitolo);
            labelArtWorkName.setAlignment(Pos.CENTER);
            labelArtWorkName.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistName.setText(labelArtista);
            labelArtistName.setAlignment(Pos.CENTER);
            labelArtistName.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtWorkName.setPrefSize(75, 25);
            labelArtistName.setPrefSize(75, 25);
            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");
            HBox.setHgrow(labelArtWorkName, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 10, 10, 10));
            VBox vBox2 = new VBox(labelArtWorkDefault, labelArtistNameDefault);
            vBox2.setAlignment(Pos.CENTER);
            labelArtWorkDefault.setText("Titolo Opera: ");
            labelArtWorkDefault.setAlignment(Pos.CENTER);
            labelArtWorkDefault.setPrefSize(75, 25);
            labelArtWorkDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            HBox.setMargin(vBox2, new Insets(10, 25, 10, 25));
            labelArtWorkDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            labelArtistNameDefault.setText("Nome Autore: ");
            labelArtistNameDefault.setAlignment(Pos.CENTER);
            labelArtistNameDefault.setPrefSize(75, 25);
            labelArtistNameDefault.setStyle("-fx-font-size: 10px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            buttonOfferta.setText(labelButton);
            buttonOfferta.setScaleX(0.7);
            buttonOfferta.setScaleY(0.7);
            buttonOfferta.setPrefSize(125, 75);
            buttonOfferta.setStyle("-fx-font-size: 14px; -fx-background-color: #39A67F; -fx-background-radius: 0;");
            ViewSearchArtWorkGallery sa = new ViewSearchArtWorkGallery();

            if (arrayListProposte.contains(idArtista)) {
                buttonOfferta.setText("Ritira Proposta");
            }
            buttonOfferta.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = null;
                    try {
                        answer = sa.manageButtonClick(buttonOfferta, idGallery, idArtista);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    buttonOfferta.setText(answer);
                    try {
                        List<HBoxInitializer> list = sa.initializeListView(input, gal);
                        List<GuiControllerMobileViewSearchArtWorkGallery.HBoxCellMobile> list2 = new ArrayList<>();
                        for (int i = 0, listSize = list.size(); i < listSize; i++) {
                            HBoxInitializer e = list.get(i);
                            list2.add(new GuiControllerMobileViewSearchArtWorkGallery.HBoxCellMobile(e.getLabelTitolo(), e.getLabelArtista(), e.getImg(), e.getIdOpera(), e.getPrice(), e.getLabelButton(), e.getIdUser(), e.getIdArtista(), e.getArrayList(),e.getInput()));

                        }
                        ObservableList<GuiControllerMobileViewSearchArtWorkGallery.HBoxCellMobile> myObservableList = FXCollections.observableList(list2);
                        listView.setItems(myObservableList);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            this.getChildren().addAll(imageView, vBox2, vBox1, buttonOfferta);
        }

    }
}





