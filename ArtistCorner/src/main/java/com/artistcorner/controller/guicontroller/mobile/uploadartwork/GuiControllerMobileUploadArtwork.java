package com.artistcorner.controller.guicontroller.mobile.uploadartwork;

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
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerMobileUploadArtwork {
    @FXML
    private AnchorPane anchorPriceExc;
    @FXML
    private ToggleButton toggleCatMob1;
    @FXML
    private ToggleButton toggleCatMob2;
    @FXML
    private ToggleButton toggleCatMob3;
    @FXML
    private ToggleButton toggleCatMob4;
    @FXML
    private AnchorPane anchorMainUpM;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private RadioButton radioBtmSell;
    @FXML
    private Label labelFilePath;
    @FXML
    private Pane paneExceptionLoad;

    private double x=0;
    private double y=0;
    private Stage stage;

    private String filePath="";
    private ToggleGroup toggleGroup = new ToggleGroup();
    private ArtistBean art;


    public void initialize(){
        makeDraggable();
        initEventHandlerRadio();
        initTextFieldPrice();

        textFieldPrice.setVisible(false);
        labelUsernameDisplay.setAlignment(Pos.CENTER);

        toggleCatMob1.setToggleGroup(toggleGroup);
        toggleCatMob2.setToggleGroup(toggleGroup);
        toggleCatMob3.setToggleGroup(toggleGroup);
        toggleCatMob4.setToggleGroup(toggleGroup);

        toggleCatMob4.setSelected(true);
        anchorPriceExc.setVisible(false);
    }

    /**
     * Seleziona l'immagine dell'opera da caricare.
     */
    public void selectFile(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // Impone la selezione di soli file di tipo immagine.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File Immagine", "*.jpg", "*.png", "*.bmp", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(stage);
        filePath = selectedFile.toString();   // Setta il path dell'immagine nella bean.
        labelFilePath.setText(selectedFile.toString());   // Mostra il percorso del file selezionato.
    }

    /**
     * Effettua l'upload dell'opera.
     */
    public void uploadFile(){
        UploadArtwork upaw = new UploadArtwork();
        int flagVendibile=0;
        double prezzo=0;
        String categoria = "";

        ToggleButton selectedToggleButton = (ToggleButton) toggleGroup.getSelectedToggle();  // Ritorna il toggle selezionato.
        categoria = selectedToggleButton.getText();

        if(categoria.equals("altro")){categoria="";}

        // Stati di flagVendibile
        //  0 : opera non acquistabile
        //  1 : opera acquistabile

        try {
            ArtworkBean upArtWork = new ArtworkBean();

            if (radioBtmSell.isSelected()) {
                flagVendibile=1;
                prezzo = Double.parseDouble(textFieldPrice.getText().replace(',','.'));
            }

            upArtWork.setTitolo(textFieldTitle.getText());
            upArtWork.setPrezzo(prezzo);
            upArtWork.setFlagVendibile(flagVendibile);
            upArtWork.setArtistId(art.getIdArtista());
            upArtWork.setCategoria(categoria);

            upaw.uploadImage(upArtWork, filePath);
            anchorPriceExc.setVisible(false);

        } catch (EmptyFieldException e){
            // Eccezione: Campi lasciati vuoti.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.EMPTYFIELD_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        } catch (EmptyPathException e) {
            // Eccezione: File non selezionato.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.EMPTYPATH_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        } catch (DuplicateArtworkException e){
            // Eccezione: Opera già caricata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.DUPLICATEARTWORK_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }catch (NumberFormatException n){
            // Eccezione: Prezzo non valido.
            anchorPriceExc.setVisible(true);
        }

        resetForm();
    }

    public void resetForm(){
        labelFilePath.setText("Nessun File Selezionato");
        textFieldPrice.setText("");
        textFieldTitle.setText("");
    }


    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
    }

    public void exitWindow() throws IOException {
        stage = (Stage) anchorMainUpM.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorMainUpM.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMainUpM.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMainUpM.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    /**
     * Permette il solo inserimento di numeri e "," nel textField prezzo.
     */
    public void initTextFieldPrice(){
        textFieldPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("C=(\\d+\\,\\d+)")) {  // Expression Pattern che permette solo numeri e virgole.
                textFieldPrice.setText(newValue.replaceAll("[^C=(\\d+\\,\\d+)]", ""));
            }
        });
    }

    public void initEventHandlerRadio(){
        radioBtmSell.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> textFieldPrice.setVisible(isNowSelected));
    }

    public void switchToSceneMainArtistaFromUploadM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtistaFromUploadM(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOperaFromUploadM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVendutoFromUploadM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostreFromUploadM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneProfiloOfferteMostre(event, art);
    }
}
