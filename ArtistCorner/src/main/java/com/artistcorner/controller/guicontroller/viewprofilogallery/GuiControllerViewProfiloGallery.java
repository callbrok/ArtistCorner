package com.artistcorner.controller.guicontroller.viewprofilogallery;

import com.artistcorner.controller.applicationcontroller.ViewProfileGallery;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.Proposal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.List;

public class GuiControllerViewProfiloGallery {

    @FXML
    public Label labelInviate;
    public AnchorPane anchorParent;
    public Label labelLogOut;
    public TextField textField;
    public Label labelUsernameDisplay;
    public Button button1;
    public Button button2;
    public Button button3;
    public TilePane tilePane;
    public TilePane tilePane2;
    public SVGPath svgProfile;
    public Label labelAttesa;
    @FXML
    private Pane paneExceptionLoad;
    private double x=0, y=0;
    Stage stage;
    ArtGalleryBean gal;


    public void initialize() throws SQLException, IOException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();
        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
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

    public void getGallery(ArtGalleryBean loggedGallery) throws SQLException, IOException {
        gal = loggedGallery;
        initializeTilePanePending(gal);
        initializeTilePaneAccepted(gal);
        labelUsernameDisplay.setText(gal.getNome());
    }

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

    public void exitWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.setIconified(true);
    }
    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Cerca Opera"));
    }
    public void switchToGallerySummary(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneGallerySummary(actionEvent,gal);
    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
    private void initializeTilePanePending(ArtGalleryBean gallery) {
        ViewProfileGallery vp = new ViewProfileGallery();
        try {
            List<ProposalBean> listOfProposal = vp.retrieveProposal(gallery, 0);
            tilePane.setHgap(20);
            tilePane.setVgap(10);
            for (ProposalBean b : listOfProposal) {


                String string = vp.retrieveArtistName(b.getArtista());

                SVGPath imageThumb = new SVGPath();
                imageThumb.setContent("M25 2.0078125C12.309296 2.0078125 2.0000002 12.317108 2 25.007812C2 37.112262 11.38131 47.043195 23.259766 47.935547C23.283185 47.93745 23.306613 47.939576 23.330078 47.941406C23.882405 47.981205 24.437631 48.007812 25 48.007812C25.562369 48.007812 26.117595 47.981205 26.669922 47.941406C26.693387 47.939576 26.716815 47.93745 26.740234 47.935547C38.61869 47.043195 48 37.112262 48 25.007812C48 12.317108 37.690704 2.0078125 25 2.0078125 z M 25 4.0078125C36.609824 4.0078125 46 13.397988 46 25.007812C46 30.739515 43.704813 35.924072 39.990234 39.710938C38.401074 38.55372 36.437194 37.863387 34.677734 37.246094C32.593734 36.516094 30.622172 35.824094 30.076172 34.621094C29.990172 33.594094 29.997859 32.792094 30.005859 31.871094L30.007812 31.480469C30.895813 30.635469 32.012531 28.852078 32.394531 27.205078C33.054531 26.853078 33.861516 26.009281 34.103516 23.988281C34.224516 22.985281 33.939062 22.2085 33.539062 21.6875C34.079062 19.8325 35.153484 15.136469 33.271484 12.105469C32.475484 10.824469 31.274313 10.016266 29.695312 9.6972656C28.808312 8.5992656 27.134484 8 24.896484 8C21.495484 8.063 19.002234 9.1047031 17.490234 11.095703C15.707234 13.445703 15.370328 16.996297 16.486328 21.654297C16.073328 22.175297 15.775438 22.963328 15.898438 23.986328C16.141438 26.007328 16.945469 26.851125 17.605469 27.203125C17.987469 28.852125 19.103188 30.635469 19.992188 31.480469L19.994141 31.861328C20.002141 32.786328 20.009828 33.590094 19.923828 34.621094C19.375828 35.827094 17.394781 36.526625 15.300781 37.265625C13.551886 37.88319 11.599631 38.574586 10.013672 39.716797C6.2962191 35.929504 4 30.742023 4 25.007812C4.0000002 13.397989 13.390176 4.0078125 25 4.0078125 z");
                Label nome = new Label();
                nome.setText(string);
                nome.setAlignment(Pos.CENTER);
                nome.setPrefSize(100, 20);
                VBox vBox = new VBox(imageThumb, nome);
                vBox.setSpacing(5);
                vBox.setAlignment(Pos.CENTER);
                vBox.setPrefSize(100, 100);

                tilePane.getChildren().add(vBox);   // Popola la tilePane.
            }
        }catch (SentProposalNotFoundException e){
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.PROPOSALNOTFOUND);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }

    }
    private void initializeTilePaneAccepted(ArtGalleryBean gallery) {
        ViewProfileGallery vp = new ViewProfileGallery();
        try {
            List<ProposalBean> listOfProposal = vp.retrieveProposal(gallery, 1);  // Prendi tutte le opere caricate dall'artista.
            tilePane2.setHgap(20);    // Setta i bordi orizzontali tra un tile e l'altro.
            tilePane2.setVgap(10);
            for (ProposalBean b : listOfProposal) {


                String string = vp.retrieveArtistName(b.getArtista());

                SVGPath imageThumb = new SVGPath();
                imageThumb.setContent("M25 2.0078125C12.309296 2.0078125 2.0000002 12.317108 2 25.007812C2 37.112262 11.38131 47.043195 23.259766 47.935547C23.283185 47.93745 23.306613 47.939576 23.330078 47.941406C23.882405 47.981205 24.437631 48.007812 25 48.007812C25.562369 48.007812 26.117595 47.981205 26.669922 47.941406C26.693387 47.939576 26.716815 47.93745 26.740234 47.935547C38.61869 47.043195 48 37.112262 48 25.007812C48 12.317108 37.690704 2.0078125 25 2.0078125 z M 25 4.0078125C36.609824 4.0078125 46 13.397988 46 25.007812C46 30.739515 43.704813 35.924072 39.990234 39.710938C38.401074 38.55372 36.437194 37.863387 34.677734 37.246094C32.593734 36.516094 30.622172 35.824094 30.076172 34.621094C29.990172 33.594094 29.997859 32.792094 30.005859 31.871094L30.007812 31.480469C30.895813 30.635469 32.012531 28.852078 32.394531 27.205078C33.054531 26.853078 33.861516 26.009281 34.103516 23.988281C34.224516 22.985281 33.939062 22.2085 33.539062 21.6875C34.079062 19.8325 35.153484 15.136469 33.271484 12.105469C32.475484 10.824469 31.274313 10.016266 29.695312 9.6972656C28.808312 8.5992656 27.134484 8 24.896484 8C21.495484 8.063 19.002234 9.1047031 17.490234 11.095703C15.707234 13.445703 15.370328 16.996297 16.486328 21.654297C16.073328 22.175297 15.775438 22.963328 15.898438 23.986328C16.141438 26.007328 16.945469 26.851125 17.605469 27.203125C17.987469 28.852125 19.103188 30.635469 19.992188 31.480469L19.994141 31.861328C20.002141 32.786328 20.009828 33.590094 19.923828 34.621094C19.375828 35.827094 17.394781 36.526625 15.300781 37.265625C13.551886 37.88319 11.599631 38.574586 10.013672 39.716797C6.2962191 35.929504 4 30.742023 4 25.007812C4.0000002 13.397989 13.390176 4.0078125 25 4.0078125 z");
                Label nome = new Label();
                nome.setText(string);
                nome.setAlignment(Pos.CENTER);
                nome.setPrefSize(100, 20);
                VBox vBox = new VBox(imageThumb, nome);
                vBox.setSpacing(5);
                vBox.setAlignment(Pos.CENTER);
                vBox.setPrefSize(100, 100);

                tilePane2.getChildren().add(vBox);   // Popola la tilePane.
            }
        }catch (SentProposalNotFoundException e){
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.SENTPROPOSALNOTFOUND);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }

    }
}
