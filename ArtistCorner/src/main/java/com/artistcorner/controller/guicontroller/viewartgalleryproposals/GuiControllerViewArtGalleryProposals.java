package com.artistcorner.controller.guicontroller.viewartgalleryproposals;

import com.artistcorner.controller.applicationcontroller.ViewArtGalleryProposals;
import com.artistcorner.engclasses.bean.User;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Proposal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiControllerViewArtGalleryProposals {
    public ListView listViewProposal;
    public Label labelNomeGalleria;
    public Label labelDescrGalleria;
    public Label labelIndirizzoGalleria;
    public Button buttonAcceptProposal;
    public Button buttonRejectProposal;
    public Label labelLogOut;
    ViewArtGalleryProposals omlc = new ViewArtGalleryProposals();

    public AnchorPane anchorParent;
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public WebView webMap;
    private double x=0, y=0;
    private Stage stage;

    Artist art;

    public Pane paneInfoLoading;
    int currentProposalId;

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

    public void getArtist(Artist loggedArtist) {
        art = loggedArtist;
        populateListView(loggedArtist);
    }


    public void initialize() throws IOException {

        paneInfoLoading.setVisible(false);
        labelDescrGalleria.setMaxWidth(384);
        labelDescrGalleria.setWrapText(true);  // Per far andare a capo la linea.
        buttonAcceptProposal.setVisible(false);
        buttonRejectProposal.setVisible(false);

        makeDraggable();
        setTooltipMenu();
        makeLogOut();
    }

    /**
     * A seconda dell'indirizzo mostra a video una mappa Google Maps interattiva.
     */
    private void setWebMap(String luogo) {
        webMap.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/artist/webViewMap.css").toExternalForm());  // Elimina la scrollbar nella webView
        //String luogo="via palmanova 7 ardea";

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
        webMap.getEngine().loadContent(omlc.makeMapHtml(luogo));      // Carica l'html della pagina
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


    public void populateListView(Artist art){
        ArrayList<Proposal> arrayOfProposals = ArtistDAO.retrieveArtGalleryProposals(art.getIdArtista());
        ArrayList<ArtGallery> arrayOfArtGalleryOfProposal = new ArrayList<ArtGallery>();

        for (Proposal n : arrayOfProposals) {
            ArtGallery artG = ArtistDAO.retrieveArtGallery(n.getGalleria());   // Fai un retrieve della galleria associata alla proposta.
            listViewProposal.getItems().add(artG.getNome());  // Popola la listView.

            arrayOfArtGalleryOfProposal.add(artG); // Popola l'array con tutte le gallerie relative alle proposte dell'utente.
        }

        listViewProposal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                buttonAcceptProposal.setVisible(false);    // Reset della visualizzazione dei bottoni.
                buttonRejectProposal.setVisible(false);

                int index = listViewProposal.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.

                ArtGallery currentArtGallery = arrayOfArtGalleryOfProposal.get(index);   // Prende l'i-esima (index) galleria dall'array
                                                                                         // inizializzato precedentemente.
                setWebMap(currentArtGallery.getIndirizzo());

                Proposal currentProposal = arrayOfProposals.get(index);     // Salva l'id dell'offerta correntemente visualizzata.
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

    }


    public void acceptProposal(ActionEvent event) throws Exception {
        ArtistDAO.updateProposal(currentProposalId, 1);
    }

    public void rejectProposal(ActionEvent event) throws Exception {
        ArtistDAO.updateProposal(currentProposalId, 2);
    }


    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
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
}
