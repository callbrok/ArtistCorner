package com.artistcorner.controller.guicontroller.mobile.manageproposals;

import com.artistcorner.controller.applicationcontroller.ManageProposals;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerMobileManageProposals {
    @FXML
    private AnchorPane anchorMainPropM;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Pane paneInfoLoading;
    @FXML
    private WebView webMap;
    @FXML
    private ListView listViewProposal;
    @FXML
    private Pane paneExceptionLoad;

    private double x=0;
    private double y=0;
    private Stage stage;

    private ManageProposals omlc = new ManageProposals();
    private ArtistBean art;
    

    public void initialize(){
        paneInfoLoading.setVisible(false);
        labelUsernameDisplay.setAlignment(Pos.CENTER);

        makeDraggable();

    }

    /**
     * A seconda dell'indirizzo mostra a video una mappa Google Maps interattiva.
     */
    private void setWebMap(String luogo) {
        webMap.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/webViewMap.css").toExternalForm());  // Elimina la scrollbar nella webView

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
        webMap.getEngine().loadContent(omlc.makeMapHtml(luogo, 438));      // Carica l'html della pagina
    }


    /**
     * Ridefinisce il template delle righe/celle della listView.
     */
    public static class HBoxCell extends HBox {
        Label labelNome = new Label();
        Label labelDescrizione = new Label();
        Label labelIndirizzo = new Label();

        Button buttonAccept = new Button();
        Button buttonRecline = new Button();

        HBoxCell(String labelNewNome, String labelNewDescrizione, String labelNewIndirizzo, int flagAccettazione, int proposalId, ArtistBean art) {
            super();

            labelNome.setText(labelNewNome);
            labelNome.setStyle("-fx-font-weight: bold");
            labelNome.setTextFill(Color.rgb(39, 116, 88));
            labelNome.setMaxWidth(Double.MAX_VALUE);

            labelDescrizione.setText(labelNewDescrizione);
            labelDescrizione.setMaxWidth(Double.MAX_VALUE);

            labelIndirizzo.setText(labelNewIndirizzo);
            labelIndirizzo.setMaxWidth(Double.MAX_VALUE);

            VBox vBoxInfo = new VBox(labelNome, labelDescrizione, labelIndirizzo);
            vBoxInfo.setSpacing(10);
            vBoxInfo.setAlignment(Pos.TOP_LEFT);

            buttonAccept.setText("Accetta");
            buttonRecline.setText("Rifiuta");

            buttonAccept.getStyleClass().add("buttonPane");
            buttonRecline.getStyleClass().add("buttonPane");

            buttonAccept.setMaxWidth(100);
            buttonRecline.setMaxWidth(100);

            VBox vBoxAction = new VBox(buttonAccept, buttonRecline);
            vBoxAction.setSpacing(10);
            vBoxAction.setAlignment(Pos.CENTER);

            buttonAccept.setVisible(false); // Reset della visualizzazione dei bottoni.
            buttonRecline.setVisible(false);

            // Stati del flagAccettazione
            //  0 : non inizializzato
            //  1 : offerta accettata
            //  2 : offerta rifiutata
            if(flagAccettazione == 0){
                buttonAccept.setVisible(true);
                buttonRecline.setVisible(true);
            }


            // Inizializza evento sul click del bottone "Accetta" proposta.
            EventHandler<ActionEvent> eventAccept = e -> {
                ProposalBean propToAccept = new ProposalBean();
                ManageProposals omlc = new ManageProposals();

                propToAccept.setFlagAccettazione(1);
                propToAccept.setIdOfferta(proposalId);

                try {
                    omlc.acceptProposal(propToAccept);
                    SceneControllerMobile.switchToSceneProfiloOfferteMostre(e, art);
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            };

            // Inizializza evento sul click del bottone "Rifiuta" proposta.
            EventHandler<ActionEvent> eventDecline= e -> {
                ProposalBean propToReject = new ProposalBean();
                ManageProposals omlc = new ManageProposals();

                propToReject.setFlagAccettazione(2);
                propToReject.setIdOfferta(proposalId);

                try {
                    omlc.rejectProposal(propToReject);
                    SceneControllerMobile.switchToSceneProfiloOfferteMostre(e, art);
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            };

            // Assegna l'evento.
            buttonAccept.setOnAction(eventAccept);
            buttonRecline.setOnAction(eventDecline);


            HBox.setHgrow(vBoxInfo, Priority.ALWAYS);

            this.getChildren().addAll(vBoxInfo, vBoxAction);
        }
    }

    /**
     * Inizializza la listView contenente tutte le proposte arrivate all'artista loggato.
     */
    public void populateListView(ArtistBean art) throws IOException {
        ManageProposals wap = new ManageProposals();
        List<ProposalBean> arrayOfProposalsBean = null;

        try {
            arrayOfProposalsBean = wap.retrieveArtGalleryProposals(art);

            ArrayList<ArtGalleryBean> arrayOfArtGalleryOfProposal = new ArrayList<>();

            for (ProposalBean n : arrayOfProposalsBean) {
                ArtGalleryBean artG = wap.retrieveArtGallery(n);   // Fai un retrieve della galleria associata alla proposta.

                listViewProposal.getItems().add(new HBoxCell(artG.getNome(), artG.getDescrizione(), artG.getIndirizzo(), n.getFlagAccettazione(), n.getIdOfferta(), art));  // Popola la listView.

                arrayOfArtGalleryOfProposal.add(artG); // Popola l'array con tutte le gallerie relative alle proposte dell'utente.
            }


            listViewProposal.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {

                int index = listViewProposal.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.

                ArtGalleryBean currentArtGallery = arrayOfArtGalleryOfProposal.get(index);   // Prende l'i-esima (index) galleria dall'array
                // inizializzato precedentemente.
                setWebMap(currentArtGallery.getIndirizzo());

            });

        } catch (ProposalNotFoundException e) {
            // Eccezione: Nessuna proposta trovata.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.PROPOSALNOTFOUND_MOBILE);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }

    }
    

    public void getArtist(ArtistBean loggedArtist) throws IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        populateListView(loggedArtist);
    }

    public void exitWindow() throws IOException {
        stage = (Stage) anchorMainPropM.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorMainPropM.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMainPropM.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMainPropM.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void switchToSceneMainArtistaFromManagePropM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtistaFromManagePropM(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOperaFromManagePropM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVendutoFromManagePropM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostreFromManagePropM(ActionEvent event) throws IOException {
        SceneControllerMobile.switchToSceneProfiloOfferteMostre(event, art);
    }


}
