package com.artistcorner.controller.guicontroller.uploadartwork;

import com.artistcorner.controller.applicationcontroller.UploadArtWork;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.DuplicateArtWorkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class GuiControllerUploadArtwork {
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;

    public AnchorPane anchorParent;
    public TextField textFieldTitle;
    public TextField textFieldPrice;
    public Label labelFilePath;
    public RadioButton radioBtmSell;
    public AnchorPane anchorPaneDragAndDrop;
    public Label labelLogOut;
    public Pane paneExceptionLoad;
    public SVGPath svgProfile;
    public Label labelUsernameDisplay;
    private double x=0, y=0;
    private Stage stage;

    String filePath;
    ArtistBean art;


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
    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.setIconified(true);
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


    public void initialize(){
        initDragAndDrop();
        makeDraggable();
        setTooltipMenu();
        makeLogOut();

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Carica Opera"));
        button4.setTooltip(new Tooltip("Offerte Mostre"));
        button5.setTooltip(new Tooltip("Opere Vendute"));
    }

    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
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

    public void initDragAndDrop(){

//        anchorPaneDragAndDrop.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
//            Dragboard db = event.getD();
//            File file = db.getFiles().get(0);
//        });

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

            ev = ef.createView(ExceptionsTypeMenager.EMPTYFIELD);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        } catch (FileNotFoundException e) {
            // Eccezione: File non selezionato.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.EMPTYPATH);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        } catch (DuplicateArtWorkException e){
            // Eccezione: Opera già caricata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.DUPLICATEARTWORK);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }

        resetForm();
    }

    public void resetForm(){
        labelFilePath.setText("");
        textFieldPrice.setText("");
        textFieldTitle.setText("");
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


}
