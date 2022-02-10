package com.artistcorner.controller.guicontroller.login.summaries;

import com.artistcorner.controller.applicationcontroller.login.summaries.ViewGallerySummary;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerGallerySummary {
    @FXML
    private Button button1;
    @FXML
    private Button button4;
    @FXML
    private Button button2;
    @FXML
    private Label labelComprate;
    @FXML
    private Pane paneComprate;
    @FXML
    private AnchorPane anchorParentGallery;
    @FXML
    private ListView<String> listViewOfferte;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Label labelLogOutGallery;
    private double x=0;
    private double y=0;
    private Stage stageGallery;
    @FXML
    private SVGPath svgProfileGallery;
    @FXML
    private Pane paneExceptionLoad;

    private ArtGalleryBean gal;


    public void initialize(){
        makeDraggable();
        makeLogOutGallery();
        svgProfileGallery.setScaleX(0.07);
        svgProfileGallery.setScaleY(0.07);
    }

    public void makeLogOutGallery(){
        labelLogOutGallery.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                SceneController.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void getGallery(ArtGalleryBean loggedGallery){
        gal = loggedGallery;
        labelUsernameDisplay.setText(gal.getNome());
        initializeOfferteInviate(listViewOfferte,gal);
        paneExceptionLoad.setPrefSize(708,250);
    }


    private void makeDraggable(){

        anchorParentGallery.setOnMouseDragged((eventDraggableDrag -> {
            stageGallery = (Stage) ((Node)eventDraggableDrag.getSource()).getScene().getWindow();
            stageGallery.setX(eventDraggableDrag.getScreenX() - x);
            stageGallery.setY(eventDraggableDrag.getScreenY() - y);
        }));

        anchorParentGallery.setOnMousePressed((eventDraggablePressed -> {
            x=eventDraggablePressed.getSceneX();
            y= eventDraggablePressed.getSceneY();
        }));

    }

    public void exitWindow() {
        stageGallery = (Stage) anchorParentGallery.getScene().getWindow();
        stageGallery.close();
    }

    public void minimizeWindow() {
        stageGallery = (Stage) anchorParentGallery.getScene().getWindow();
        stageGallery.setIconified(true);
    }
    private void initializeOfferteInviate(ListView<String> listViewOfferte, ArtGalleryBean gal) {
        ViewGallerySummary vgs = new ViewGallerySummary();
        List<ProposalBean> arrayOfProposal = vgs.retrieveGalleryProposal(gal, 0);
        for (ProposalBean n : arrayOfProposal) {
            ArtistBean artistProposal = vgs.retrieveArtistNameGallerySum(n);
            String artistNameProposal = artistProposal.getNome() + " " + artistProposal.getCognome();
            listViewOfferte.getItems().add("Offerta inviata per Artista :  " + artistNameProposal);  // Popola la listView.
        }
    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController.switchToSceneProfiloGallery(actionEvent,gal);
    }


}
