package com.artistcorner.controller.guicontroller.mobile.viewsearchartworkgallery;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkGallery;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileViewSearchArtWorkGallery {
    @FXML
    private AnchorPane anchorMainSearchGalMob;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private ListView<HBoxCellMobile> listView;
    @FXML
    private Stage stageGalMob;
    @FXML
    private Pane paneExLoad;
    @FXML
    private ToggleButton toglleCat1,toglleCat2,toglleCat3;
    @FXML
    private TextField textField;

    private double x = 0;
    private  double y = 0;
    private ArtGalleryBean gal;
    private String category="";

    public void initialize() {
        makeDraggable();
        ToggleGroup toggleGroup = new ToggleGroup();
        toglleCat1.setToggleGroup(toggleGroup);
        toglleCat2.setToggleGroup(toggleGroup);
        toglleCat3.setToggleGroup(toggleGroup);
    }


    public void getGallery(ArtGalleryBean loggedGallery) {
        gal = loggedGallery;      // Prendo le informazioni riguardanti la galleria che ha effettuato il login.
        labelUsernameDisplay.setText(gal.getNome());
    }

    public void exitWindow() {
        stageGalMob = (Stage) anchorMainSearchGalMob.getScene().getWindow();
        stageGalMob.close();
    }

    public void minimizeWindow() {
        stageGalMob = (Stage) anchorMainSearchGalMob.getScene().getWindow();
        stageGalMob.setIconified(true);
    }

    private void makeDraggable() {
        anchorMainSearchGalMob.setOnMousePressed((eventGalPress -> {
            x = eventGalPress.getSceneX();
            y = eventGalPress.getSceneY();
        }));

        anchorMainSearchGalMob.setOnMouseDragged((eventGalDrag -> {
            stageGalMob = (Stage) ((Node) eventGalDrag.getSource()).getScene().getWindow();
            stageGalMob.setX(eventGalDrag.getScreenX() - x);
            stageGalMob.setY(eventGalDrag.getScreenY() - y);
        }));
    }
    public void buttonSearchOnClick() throws SQLException, IOException {
        String input = textField.getText();
        populateListView(input);
    }

    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException{
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String input = textField.getText();
            populateListView(input);
        }
    }

    public void buttonCancOnClick() {
        textField.setText("");
    }

    public void switchToSummaryGallery(ActionEvent actionEvent) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneGallerySummary(actionEvent, gal);
    }

    public void switchToSentArtGalleryProposal(ActionEvent actionEvent) throws IOException, SQLException{
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneSentArtGalleryProposal(actionEvent, gal);
    }
    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloGallery(actionEvent,gal);
    }

    public class HBoxCellMobile extends HBox {
        Label labelArtWorkName = new Label();
        Label labelArtistName = new Label();
        Button buttonOfferta = new Button();
        Label labelArtistNameDefault = new Label();
        Label labelArtWorkDefault = new Label();

        public HBoxCellMobile(String labelTitolo, String labelArtista, Image img, int idOpera, double price, String labelButton, int idGallery, int idArtista, List<Integer> arrayListProposte,String input) throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);
            labelArtWorkName.setText(labelTitolo);
            labelArtWorkName.isWrapText();
            labelArtWorkName.setAlignment(Pos.CENTER);
            labelArtWorkName.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistName.setText(labelArtista);
            labelArtistName.isWrapText();
            labelArtistName.setAlignment(Pos.CENTER);
            labelArtistName.setStyle("-fx-font-size: 10px;-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setSpacing(5);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");
            HBox.setHgrow(labelArtWorkName, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 65, 10, 65));
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
                        populateListView(input);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            this.getChildren().addAll(imageView,vBox1, buttonOfferta);
        }

    }
    public void populateListView(String input) throws SQLException, IOException {
        paneExLoad.setVisible(false);
        if(toglleCat1.isSelected()){category = "impressionista";}
        if(toglleCat2.isSelected()){category = "espressionista";}
        if(toglleCat3.isSelected()){category = "stilizzato";}

        if (listView.getItems().size()!=0){
            listView.getItems().clear();
        }
        ViewSearchArtWorkGallery vsg = new ViewSearchArtWorkGallery();
        ArtistBean artist=null;

        try{
            List<ArtWorkBean> arrayOfArtWork = vsg.retrieveGallerySearchArtWorkByName(input,category);
            List<Integer> artistIdList = vsg.retrieveGallerySearchArtistId(gal);
            for (ArtWorkBean artWork: arrayOfArtWork) {
                artist = vsg.retrieveGallerySearchArtistName(artWork);
                Image image1 = extractImage(artWork.getImmagine());
                listView.getItems().add(new HBoxCellMobile(artWork.getTitolo(), artist.getNome()+" "+artist.getCognome(),image1, artWork.getIdOpera(), artWork.getPrezzo(),"Invia Proposta", gal.getGalleria(), artist.getIdArtista(),artistIdList,input));

            }
        } catch (ArtWorkNotFoundException throwables) {
            paneExLoad.setVisible(true);
        }
    }
    private Image extractImage(Blob blob2){
        InputStream inputStream2 = null;
        try {
            inputStream2 = blob2.getBinaryStream();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert inputStream2 != null;
        return new Image(inputStream2, 100, 100, true, false);

    }
}





