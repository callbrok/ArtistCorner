package com.artistcorner.controller.guicontroller.uploadartwork;

import com.artistcorner.controller.applicationcontroller.UploadArtWork;
import com.artistcorner.engclasses.bean.UploadingArtWork;
import com.artistcorner.engclasses.bean.User;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.Artist;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    private double x=0, y=0;
    private Stage stage;

    UploadingArtWork upArtWork = new UploadingArtWork();
    Artist art;


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
    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Carica Opera"));
        button4.setTooltip(new Tooltip("Offerte Mostre"));
        button5.setTooltip(new Tooltip("Opere Vendute"));
    }

    public void getArtist(Artist loggedArtist) {
        art = loggedArtist;
    }

    public void selectFile(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        File selectedFile = fileChooser.showOpenDialog(stage);
        upArtWork.setFilePath(selectedFile.toString());   // Setta il path dell'immagine nella bean.
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

        upArtWork.setTitolo(textFieldTitle.getText());
        upArtWork.setIdArtist(art.getIdArtista());

        // Stati di flagVendibile
        //  0 : opera non acquistabile
        //  1 : opera acquistabile
        if (radioBtmSell.isSelected()) {
            upArtWork.setFlagVendibile(1);
            upArtWork.setPrezzo(Double.parseDouble(textFieldPrice.getText()));
        } else {
            upArtWork.setFlagVendibile(0);
            upArtWork.setPrezzo(0);
        }

        upaw.uploadImage(upArtWork);
        resetForm();

    }

    public void resetForm(){
        labelFilePath.setText("");
        textFieldPrice.setText("");
        textFieldTitle.setText("");
    }


    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
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
