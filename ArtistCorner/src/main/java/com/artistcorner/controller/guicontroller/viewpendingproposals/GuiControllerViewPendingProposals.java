package com.artistcorner.controller.guicontroller.viewpendingproposals;


import com.artistcorner.controller.applicationcontroller.ViewPendingProposals;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
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

public class GuiControllerViewPendingProposals {
    @FXML
    private AnchorPane anchorParentProfGal;
    @FXML
    private Label labelLogOut;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private TilePane tilePanePending;
    @FXML
    private TilePane tilePaneAcceptedSent;
    @FXML
    private SVGPath svgProfileGallery;
    @FXML
    private Pane paneExceptionLoad;

    private double x=0;
    private double y=0;
    private Stage stage;
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
        initializeTilePanePending(gal);
        initializeTilePaneAccepted(gal);
        labelUsernameDisplay.setText(gal.getNome());
    }

    private void makeDraggable(){
        anchorParentProfGal.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParentProfGal.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                SceneController.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void exitWindow() {
        stage = (Stage) anchorParentProfGal.getScene().getWindow();
        stage.close();
    }

    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Cerca Opera"));
    }
    public void minimizeWindow() {
        stage = (Stage) anchorParentProfGal.getScene().getWindow();
        stage.setIconified(true);
    }
    public void switchToGallerySummary(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneGallerySummary(actionEvent,gal);
    }
    public void switchToSearchArtWorkGallery(ActionEvent actionEvent) throws IOException {
        SceneController.switchToSceneSearchArtWorkGallery(actionEvent,gal);
    }
    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController.switchToSceneProfiloGallery(actionEvent,gal);
    }
    private void initializeTilePanePending(ArtGalleryBean gallery) {
        ViewPendingProposals vp = new ViewPendingProposals();
        try {
            List<ProposalBean> listOfProposalAttesa = vp.retrieveProposal(gallery, 0);
            tilePanePending.setHgap(20);
            tilePanePending.setVgap(10);
            for (ProposalBean propPend : listOfProposalAttesa) {
                String string = vp.retrieveArtistName(propPend);

                SVGPath imageThumb = new SVGPath();
                imageThumb.setContent("M25 2.0078125C12.309296 2.0078125 2.0000002 12.317108 2 25.007812C2 37.112262 11.38131 47.043195 23.259766 47.935547C23.283185 47.93745 23.306613 47.939576 23.330078 47.941406C23.882405 47.981205 24.437631 48.007812 25 48.007812C25.562369 48.007812 26.117595 47.981205 26.669922 47.941406C26.693387 47.939576 26.716815 47.93745 26.740234 47.935547C38.61869 47.043195 48 37.112262 48 25.007812C48 12.317108 37.690704 2.0078125 25 2.0078125 z M 25 4.0078125C36.609824 4.0078125 46 13.397988 46 25.007812C46 30.739515 43.704813 35.924072 39.990234 39.710938C38.401074 38.55372 36.437194 37.863387 34.677734 37.246094C32.593734 36.516094 30.622172 35.824094 30.076172 34.621094C29.990172 33.594094 29.997859 32.792094 30.005859 31.871094L30.007812 31.480469C30.895813 30.635469 32.012531 28.852078 32.394531 27.205078C33.054531 26.853078 33.861516 26.009281 34.103516 23.988281C34.224516 22.985281 33.939062 22.2085 33.539062 21.6875C34.079062 19.8325 35.153484 15.136469 33.271484 12.105469C32.475484 10.824469 31.274313 10.016266 29.695312 9.6972656C28.808312 8.5992656 27.134484 8 24.896484 8C21.495484 8.063 19.002234 9.1047031 17.490234 11.095703C15.707234 13.445703 15.370328 16.996297 16.486328 21.654297C16.073328 22.175297 15.775438 22.963328 15.898438 23.986328C16.141438 26.007328 16.945469 26.851125 17.605469 27.203125C17.987469 28.852125 19.103188 30.635469 19.992188 31.480469L19.994141 31.861328C20.002141 32.786328 20.009828 33.590094 19.923828 34.621094C19.375828 35.827094 17.394781 36.526625 15.300781 37.265625C13.551886 37.88319 11.599631 38.574586 10.013672 39.716797C6.2962191 35.929504 4 30.742023 4 25.007812C4.0000002 13.397989 13.390176 4.0078125 25 4.0078125 z");
                Label nome = new Label();
                nome.setText(string);
                nome.isWrapText();
                nome.setAlignment(Pos.CENTER);
                nome.setContentDisplay(ContentDisplay.CENTER);
                SVGPath newSvgPath = new SVGPath();
                newSvgPath.setStyle("-fx-fill: #FF0000");
                newSvgPath.setContent("M7 5h17v16h-17l-7-7.972 7-8.028zm7 6.586l-2.586-2.586-1.414 1.414 2.586 2.586-2.586 2.586 1.414 1.414 2.586-2.586 2.586 2.586 1.414-1.414-2.586-2.586 2.586-2.586-1.414-1.414-2.586 2.586z");
                newSvgPath.setScaleX(0.7);
                newSvgPath.setScaleY(0.7);
                Button button = new Button("Ritira Proposta",newSvgPath);
                button.setAlignment(Pos.CENTER);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            vp.removeProposta(gallery,propPend.getArtista());
                            SceneController.switchToSceneSentArtGalleryProposal(arg0,gal);
                        } catch (SQLException | IOException | ProposalNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                VBox vBoxImg = new VBox(imageThumb, nome,button);
                vBoxImg.setSpacing(5);

                vBoxImg.setAlignment(Pos.CENTER);

                tilePanePending.getChildren().add(vBoxImg);   // Popola la tilePane.
            }
        }catch (SentProposalNotFoundException e){
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.SENTPROPOSALNOTFOUND);
            tilePanePending.getChildren().add(ev.getExceptionPane());
        }

    }
    private void initializeTilePaneAccepted(ArtGalleryBean gallery) {
        ViewPendingProposals vp = new ViewPendingProposals();
        try {
            tilePaneAcceptedSent.setHgap(20);    // Setta i bordi orizzontali tra un tile e l'altro.
            tilePaneAcceptedSent.setVgap(10);
            List<ProposalBean> listOfSentGalProposalAccetta = vp.retrieveProposal(gallery, 1);  // Prendi tutte le opere caricate dall'artista.

            for (ProposalBean propSent : listOfSentGalProposalAccetta) {
                String artNameSent = vp.retrieveArtistName(propSent);
                SVGPath imageThumbSent = new SVGPath();
                imageThumbSent.setContent("M25 2.0078125C12.309296 2.0078125 2.0000002 12.317108 2 25.007812C2 37.112262 11.38131 47.043195 23.259766 47.935547C23.283185 47.93745 23.306613 47.939576 23.330078 47.941406C23.882405 47.981205 24.437631 48.007812 25 48.007812C25.562369 48.007812 26.117595 47.981205 26.669922 47.941406C26.693387 47.939576 26.716815 47.93745 26.740234 47.935547C38.61869 47.043195 48 37.112262 48 25.007812C48 12.317108 37.690704 2.0078125 25 2.0078125 z M 25 4.0078125C36.609824 4.0078125 46 13.397988 46 25.007812C46 30.739515 43.704813 35.924072 39.990234 39.710938C38.401074 38.55372 36.437194 37.863387 34.677734 37.246094C32.593734 36.516094 30.622172 35.824094 30.076172 34.621094C29.990172 33.594094 29.997859 32.792094 30.005859 31.871094L30.007812 31.480469C30.895813 30.635469 32.012531 28.852078 32.394531 27.205078C33.054531 26.853078 33.861516 26.009281 34.103516 23.988281C34.224516 22.985281 33.939062 22.2085 33.539062 21.6875C34.079062 19.8325 35.153484 15.136469 33.271484 12.105469C32.475484 10.824469 31.274313 10.016266 29.695312 9.6972656C28.808312 8.5992656 27.134484 8 24.896484 8C21.495484 8.063 19.002234 9.1047031 17.490234 11.095703C15.707234 13.445703 15.370328 16.996297 16.486328 21.654297C16.073328 22.175297 15.775438 22.963328 15.898438 23.986328C16.141438 26.007328 16.945469 26.851125 17.605469 27.203125C17.987469 28.852125 19.103188 30.635469 19.992188 31.480469L19.994141 31.861328C20.002141 32.786328 20.009828 33.590094 19.923828 34.621094C19.375828 35.827094 17.394781 36.526625 15.300781 37.265625C13.551886 37.88319 11.599631 38.574586 10.013672 39.716797C6.2962191 35.929504 4 30.742023 4 25.007812C4.0000002 13.397989 13.390176 4.0078125 25 4.0078125 z");
                Label nameSent = new Label();
                nameSent.setText(artNameSent);
                nameSent.setAlignment(Pos.CENTER);
                nameSent.setContentDisplay(ContentDisplay.CENTER);
                nameSent.setMinWidth(120);
                VBox vBoxSent = new VBox(imageThumbSent, nameSent);
                vBoxSent.setSpacing(5);
                vBoxSent.setAlignment(Pos.CENTER);

                tilePaneAcceptedSent.getChildren().add(vBoxSent);   // Popola la tilePane.
            }
        }catch (SentProposalNotFoundException e){
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.SENTPROPOSALNOTFOUND);
            tilePaneAcceptedSent.getChildren().add(ev.getExceptionPane());
        }

    }
}
