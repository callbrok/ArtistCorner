package com.artistcorner.controller.guicontroller.viewsearchartworkgallery;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkGallery;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
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

public class GuiControllerViewSearchArtWorkGallery {
    @FXML
    private AnchorPane anchorParentSearchGal;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Label labelLogOut;
    @FXML
    private ListView<HBoxCell> listView;
    @FXML
    private TextField textField;
    @FXML
    private SVGPath svgProfileSearchGal;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button4;
    @FXML
    private Pane paneExceptionLoad;
    @FXML
    private ToggleButton toglleCat1;
    @FXML
    private ToggleButton toglleCat2;
    @FXML
    private ToggleButton toglleCat3;

    public String category = "";
    private double x=0;
    private double y=0;
    private Stage stageSearchGal;
    private ArtGalleryBean gal;


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

    public void initialize() throws SQLException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();

        ToggleGroup toggleGroup = new ToggleGroup();
        toglleCat1.setToggleGroup(toggleGroup);
        toglleCat2.setToggleGroup(toggleGroup);
        toglleCat3.setToggleGroup(toggleGroup);

        svgProfileSearchGal.setScaleX(0.07);
        svgProfileSearchGal.setScaleY(0.07);
    }

    private void makeDraggable(){
        anchorParentSearchGal.setOnMousePressed((eventSearchPress -> {
            x=eventSearchPress.getSceneX();
            y= eventSearchPress.getSceneY();
        }));

        anchorParentSearchGal.setOnMouseDragged((eventSearchDrag -> {
            stageSearchGal = (Stage) ((Node)eventSearchDrag.getSource()).getScene().getWindow();
            stageSearchGal.setX(eventSearchDrag.getScreenX() - x);
            stageSearchGal.setY(eventSearchDrag.getScreenY() - y);
        }));
    }

    public void exitWindow() {
        stageSearchGal = (Stage) anchorParentSearchGal.getScene().getWindow();
        stageSearchGal.close();
    }

    public void getGallery(ArtGalleryBean loggedGallery) {
        gal = loggedGallery;
        labelUsernameDisplay.setText(gal.getNome());
    }

    public void minimizeWindow() {
        stageSearchGal = (Stage) anchorParentSearchGal.getScene().getWindow();
        stageSearchGal.setIconified(true);
    }

    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException { // avvia la ricerca quando si preme enter 
        if(keyEvent.getCode()== KeyCode.ENTER){ 
            String input= textField.getText();
            populateListView(input);
        }
    }
    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button4.setTooltip(new Tooltip("Cerca Opere"));
    }

    public void buttonSearchOnClick() throws SQLException, IOException{ // avvia la ricerca quando si preme sul bottone della ricerca
        String input= textField.getText();
        populateListView(input);
    }

    public void buttonCancOnClick(){
        textField.setText("");
    }

    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloGallery(actionEvent,gal);
    }
    public void switchToGallerySummary(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneGallerySummary(actionEvent,gal);
    }
    public void populateListView(String input) throws SQLException, IOException { //popola la listview con le opere disponibili
        ArtworkBean artToSearch = new ArtworkBean();

        paneExceptionLoad.setVisible(false);

        if(toglleCat1.isSelected()){category = "impressionista";}
        if(toglleCat2.isSelected()){category = "espressionista";}
        if(toglleCat3.isSelected()){category = "stilizzato";}

        if (listView.getItems().size()!=0){ //le la listview non è vuota al momento della chiamata
            listView.getItems().clear();  //viene svuotata
        }

        ViewSearchArtWorkGallery vsawg = new ViewSearchArtWorkGallery();
        ArtistBean artistSearch;

        artToSearch.setTitolo(input);
        artToSearch.setCategoria(category);

        try{
            List<ArtworkBean> arrayOfArtWorkSearchGal = vsawg.retrieveGallerySearchArtWorkByName(artToSearch);    // lista opere disponibili in base all'input inserito nella textfield
            List<ArtistBean> artistIdListSearchGal = vsawg.retrieveGallerySearchArtistId(gal); //lista id artisti a cui è  stata inviata una proposta

            for (ArtworkBean artWork: arrayOfArtWorkSearchGal) {
                artistSearch = vsawg.retrieveGallerySearchArtistName(artWork);
                listView.getItems().add(new HBoxCell(artistIdListSearchGal,input,gal,artistSearch,artWork));
            }

        } catch ( ArtworkNotFoundException throwables) {
            paneExceptionLoad.setVisible(true);
        }
    }
    public class HBoxCell extends HBox {
        Label labelArtWorkNameSearchGal = new Label();
        Label labelArtistNameSearchGal = new Label();
        Button buttonOffertaSearchGal = new Button();
        ImageView imageViewSearchGal = new ImageView(); // immagine dell'opera

        public HBoxCell(List<ArtistBean> arrayListProposteGal, String inputSearchGal, ArtGalleryBean artGalleryBean, ArtistBean artBean, ArtworkBean artWork) throws SQLException, IOException {
            imageViewSearchGal.setImage(extractImage(artWork.getImmagine()));
            imageViewSearchGal.setFitHeight(100);
            imageViewSearchGal.setFitWidth(100);

            labelArtWorkNameSearchGal.setText(artWork.getTitolo());
            labelArtWorkNameSearchGal.isWrapText();
            labelArtWorkNameSearchGal.setAlignment(Pos.CENTER);
            labelArtWorkNameSearchGal.setStyle("-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtWorkNameSearchGal.isWrapText();

            labelArtistNameSearchGal.setText(artBean.getNome()+" "+artBean.getCognome());
            labelArtistNameSearchGal.setAlignment(Pos.CENTER);
            labelArtistNameSearchGal.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtistNameSearchGal.isWrapText();

            VBox vBox1 = new VBox(labelArtWorkNameSearchGal, labelArtistNameSearchGal); // vbox contenente titolo dell'opera e nome dell'artista
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setMinWidth(150);
            vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");

            HBox.setHgrow(labelArtWorkNameSearchGal, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 120, 10, 100));


            buttonOffertaSearchGal.setText("Invia Proposta");
            buttonOffertaSearchGal.setPrefSize(150, 100);
            buttonOffertaSearchGal.setStyle("-fx-font-size: 16px;");
            ViewSearchArtWorkGallery sawg = new ViewSearchArtWorkGallery();
            for (ArtistBean a :arrayListProposteGal) {
                if (a.getIdArtista()==artBean.getIdArtista()) {     // se l'array contenente tutti gli id artista contiene l'id dell'artista in questione
                    buttonOffertaSearchGal.setText("Ritira Proposta");      // imposto il testo del button su 'ritira proposta'
                }
            }
            buttonOffertaSearchGal.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = null;
                    try {
                        answer = sawg.manageButtonClick(buttonOffertaSearchGal.getText(), artGalleryBean, artBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    buttonOffertaSearchGal.setText(answer);
                    try {
                        populateListView(inputSearchGal); //ricarica la listview per aggiornare le ricorrenze di opere dello stesso artista
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            this.getChildren().addAll(imageViewSearchGal,vBox1, buttonOffertaSearchGal);
        }

    }

    private Image extractImage(Blob blob5){
        InputStream inputStream5 = null;
        try {
            inputStream5 = blob5.getBinaryStream();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert inputStream5 != null;
        return new Image(inputStream5, 100, 100, true, false);

    }

}
