package com.artistcorner.controller.guicontroller.mobile.viewsentartgalleryproposal;

import com.artistcorner.controller.applicationcontroller.ViewSentArtGalleryProposal;
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

public class GuiControllerMobileViewSentArtGalleryProposal {
    @FXML
    private AnchorPane anchorMainProfGalMob;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private TilePane tilePanePendingMobile;
    @FXML
    private TilePane tilePaneAcceptedMobile;
    private double x=0;
    private double y=0;
    @FXML
    private Stage stageProfGal;
    private ArtGalleryBean gal;



    public void makeLogOut(ActionEvent event) throws IOException {
        SceneControllerMobile sm = new SceneControllerMobile();
        sm.switchToLogin(event);
    }
    public void minimizeWindow() {
        stageProfGal = (Stage) anchorMainProfGalMob.getScene().getWindow();
        stageProfGal.setIconified(true);
    }
    public void initialize(){
        makeDraggable();
    }

    public void exitWindow() {
        stageProfGal = (Stage) anchorMainProfGalMob.getScene().getWindow();
        stageProfGal.close();
    }

    private void makeDraggable(){
        anchorMainProfGalMob.setOnMousePressed((eventProfGalMob -> {
            x=eventProfGalMob.getSceneX();
            y= eventProfGalMob.getSceneY();
        }));

        anchorMainProfGalMob.setOnMouseDragged((eventProfGal2Mob -> {
            stageProfGal = (Stage) ((Node)eventProfGal2Mob.getSource()).getScene().getWindow();
            stageProfGal.setX(eventProfGal2Mob.getScreenX() - x);
            stageProfGal.setY(eventProfGal2Mob.getScreenY() - y);
        }));
    }
    public void getGallery(ArtGalleryBean loggedGallery) throws SQLException, IOException{
        gal = loggedGallery;      // Prendo le informazioni riguardanti la galleria che ha effettuato il login.
        labelUsernameDisplay.setText(gal.getNome());
        initializeTilePaneAccepted(gal);
        initializeTilePanePending(gal);
    }
    public void switchToSummaryGallery(ActionEvent actionEvent) throws IOException{
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneGallerySummary(actionEvent,gal);
    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloGallery(actionEvent,gal);
    }

