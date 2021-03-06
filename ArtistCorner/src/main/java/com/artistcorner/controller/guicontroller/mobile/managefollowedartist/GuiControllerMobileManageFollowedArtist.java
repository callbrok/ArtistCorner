package com.artistcorner.controller.guicontroller.mobile.managefollowedartist;

import com.artistcorner.controller.applicationcontroller.ManageFollowedArtist;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileManageFollowedArtist {
    @FXML
    private AnchorPane anchorMainProfGal;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private TilePane tilePaneAcceptedMob;
    @FXML
    private Label labelNameGallery;
    @FXML
    private Label labelDescriptionGallery;
    @FXML
    private Label labelAddressGallery;
    @FXML
    private Stage stageProfGal;

    private double x=0;
    private double y=0;
    private ArtGalleryBean gal;


    public void minimizeWindow() {
        stageProfGal = (Stage) anchorMainProfGal.getScene().getWindow();
        stageProfGal.setIconified(true);
    }
    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToLogin(event);
    }
    public void initialize(){
        makeDraggable();
    }

    private void makeDraggable(){
        anchorMainProfGal.setOnMousePressed((eventProfGal -> {
            x=eventProfGal.getSceneX();
            y= eventProfGal.getSceneY();
        }));

        anchorMainProfGal.setOnMouseDragged((eventProfGal2 -> {
            stageProfGal = (Stage) ((Node)eventProfGal2.getSource()).getScene().getWindow();
            stageProfGal.setX(eventProfGal2.getScreenX() - x);
            stageProfGal.setY(eventProfGal2.getScreenY() - y);
        }));
    }
    public void exitWindow() {
        stageProfGal = (Stage) anchorMainProfGal.getScene().getWindow();
        stageProfGal.close();
    }
    public void getGallery(ArtGalleryBean loggedGallery) throws SQLException, IOException{
        gal = loggedGallery;      // Prendo le informazioni riguardanti la galleria che ha effettuato il login.
        labelUsernameDisplay.setText(gal.getNome());
        initializeTilePaneAccepted(gal);
        initializeDataGalleryMobile(gal);
    }
    public void switchToSummaryGallery(ActionEvent actionEvent) throws IOException{
        SceneControllerMobile.switchToSceneGallerySummary(actionEvent,gal);
    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
    public void switchToSentArtGalleryProposal(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile.switchToSceneSentArtGalleryProposal(actionEvent,gal);
    }


    private void initializeTilePaneAccepted(ArtGalleryBean gallery)  {
        ManageFollowedArtist vp = new ManageFollowedArtist();
        try {
            List<ProposalBean> listOfProposalAcceptedMob = vp.retrieveProposal(gallery, 1);  // Prendi tutte le proposte accettate.
            tilePaneAcceptedMob.setHgap(20);  // Setta i bordi orizzontali tra un tile e l'altro.
            tilePaneAcceptedMob.setVgap(10);
            for (ProposalBean proposalBean : listOfProposalAcceptedMob) {
                String artistNameProfMob = vp.retrieveArtistName(proposalBean);
                SVGPath imageThumbProfMob = new SVGPath();
                imageThumbProfMob.setContent("M25 2.0078125C12.309296 2.0078125 2.0000002 12.317108 2 25.007812C2 37.112262 11.38131 47.043195 23.259766 47.935547C23.283185 47.93745 23.306613 47.939576 23.330078 47.941406C23.882405 47.981205 24.437631 48.007812 25 48.007812C25.562369 48.007812 26.117595 47.981205 26.669922 47.941406C26.693387 47.939576 26.716815 47.93745 26.740234 47.935547C38.61869 47.043195 48 37.112262 48 25.007812C48 12.317108 37.690704 2.0078125 25 2.0078125 z M 25 4.0078125C36.609824 4.0078125 46 13.397988 46 25.007812C46 30.739515 43.704813 35.924072 39.990234 39.710938C38.401074 38.55372 36.437194 37.863387 34.677734 37.246094C32.593734 36.516094 30.622172 35.824094 30.076172 34.621094C29.990172 33.594094 29.997859 32.792094 30.005859 31.871094L30.007812 31.480469C30.895813 30.635469 32.012531 28.852078 32.394531 27.205078C33.054531 26.853078 33.861516 26.009281 34.103516 23.988281C34.224516 22.985281 33.939062 22.2085 33.539062 21.6875C34.079062 19.8325 35.153484 15.136469 33.271484 12.105469C32.475484 10.824469 31.274313 10.016266 29.695312 9.6972656C28.808312 8.5992656 27.134484 8 24.896484 8C21.495484 8.063 19.002234 9.1047031 17.490234 11.095703C15.707234 13.445703 15.370328 16.996297 16.486328 21.654297C16.073328 22.175297 15.775438 22.963328 15.898438 23.986328C16.141438 26.007328 16.945469 26.851125 17.605469 27.203125C17.987469 28.852125 19.103188 30.635469 19.992188 31.480469L19.994141 31.861328C20.002141 32.786328 20.009828 33.590094 19.923828 34.621094C19.375828 35.827094 17.394781 36.526625 15.300781 37.265625C13.551886 37.88319 11.599631 38.574586 10.013672 39.716797C6.2962191 35.929504 4 30.742023 4 25.007812C4.0000002 13.397989 13.390176 4.0078125 25 4.0078125 z");
                Label nomeArtistaProfMob = new Label();
                nomeArtistaProfMob.setText(artistNameProfMob);
                nomeArtistaProfMob.setAlignment(Pos.CENTER);
                nomeArtistaProfMob.isWrapText();
                SVGPath newSvgPath = new SVGPath();
                newSvgPath.setContent("M7 5h17v16h-17l-7-7.972 7-8.028zm7 6.586l-2.586-2.586-1.414 1.414 2.586 2.586-2.586 2.586 1.414 1.414 2.586-2.586 2.586 2.586 1.414-1.414-2.586-2.586 2.586-2.586-1.414-1.414-2.586 2.586z");
                newSvgPath.setScaleX(0.6);
                newSvgPath.setScaleY(0.6);
                newSvgPath.setStyle("-fx-fill: #FF0000");
                Button button = new Button("Rimuovi dai Seguiti",newSvgPath);
                button.setAlignment(Pos.CENTER);
                button.isWrapText();
                button.getStyleClass().add("buttonRemove");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                           vp.removeProposta(gallery,proposalBean.getArtista());
                            SceneControllerMobile.switchToSceneSentArtGalleryProposal(arg0,gal);
                        } catch (SQLException | IOException | ProposalNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                VBox vBoxMob = new VBox(imageThumbProfMob, nomeArtistaProfMob,button);
                vBoxMob.setSpacing(2.5);
                vBoxMob.setAlignment(Pos.CENTER);

                tilePaneAcceptedMob.getChildren().add(vBoxMob);   // Popola la tilePane.
            }
        }catch (SentProposalNotFoundException e) {
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;
            ev = ef.createView(ExceptionsTypeManager.SENTPROPOSALNOTFOUND_MOBILE);
            tilePaneAcceptedMob.getChildren().add(ev.getExceptionPane());

        }
    }
    public void initializeDataGalleryMobile(ArtGalleryBean gal){
        labelAddressGallery.setText(gal.getIndirizzo());
        labelDescriptionGallery.setText(gal.getDescrizione());
        labelNameGallery.setText(gal.getNome());
    }

}


