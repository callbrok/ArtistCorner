package com.artistcorner.controller.guicontroller.managefollowedartist;


import com.artistcorner.controller.applicationcontroller.ManageFollowedArtist;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerManageFollowedArtist {
    @FXML
    private AnchorPane anchorParentProfGal;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private TilePane tilePaneAccepted;
    @FXML
    private SVGPath svgProfileGallery;
    @FXML
    private Label labelLogOutProf;
    @FXML
    private Label labelUsernameDisplayProf;
    @FXML
    private Pane paneException;

    private double x=0;
    private double y=0;

    @FXML
    private Label labelNameGallery;
    @FXML
    private Label labelDescriptionGallery;
    @FXML
    private Label labelAddressGallery;

    private Stage stageProfGal;
    private ArtGalleryBean gal;

    public void initialize() throws SQLException, IOException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();
        svgProfileGallery.setScaleX(0.07);
        svgProfileGallery.setScaleY(0.07);
    }

    public void getGallery(ArtGalleryBean loggedGallery) throws SQLException, IOException {
        gal = loggedGallery;
        initializeTilePaneArtist(gal);
        initializeDataGallery();
        labelUsernameDisplayProf.setText(gal.getNome());

    }

    private void makeDraggable(){
        anchorParentProfGal.setOnMousePressed((eventPressProf -> {
            x=eventPressProf.getSceneX();
            y= eventPressProf.getSceneY();
        }));

        anchorParentProfGal.setOnMouseDragged((eventDragProf -> {
            stageProfGal = (Stage) ((Node)eventDragProf.getSource()).getScene().getWindow();
            stageProfGal.setX(eventDragProf.getScreenX() - x);
            stageProfGal.setY(eventDragProf.getScreenY() - y);
        }));
    }

    public void makeLogOut(){
        labelLogOutProf.addEventHandler(MouseEvent.MOUSE_CLICKED, eventProf -> {
            try {
                SceneController.switchToLogin(eventProf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void exitWindow() {
        stageProfGal = (Stage) anchorParentProfGal.getScene().getWindow();
        stageProfGal.close();
    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button4.setTooltip(new Tooltip("Cerca Opera"));
    }
    public void minimizeWindow() {
        stageProfGal = (Stage) anchorParentProfGal.getScene().getWindow();
        stageProfGal.setIconified(true);
    }

    private void initializeTilePaneArtist(ArtGalleryBean gallery) {
        ManageFollowedArtist vp = new ManageFollowedArtist();
        try {
            tilePaneAccepted.setHgap(20);    // Setta i bordi orizzontali tra un tile e l'altro.
            tilePaneAccepted.setVgap(10);
            List<ProposalBean> listOfProposalAccetta = vp.retrieveProposal(gallery, 1);  // Prendi tutte le opere caricate dall'artista.

            for (ProposalBean proposB : listOfProposalAccetta) {
                String artName = vp.retrieveArtistName(proposB);
                SVGPath imageThumb = new SVGPath();
                imageThumb.setContent("M25 2.0078125C12.309296 2.0078125 2.0000002 12.317108 2 25.007812C2 37.112262 11.38131 47.043195 23.259766 47.935547C23.283185 47.93745 23.306613 47.939576 23.330078 47.941406C23.882405 47.981205 24.437631 48.007812 25 48.007812C25.562369 48.007812 26.117595 47.981205 26.669922 47.941406C26.693387 47.939576 26.716815 47.93745 26.740234 47.935547C38.61869 47.043195 48 37.112262 48 25.007812C48 12.317108 37.690704 2.0078125 25 2.0078125 z M 25 4.0078125C36.609824 4.0078125 46 13.397988 46 25.007812C46 30.739515 43.704813 35.924072 39.990234 39.710938C38.401074 38.55372 36.437194 37.863387 34.677734 37.246094C32.593734 36.516094 30.622172 35.824094 30.076172 34.621094C29.990172 33.594094 29.997859 32.792094 30.005859 31.871094L30.007812 31.480469C30.895813 30.635469 32.012531 28.852078 32.394531 27.205078C33.054531 26.853078 33.861516 26.009281 34.103516 23.988281C34.224516 22.985281 33.939062 22.2085 33.539062 21.6875C34.079062 19.8325 35.153484 15.136469 33.271484 12.105469C32.475484 10.824469 31.274313 10.016266 29.695312 9.6972656C28.808312 8.5992656 27.134484 8 24.896484 8C21.495484 8.063 19.002234 9.1047031 17.490234 11.095703C15.707234 13.445703 15.370328 16.996297 16.486328 21.654297C16.073328 22.175297 15.775438 22.963328 15.898438 23.986328C16.141438 26.007328 16.945469 26.851125 17.605469 27.203125C17.987469 28.852125 19.103188 30.635469 19.992188 31.480469L19.994141 31.861328C20.002141 32.786328 20.009828 33.590094 19.923828 34.621094C19.375828 35.827094 17.394781 36.526625 15.300781 37.265625C13.551886 37.88319 11.599631 38.574586 10.013672 39.716797C6.2962191 35.929504 4 30.742023 4 25.007812C4.0000002 13.397989 13.390176 4.0078125 25 4.0078125 z");
                Label nameA = new Label();
                nameA.setText(artName);
                nameA.setAlignment(Pos.CENTER);
                nameA.setMinWidth(100);
                SVGPath newSvgPath = new SVGPath();
                newSvgPath.setStyle("-fx-fill: #FF0000");
                newSvgPath.setContent("M7 5h17v16h-17l-7-7.972 7-8.028zm7 6.586l-2.586-2.586-1.414 1.414 2.586 2.586-2.586 2.586 1.414 1.414 2.586-2.586 2.586 2.586 1.414-1.414-2.586-2.586 2.586-2.586-1.414-1.414-2.586 2.586z");
                newSvgPath.setScaleX(0.7);
                newSvgPath.setScaleY(0.7);
                Button button = new Button("Rimuovi dai Seguiti",newSvgPath);
                button.setAlignment(Pos.CENTER);
                button.isWrapText();
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            vp.removeProposta(gallery,proposB.getArtista());
                            SceneController.switchToSceneSentArtGalleryProposal(arg0,gal);
                        } catch (SQLException | IOException | ProposalNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                VBox vBoxProf = new VBox(imageThumb, nameA,button);
                vBoxProf.setSpacing(5);
                vBoxProf.setAlignment(Pos.CENTER);

                tilePaneAccepted.getChildren().add(vBoxProf);   // Popola la tilePane.
            }
        }catch (SentProposalNotFoundException e){
            paneException.setVisible(true);
        }
    }
    public void initializeDataGallery(){
        labelNameGallery.setText(gal.getNome());
        labelAddressGallery.setText(gal.getIndirizzo());
        labelDescriptionGallery.setText(gal.getDescrizione());
    }
    public void switchToGallerySummary(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneGallerySummary(actionEvent,gal);
    }
    public void switchToSentArtGalleryProposal(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController.switchToSceneSentArtGalleryProposal(actionEvent,gal);
    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
}
