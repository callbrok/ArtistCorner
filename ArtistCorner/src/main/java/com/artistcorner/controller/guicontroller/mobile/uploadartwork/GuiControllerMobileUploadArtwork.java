package com.artistcorner.controller.guicontroller.mobile.uploadartwork;

import com.artistcorner.controller.applicationcontroller.UploadArtWork;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.DuplicateArtWorkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerMobileUploadArtwork {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public TextField textFieldTitle;
    public TextField textFieldPrice;
    public RadioButton radioBtmSell;
    public Label labelFilePath;
    public Pane paneExceptionLoad;
    private double x=0, y=0;
    private Stage stage;

    String filePath;
    ArtistBean art;


    public void initialize(){
        makeDraggable();

    }

    public void selectFile(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // Impone la selezione di soli file di tipo immagine.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File Immagine", "*.jpg", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(stage);
        filePath = selectedFile.toString();   // Setta il path dell'immagine nella bean.
        labelFilePath.setText(selectedFile.toString());   // Mostra il percorso del file selezionato.
    }

    public void uploadFile(ActionEvent event) throws Exception {
        UploadArtWork upaw = new UploadArtWork();
        int flagVendibile;
        double prezzo;

        // Stati di flagVendibile
        //  0 : opera non acquistabile
        //  1 : opera acquistabile
        if (radioBtmSell.isSelected()) {
            flagVendibile=1;
            prezzo = Double.parseDouble(textFieldPrice.getText());
        } else {
            flagVendibile = 0;
            prezzo = 0;
        }

        try {
            ArtWorkBean upArtWork = new ArtWorkBean(textFieldTitle.getText(), prezzo, flagVendibile);
            upaw.uploadImage(upArtWork, art.getIdArtista(), filePath);
        } catch (EmptyFieldException e){
            // Eccezione: Campi lasciati vuoti.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.EMPTYFIELD_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        } catch (FileNotFoundException e) {
            // Eccezione: File non selezionato.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.EMPTYPATH_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        } catch (DuplicateArtWorkException e){
            // Eccezione: Opera già caricata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.DUPLICATEARTWORK_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }

        resetForm();
    }

    public void resetForm(){
        labelFilePath.setText("");
        textFieldPrice.setText("");
        textFieldTitle.setText("");
    }


    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
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

    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }
}
