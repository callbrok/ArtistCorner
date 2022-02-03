package com.artistcorner.controller.guicontroller.viewartgalleryproposals;

import com.artistcorner.controller.applicationcontroller.ViewArtGalleryProposals;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerViewArtGalleryProposals {
    @FXML
    private ListView listViewProposal;
    @FXML
    private Label labelNomeGalleria;
    @FXML
    private Label labelDescrGalleria;
    @FXML
    private Label labelIndirizzoGalleria;
    @FXML
    private Button buttonAcceptProposal;
    @FXML
    private Button buttonRejectProposal;
    @FXML
    private Label labelLogOut;
    @FXML
    private Pane paneExceptionLoad;
    @FXML
    private SVGPath svgProfile;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private AnchorPane anchorParentPrpDesk;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private WebView webMap;
    @FXML
    private Pane paneInfoLoading;

    private double x=0;
    private double y=0;
    private Stage stage;

    private ViewArtGalleryProposals omlc = new ViewArtGalleryProposals();
    private ArtistBean art;
    private int currentProposalId;

    private void makeDraggable(){
        anchorParentPrpDesk.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParentPrpDesk.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void exitWindow() throws IOException {
        SceneController.deleteSerialNodo(art.getIdArtista());

        stage = (Stage) anchorParentPrpDesk.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentPrpDesk.getScene().getWindow();
        stage.setIconified(true);
    }

    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController scvp = new SceneController();
            try {
                SceneController.deleteSerialNodo(art.getIdArtista());
                scvp.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void getArtist(ArtistBean loggedArtist) throws IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        populateListView(loggedArtist);
    }


    public void initialize() throws IOException {

        paneInfoLoading.setVisible(false);
        labelDescrGalleria.setMaxWidth(384);
        labelDescrGalleria.setWrapText(true);  // Per far andare a capo la linea.
        buttonAcceptProposal.setVisible(false);
        buttonRejectProposal.setVisible(false);

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);

        makeDraggable();
        setTooltipMenu();
        makeLogOut();
    }

    /**
     * A seconda dell'indirizzo mostra a video una mappa Google Maps interattiva.
     */
    private void setWebMap(String luogo) {
        webMap.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/artist/webViewMap.css").toExternalForm());  // Elimina la scrollbar nella webView

        /**
         * L'html nella WebView viene caricato ed interpretato da un thread in background il cui progresso
         * rintracciabile tramite il metodo getLoadWorker().
         */
        webMap.getEngine().getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {       // Identifica il completamento del thread
                        paneInfoLoading.setVisible(false);          // A thread concluso disabilita il messaggio di caricamento
                    }
                });

        paneInfoLoading.setVisible(true);              // Durante l'esecuzione del thread visualizza un messaggio di caricamento
        webMap.getEngine().loadContent(omlc.makeMapHtml(luogo, 422));      // Carica l'html della pagina
    }

    /**
     * Setta i tooltip su i bottoni del menu.
     */
    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Profilo"));
        button3.setTooltip(new Tooltip("Carica Opera"));
        button4.setTooltip(new Tooltip("Offerte Mostre"));
        button5.setTooltip(new Tooltip("Opere Vendute"));
    }


    public void populateListView(ArtistBean art) throws IOException {
        ViewArtGalleryProposals wap = new ViewArtGalleryProposals();
        List<ProposalBean> arrayOfProposalsBean = null;

        try {
            arrayOfProposalsBean = wap.retrieveArtGalleryProposals(art);

            ArrayList<ArtGalleryBean> arrayOfArtGalleryOfProposal = new ArrayList<>();

            for (ProposalBean n : arrayOfProposalsBean) {
                ArtGalleryBean artG = wap.retrieveArtGallery(n);   // Fai un retrieve della galleria associata alla proposta.
                listViewProposal.getItems().add(artG.getNome());  // Popola la listView.

                arrayOfArtGalleryOfProposal.add(artG); // Popola l'array con tutte le gallerie relative alle proposte dell'utente.
            }

            List<ProposalBean> finalArrayOfProposals = arrayOfProposalsBean;

            listViewProposal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    buttonAcceptProposal.setVisible(false);    // Reset della visualizzazione dei bottoni.
                    buttonRejectProposal.setVisible(false);

                    int index = listViewProposal.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.

                    ArtGalleryBean currentArtGallery = arrayOfArtGalleryOfProposal.get(index);   // Prende l'i-esima (index) galleria dall'array
                                                                                             // inizializzato precedentemente.
                    setWebMap(currentArtGallery.getIndirizzo());

                    ProposalBean currentProposal = finalArrayOfProposals.get(index);     // Salva l'id dell'offerta correntemente visualizzata.
                    currentProposalId = currentProposal.getIdOfferta();

                    // Stati del flagAccettazione
                    //  0 : non inizializzato
                    //  1 : offerta accettata
                    //  2 : offerta rifiutata
                    if(currentProposal.getFlagAccettazione() == 0){
                        buttonAcceptProposal.setVisible(true);
                        buttonRejectProposal.setVisible(true);
                    }

                    labelNomeGalleria.setText(currentArtGallery.getNome());
                    labelDescrGalleria.setText(currentArtGallery.getDescrizione());
                    labelIndirizzoGalleria.setText(currentArtGallery.getIndirizzo());
                }
            });

        } catch (ProposalNotFoundException e) {
            // Eccezione: Nessuna proposta trovata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.PROPOSALNOTFOUND);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }

    }


    public void acceptProposal() throws SQLException {
        ArtistDAO.updateProposal(currentProposalId, 1);
    }

    public void rejectProposal() throws SQLException {
        ArtistDAO.updateProposal(currentProposalId, 2);
    }


    public void switchToSceneMainArtista(ActionEvent event) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }
}
