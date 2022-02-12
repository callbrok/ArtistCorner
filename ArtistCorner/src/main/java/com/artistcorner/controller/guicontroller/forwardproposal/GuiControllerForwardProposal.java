package com.artistcorner.controller.guicontroller.forwardproposal;

import com.artistcorner.controller.applicationcontroller.ForwardProposal;
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

public class GuiControllerForwardProposal {
    @FXML
    private AnchorPane anchorParentSearchGal;
    @FXML
    private AnchorPane anchorPaneFocus;
    @FXML
    private ImageView imageFocused;
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

    private String category = "";
    private double x=0;
    private double y=0;
    private Stage stageSearchGal;
    private ArtGalleryBean gal;


    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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
        SceneController.switchToSceneProfiloGallery(actionEvent,gal);
    }
    public void switchToGallerySummary(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneGallerySummary(actionEvent,gal);
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

        ForwardProposal vsawg = new ForwardProposal();
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

            HBox hBox_border = new HBox(imageViewSearchGal);  // Imposta bordo all'immagine tramite un HBox
            hBox_border.setMinWidth(100);
            hBox_border.setMinHeight(100);
            hBox_border.getStyleClass().add("hBoxBorderMAB");

            labelArtWorkNameSearchGal.setText(artWork.getTitolo());
            labelArtWorkNameSearchGal.isWrapText();
            labelArtWorkNameSearchGal.setAlignment(Pos.CENTER);
            labelArtWorkNameSearchGal.setStyle("-fx-text-fill: #22634c; -fx-font-weight: bold ");
            labelArtWorkNameSearchGal.isWrapText();

            labelArtistNameSearchGal.setText(artBean.getNome()+" "+artBean.getCognome());
            labelArtistNameSearchGal.setAlignment(Pos.CENTER);
            labelArtistNameSearchGal.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtistNameSearchGal.isWrapText();


            VBox vBox1 = new VBox(labelArtWorkNameSearchGal, labelArtistNameSearchGal); // vbox contenente titolo dell'opera e nome dell'artista
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");

            HBox.setHgrow(vBox1, Priority.ALWAYS);


            buttonOffertaSearchGal.setText("Invia Proposta");
            buttonOffertaSearchGal.setPrefSize(170, 50);
            buttonOffertaSearchGal.setStyle("-fx-font-size: 16px;");

            HBox hBoxSearchB = new HBox(buttonOffertaSearchGal);
            hBoxSearchB.setAlignment(Pos.CENTER);

            ForwardProposal sawg = new ForwardProposal();

            for (ArtistBean a :arrayListProposteGal) {
                if (a.getIdArtista()==artBean.getIdArtista()) {     // se l'array contenente tutti gli id artista contiene l'id dell'artista in questione
                    buttonOffertaSearchGal.setText("Proposta Inviata");      // imposto il testo del button su 'ritira proposta'
                    buttonOffertaSearchGal.setDisable(true);
                }
            }

            buttonOffertaSearchGal.setOnAction(arg0 -> {
                String answer = null;
                try {
                    answer = sawg.manageButtonClick(buttonOffertaSearchGal.getText(), artGalleryBean, artBean);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                buttonOffertaSearchGal.setText(answer);
                buttonOffertaSearchGal.setDisable(true);
                try {
                    populateListView(inputSearchGal); //ricarica la listview per aggiornare le ricorrenze di opere dello stesso artista
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }

            });

            EventHandler<MouseEvent> mouseHandler = tD -> {    // Crea un EventHandler sull'imageView all'interno del tilePane.
                ImageView imageView2 = (ImageView) tD.getSource();  // Prende l'imageView collegata all'evento.

                imageFocused.setImage(imageView2.getImage());   // Setta l'immagine e la rende focused.
                centerImage(imageFocused);                     // Centra l'immagine.
                anchorPaneFocus.setVisible(true);
            };

            anchorPaneFocus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> anchorPaneFocus.setVisible(false));

            imageViewSearchGal.setOnMouseClicked(mouseHandler);
            this.getChildren().addAll(hBox_border,vBox1, hBoxSearchB);
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
        public void centerImage(ImageView imageView) {
            Image img = imageView.getImage();
            if (img != null) {
                double w1 = 0;
                double h1 = 0;

                double ratioX1 = imageView.getFitWidth() / img.getWidth();
                double ratioY1 = imageView.getFitHeight() / img.getHeight();

                double reducCoeff1 = 0;
                if(ratioX1 >= ratioY1) {
                    reducCoeff1 = ratioY1;
                } else {
                    reducCoeff1 = ratioX1;
                }

                w1 = img.getWidth() * reducCoeff1;
                h1 = img.getHeight() * reducCoeff1;

                imageView.setX((imageView.getFitWidth() - w1) / 2);
                imageView.setY((imageView.getFitHeight() - h1) / 2);

            }
        }

    }



}
