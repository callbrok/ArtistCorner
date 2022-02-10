package com.artistcorner.controller.guicontroller.uploadartwork;

import com.artistcorner.controller.applicationcontroller.UploadArtwork;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.DuplicateArtworkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.EmptyPathException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerUploadArtwork {
    @FXML
    private ToggleButton toggleCat1;
    @FXML
    private ToggleButton toggleCat2;
    @FXML
    private ToggleButton toggleCat3;
    @FXML
    private ToggleButton toggleCat4;
    @FXML
    private Button button1Up;
    @FXML
    private Button button2Up;
    @FXML
    private Button button3Up;
    @FXML
    private Button button4Up;
    @FXML
    private Button button5Up;
    @FXML
    private AnchorPane anchorParentUpload;
    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private Label labelFilePath;
    @FXML
    private AnchorPane anchorPaneDragAndDrop;
    @FXML
    private Pane paneExceptionLoad;
    @FXML
    private SVGPath svgProfile;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Label labelLogOutUpload;
    @FXML
    private RadioButton radioBtmSellUpload;

    private double xUpload=0;
    private double yUpload=0;
    private ToggleGroup toggleGroup = new ToggleGroup();
    private Stage stage;

    private String filePath="";
    private ArtistBean art;


    private void makeDraggable(){
        anchorParentUpload.setOnMousePressed((eventUpload -> {
            xUpload=eventUpload.getSceneX();
            yUpload= eventUpload.getSceneY();
        }));

        anchorParentUpload.setOnMouseDragged((eventUpload -> {
            stage = (Stage) ((Node)eventUpload.getSource()).getScene().getWindow();
            stage.setX(eventUpload.getScreenX() - xUpload);
            stage.setY(eventUpload.getScreenY() - yUpload);
        }));
    }
    
    public void exitWindowUpload() throws IOException {
        SceneController.deleteSerialNodo(art.getIdArtista());

        stage = (Stage) anchorParentUpload.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentUpload.getScene().getWindow();
        stage.setIconified(true);
    }

    public void makeLogOut(){
        labelLogOutUpload.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                SceneController.deleteSerialNodo(art.getIdArtista());
                sc.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public void initialize(){
        initDragAndDrop();
        makeDraggable();
        setTooltipMenu();
        makeLogOut();
        initEventHandlerRadio();

        toggleCat1.setToggleGroup(toggleGroup);
        toggleCat2.setToggleGroup(toggleGroup);
        toggleCat3.setToggleGroup(toggleGroup);
        toggleCat4.setToggleGroup(toggleGroup);

        toggleCat4.setSelected(true);

        textFieldPrice.setVisible(false);
        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }

    /**
     * Setta i tooltip su i bottoni del menu.
     */
    public void setTooltipMenu(){
        button1Up.setTooltip(new Tooltip("Home"));
        button2Up.setTooltip(new Tooltip("Profilo"));
        button3Up.setTooltip(new Tooltip("Carica Opera"));
        button4Up.setTooltip(new Tooltip("Offerte Mostre"));
        button5Up.setTooltip(new Tooltip("Opere Vendute"));
    }

    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
    }

    /**
     * Seleziona l'immagine da caricare.
     */
    public void selectFile(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // Impone la selezione di soli file di tipo immagine.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File Immagine", "*.jpg", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(stage);

        filePath = selectedFile.toString();

        labelFilePath.setText(selectedFile.toString());   // Mostra il percorso del file selezionato.
    }

    public void initDragAndDrop(){
        //Implement Drag and Drop Feature.

    }

    /**
     * Effettua l'upload dell'opera.
     */
    public void uploadFile(){
        UploadArtwork upawDesk = new UploadArtwork();
        int flagVendibile;
        double prezzo;
        String categoria = "";

        ToggleButton selectedToggleButton = (ToggleButton) toggleGroup.getSelectedToggle();  // Ritorna il toggle selezionato.


        categoria = selectedToggleButton.getText();

        if(selectedToggleButton.getText().equals("altro")){categoria="";}


        // Stati di flagVendibile
        //  0 : opera non acquistabile
        //  1 : opera acquistabile
        if (radioBtmSellUpload.isSelected()) {
            flagVendibile=1;
            prezzo = Double.parseDouble(textFieldPrice.getText());
        } else {
            flagVendibile = 0;
            prezzo = 0;
        }

        try {
            ArtworkBean upArtWork = new ArtworkBean();

            upArtWork.setTitolo(textFieldTitle.getText());
            upArtWork.setPrezzo(prezzo);
            upArtWork.setFlagVendibile(flagVendibile);
            upArtWork.setArtistId(art.getIdArtista());
            upArtWork.setCategoria(categoria);

            upawDesk.uploadImage(upArtWork, filePath);

        } catch (EmptyFieldException e){
            // Eccezione: Campi lasciati vuoti.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.EMPTYFIELD);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }catch (EmptyPathException e) {
            // Eccezione: File non selezionato.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.EMPTYPATH);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }catch (DuplicateArtworkException e){
            // Eccezione: Opera già caricata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.DUPLICATEARTWORK);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }

        resetForm();
    }

    public void resetForm(){
        labelFilePath.setText("");
        textFieldPrice.setText("");
        textFieldTitle.setText("");
    }

    public void initEventHandlerRadio(){
        radioBtmSellUpload.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> textFieldPrice.setVisible(isNowSelected));
    }


    public void switchToSceneMainArtista(ActionEvent event) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneUploadOpera(event, art);
    }
}
