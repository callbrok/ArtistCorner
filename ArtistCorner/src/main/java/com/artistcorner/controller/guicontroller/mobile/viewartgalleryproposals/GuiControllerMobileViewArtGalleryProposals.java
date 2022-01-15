package com.artistcorner.controller.guicontroller.mobile.viewartgalleryproposals;

import com.artistcorner.controller.applicationcontroller.ViewArtGalleryProposals;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ProposalBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class GuiControllerMobileViewArtGalleryProposals {
    public AnchorPane anchorMain;
    public Label labelUsernameDisplay;
    public Pane paneInfoLoading;
    public WebView webMap;
    public ListView listViewProposal;
    public Pane paneExceptionLoad;
    private double x=0, y=0;
    private Stage stage;

    ViewArtGalleryProposals omlc = new ViewArtGalleryProposals();
    ArtistBean art;
    

    public void initialize(){
        paneInfoLoading.setVisible(false);
        
        makeDraggable();

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
        webMap.getEngine().loadContent(omlc.makeMapHtml(luogo, 438));      // Carica l'html della pagina
    }


    // Riprogetta la riga della ListView.
    public static class HBoxCell extends HBox {
        Label labelNome = new Label();
        Label labelDescrizione = new Label();
        Label labelIndirizzo = new Label();

        Button buttonAccept = new Button();
        Button buttonRecline = new Button();

        HBoxCell(String labelNewNome, String labelNewDescrizione, String labelNewIndirizzo, int flagAccettazione, int proposalId) {
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



            // action event
            EventHandler<ActionEvent> eventAccept = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        ArtistDAO.updateProposal(proposalId, 1);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };

            EventHandler<ActionEvent> eventDecline= new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        ArtistDAO.updateProposal(proposalId, 2);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };

            // when button is pressed
            buttonAccept.setOnAction(eventAccept);
            buttonRecline.setOnAction(eventDecline);


            HBox.setHgrow(vBoxInfo, Priority.ALWAYS);

            this.getChildren().addAll(vBoxInfo, vBoxAction);
        }
    }


    public void populateListView(ArtistBean art) throws IOException {
        ViewArtGalleryProposals wap = new ViewArtGalleryProposals();
        ArrayList<ProposalBean> arrayOfProposalsBean = null;

        try {
            arrayOfProposalsBean = wap.retrieveArtGalleryProposals(art);

            ArrayList<ArtGalleryBean> arrayOfArtGalleryOfProposal = new ArrayList<ArtGalleryBean>();

            for (ProposalBean n : arrayOfProposalsBean) {
                ArtGalleryBean artG = wap.retrieveArtGallery(n.getGalleria());   // Fai un retrieve della galleria associata alla proposta.

                listViewProposal.getItems().add(new HBoxCell(artG.getNome(), artG.getDescrizione(), artG.getIndirizzo(), n.getFlagAccettazione(), n.getIdOfferta()));  // Popola la listView.

                arrayOfArtGalleryOfProposal.add(artG); // Popola l'array con tutte le gallerie relative alle proposte dell'utente.
            }

            ArrayList<ProposalBean> finalArrayOfProposals = arrayOfProposalsBean;

            listViewProposal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {

                    int index = listViewProposal.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.

                    ArtGalleryBean currentArtGallery = arrayOfArtGalleryOfProposal.get(index);   // Prende l'i-esima (index) galleria dall'array
                    // inizializzato precedentemente.
                    setWebMap(currentArtGallery.getIndirizzo());

                    ProposalBean currentProposal = finalArrayOfProposals.get(index);     // Salva l'id dell'offerta correntemente visualizzata.

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
    

    public void getArtist(ArtistBean loggedArtist) throws IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        populateListView(loggedArtist);
    }

    public void exitWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stage = (Stage) anchorMain.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMain.setOnMousePressed(((event) -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMain.setOnMouseDragged(((event) -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneControllerMobile sc = new SceneControllerMobile();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }


}