    private void initializeTilePanePending(ArtGalleryBean galleryP) {
        ViewSentArtGalleryProposal vp = new ViewSentArtGalleryProposal();
        SceneControllerMobile sc = new SceneControllerMobile();
        try {
            tilePanePendingMobile.setHgap(20);
            tilePanePendingMobile.setVgap(10);
            List<ProposalBean> listOfProposalPending = vp.retrieveProposal(galleryP, 0); // prendi tutte le proposte pendenti
            for (ProposalBean proposal : listOfProposalPending) {
                String stringName = vp.retrieveArtistName(proposal);
                SVGPath imageThumb = new SVGPath();
                imageThumb.setContent("M25 2.0078125C12.309296 2.0078125 2.0000002 12.317108 2 25.007812C2 37.112262 11.38131 47.043195 23.259766 47.935547C23.283185 47.93745 23.306613 47.939576 23.330078 47.941406C23.882405 47.981205 24.437631 48.007812 25 48.007812C25.562369 48.007812 26.117595 47.981205 26.669922 47.941406C26.693387 47.939576 26.716815 47.93745 26.740234 47.935547C38.61869 47.043195 48 37.112262 48 25.007812C48 12.317108 37.690704 2.0078125 25 2.0078125 z M 25 4.0078125C36.609824 4.0078125 46 13.397988 46 25.007812C46 30.739515 43.704813 35.924072 39.990234 39.710938C38.401074 38.55372 36.437194 37.863387 34.677734 37.246094C32.593734 36.516094 30.622172 35.824094 30.076172 34.621094C29.990172 33.594094 29.997859 32.792094 30.005859 31.871094L30.007812 31.480469C30.895813 30.635469 32.012531 28.852078 32.394531 27.205078C33.054531 26.853078 33.861516 26.009281 34.103516 23.988281C34.224516 22.985281 33.939062 22.2085 33.539062 21.6875C34.079062 19.8325 35.153484 15.136469 33.271484 12.105469C32.475484 10.824469 31.274313 10.016266 29.695312 9.6972656C28.808312 8.5992656 27.134484 8 24.896484 8C21.495484 8.063 19.002234 9.1047031 17.490234 11.095703C15.707234 13.445703 15.370328 16.996297 16.486328 21.654297C16.073328 22.175297 15.775438 22.963328 15.898438 23.986328C16.141438 26.007328 16.945469 26.851125 17.605469 27.203125C17.987469 28.852125 19.103188 30.635469 19.992188 31.480469L19.994141 31.861328C20.002141 32.786328 20.009828 33.590094 19.923828 34.621094C19.375828 35.827094 17.394781 36.526625 15.300781 37.265625C13.551886 37.88319 11.599631 38.574586 10.013672 39.716797C6.2962191 35.929504 4 30.742023 4 25.007812C4.0000002 13.397989 13.390176 4.0078125 25 4.0078125 z");
                Label nome = new Label();
                nome.setText(stringName);
                nome.setAlignment(Pos.CENTER);
                nome.isWrapText();
                SVGPath newSvgPath = new SVGPath();
                newSvgPath.setContent("M7 5h17v16h-17l-7-7.972 7-8.028zm7 6.586l-2.586-2.586-1.414 1.414 2.586 2.586-2.586 2.586 1.414 1.414 2.586-2.586 2.586 2.586 1.414-1.414-2.586-2.586 2.586-2.586-1.414-1.414-2.586 2.586z");
                newSvgPath.setStyle("-fx-fill: #FF0000");
                newSvgPath.setScaleX(0.6);
                newSvgPath.setScaleY(0.6);
                Button button = new Button("Rimuovi dai Seguiti",newSvgPath);
                button.setAlignment(Pos.CENTER);
                button.isWrapText();
                button.getStyleClass().add("buttonRemove");
                double legnth = nome.getMaxWidth();
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            vp.removeProposta(galleryP,proposal.getArtista());
                            sc.switchToSceneSentArtGalleryProposal(arg0,gal);
                        } catch (SQLException | IOException | ProposalNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                VBox vBox = new VBox(imageThumb, nome,button);
                vBox.setSpacing(2.5);
                vBox.setAlignment(Pos.CENTER);

                tilePanePendingMobile.getChildren().add(vBox);   // Popola la tilePane.
            }
        }catch (SentProposalNotFoundException e ){
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.SENTPROPOSALNOTFOUND_MOBILE);
            tilePanePendingMobile.getChildren().add(ev.getExceptionPane());
        }
    }
    private void initializeTilePaneAccepted(ArtGalleryBean gallery)  {
        ViewSentArtGalleryProposal vp = new ViewSentArtGalleryProposal();
        try {
            List<ProposalBean> listOfProposalAccepted = vp.retrieveProposal(gallery, 1);  // Prendi tutte le proposte accettate.
            tilePaneAcceptedMobile.setHgap(20);    // Setta i bordi orizzontali tra un tile e l'altro.
            tilePaneAcceptedMobile.setVgap(10);
            for (ProposalBean proposalBean : listOfProposalAccepted) {
                String artistName = vp.retrieveArtistName(proposalBean);
                SVGPath imageThumb = new SVGPath();
                imageThumb.setContent("M25 2.0078125C12.309296 2.0078125 2.0000002 12.317108 2 25.007812C2 37.112262 11.38131 47.043195 23.259766 47.935547C23.283185 47.93745 23.306613 47.939576 23.330078 47.941406C23.882405 47.981205 24.437631 48.007812 25 48.007812C25.562369 48.007812 26.117595 47.981205 26.669922 47.941406C26.693387 47.939576 26.716815 47.93745 26.740234 47.935547C38.61869 47.043195 48 37.112262 48 25.007812C48 12.317108 37.690704 2.0078125 25 2.0078125 z M 25 4.0078125C36.609824 4.0078125 46 13.397988 46 25.007812C46 30.739515 43.704813 35.924072 39.990234 39.710938C38.401074 38.55372 36.437194 37.863387 34.677734 37.246094C32.593734 36.516094 30.622172 35.824094 30.076172 34.621094C29.990172 33.594094 29.997859 32.792094 30.005859 31.871094L30.007812 31.480469C30.895813 30.635469 32.012531 28.852078 32.394531 27.205078C33.054531 26.853078 33.861516 26.009281 34.103516 23.988281C34.224516 22.985281 33.939062 22.2085 33.539062 21.6875C34.079062 19.8325 35.153484 15.136469 33.271484 12.105469C32.475484 10.824469 31.274313 10.016266 29.695312 9.6972656C28.808312 8.5992656 27.134484 8 24.896484 8C21.495484 8.063 19.002234 9.1047031 17.490234 11.095703C15.707234 13.445703 15.370328 16.996297 16.486328 21.654297C16.073328 22.175297 15.775438 22.963328 15.898438 23.986328C16.141438 26.007328 16.945469 26.851125 17.605469 27.203125C17.987469 28.852125 19.103188 30.635469 19.992188 31.480469L19.994141 31.861328C20.002141 32.786328 20.009828 33.590094 19.923828 34.621094C19.375828 35.827094 17.394781 36.526625 15.300781 37.265625C13.551886 37.88319 11.599631 38.574586 10.013672 39.716797C6.2962191 35.929504 4 30.742023 4 25.007812C4.0000002 13.397989 13.390176 4.0078125 25 4.0078125 z");
                Label nomeArtista = new Label();
                nomeArtista.setText(artistName);
                nomeArtista.setAlignment(Pos.CENTER);
                nomeArtista.isWrapText();
                nomeArtista.setMinWidth(140);
                VBox vBox = new VBox(imageThumb, nomeArtista);
                vBox.setSpacing(5);
                vBox.setAlignment(Pos.CENTER);
                vBox.setMinWidth(100);

                tilePaneAcceptedMobile.getChildren().add(vBox);   // Popola la tilePane.
            }
        }catch (SentProposalNotFoundException e) {
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;
            ev = ef.createView(ExceptionsTypeManager.SENTPROPOSALNOTFOUND_MOBILE);
            tilePaneAcceptedMobile.getChildren().add(ev.getExceptionPane());

        }
    }
}
