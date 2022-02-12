package com.artistcorner.controller.guicontroller.mobile.forwardproposal;

import com.artistcorner.controller.applicationcontroller.ForwardProposal;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
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

public class GuiControllerMobileForwardProposal {
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
    private ToggleButton toglleCat1Mob;
    @FXML
    private ToggleButton toglleCat2Mob;
    @FXML
    private ToggleButton toglleCat3Mob;
    @FXML
    private TextField textField;

    private double x = 0;
    private  double y = 0;
    private ArtGalleryBean gal;
    private String category="";

    public void initialize() {
        makeDraggable();
        ToggleGroup toggleGroup = new ToggleGroup();
        toglleCat1Mob.setToggleGroup(toggleGroup);
        toglleCat2Mob.setToggleGroup(toggleGroup);
        toglleCat3Mob.setToggleGroup(toggleGroup);
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
        SceneControllerMobile.switchToSceneGallerySummary(actionEvent, gal);
    }
    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile.switchToSceneProfiloGallery(actionEvent,gal);
    }

    public class HBoxCellMobile extends HBox {
        Label labelArtWorkName = new Label();
        Label labelArtistName = new Label();
        Button buttonOfferta = new Button();
        ImageView imageView = new ImageView();
        public HBoxCellMobile(List<ArtistBean> arrayListProposte, String input, ArtistBean artistBean, ArtworkBean artWorkBean) throws SQLException, IOException {
            imageView.setImage(extractImage(artWorkBean.getImmagine()));
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);

            HBox hBox_border = new HBox(imageView);  // Imposta bordo all'immagine tramite un HBox
            hBox_border.setMinWidth(75);
            hBox_border.setMinHeight(75);
            hBox_border.getStyleClass().add("hBoxBorderMAB");

            labelArtWorkName.setText(artWorkBean.getTitolo());
            labelArtWorkName.isWrapText();
            labelArtWorkName.setAlignment(Pos.CENTER);
            labelArtWorkName.setTextFill(Paint.valueOf("22634c"));

            labelArtistName.setText(artistBean.getNome()+" "+artistBean.getCognome());
            labelArtistName.isWrapText();
            labelArtistName.setAlignment(Pos.CENTER);
            labelArtistName.setTextFill(Paint.valueOf("39A67F"));

            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setSpacing(5);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold ");
            vBox1.setMinWidth(120);

            HBox.setHgrow(vBox1, Priority.ALWAYS);

            buttonOfferta.setText("Invia Proposta");
            buttonOfferta.setPrefSize(110, 30);
            buttonOfferta.getStyleClass().add("buttonProp");

            HBox hBoxSearchB = new HBox(buttonOfferta);
            hBoxSearchB.setAlignment(Pos.CENTER);


            ForwardProposal sa = new ForwardProposal();
            for(ArtistBean art : arrayListProposte) {
                if (art.getIdArtista()==artistBean.getIdArtista()) {
                    buttonOfferta.setText("Proposta Inviata");
                    buttonOfferta.setDisable(true);
                    buttonOfferta.getStyleClass().add("buttonPropSent");

                }
            }
            buttonOfferta.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = null;
                    try {
                        answer = sa.manageButtonClick(buttonOfferta.getText(), gal,artistBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    buttonOfferta.setText(answer);
                    buttonOfferta.setDisable(true);
                    buttonOfferta.getStyleClass().add("buttonPropSent");
                    try {
                        populateListView(input);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            this.getChildren().addAll(hBox_border,vBox1, hBoxSearchB);
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
    public void populateListView(String input) throws SQLException, IOException {
        paneExLoad.setVisible(false);
        if(toglleCat1Mob.isSelected()){category = "impressionista";}
        if(toglleCat2Mob.isSelected()){category = "espressionista";}
        if(toglleCat3Mob.isSelected()){category = "stilizzato";}

        if (listView.getItems().size()!=0){
            listView.getItems().clear();
        }
        ForwardProposal vsg = new ForwardProposal();
        ArtistBean artist=null;
        ArtworkBean artToSearch = new ArtworkBean();
        artToSearch.setTitolo(input);
        artToSearch.setCategoria(category);

        try{
            List<ArtworkBean> arrayOfArtWork = vsg.retrieveGallerySearchArtWorkByName(artToSearch);
            List<ArtistBean> artistIdList = vsg.retrieveGallerySearchArtistId(gal);
            for (ArtworkBean artWork: arrayOfArtWork) {
                artist = vsg.retrieveGallerySearchArtistName(artWork);
                listView.getItems().add(new HBoxCellMobile(artistIdList,input,artist,artWork));

            }
        } catch (ArtworkNotFoundException throwables) {
            paneExLoad.setVisible(true);
        }
    }

}





