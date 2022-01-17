package com.artistcorner.controller.guicontroller.getreccomandation;

import com.artistcorner.controller.applicationcontroller.GetReccomandation;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.Nodo;
import com.artistcorner.engclasses.exceptions.GetRaccomandationProblemException;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.Artist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerGetReccomandation implements Serializable{
    @FXML
    public Button button1Gr;
    @FXML
    public Button button2Gr;
    @FXML
    public Button button3Gr;
    @FXML
    public Button button4Gr;
    @FXML
    public Button button5Gr;
    @FXML
    public Button button6Gr;
    @FXML
    public AnchorPane anchorParent;
    @FXML
    public Label labelQuestion;
    @FXML
    public Button buttonReset;
    @FXML
    public AnchorPane anchorResultGr;
    @FXML
    public Label labelResultSoggettoGr;
    @FXML
    public Label labelResultCaractGr;
    @FXML
    public Label labelResultStatoGr;
    @FXML
    public Label labelResultColoriGr;
    @FXML
    public Label labelResultStileGr;
    @FXML
    public Label labelLogOut;
    @FXML
    public SVGPath svgProfile;
    @FXML
    public Label labelUsernameDisplay;

    private double x=0;
    private double y=0;
    private Stage stage;

    public static final String OBJECTNODO_PATH = "ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo.txt";

    GetReccomandation lc = new GetReccomandation();
    List<Nodo> arraylist = lc.initializeTreeTxt(); // Inizializza albero
    int idLivello; // Variabile che tiene conto del livello corrente dell'albero
    ArtistBean art;
    Nodo n;

    /**
     * Deserializza lo stato dell'algoritmo di decisione, nel caso in cui il file contenente l'ultima istanza
     * serializzata ("object.txt") non fosse presente, inizializza l'algoritmo da zero.
     */
    public void inizializeIdLivello() throws IOException, ClassNotFoundException {
        // Controlla prima se c'è un file su cui fare al deserializzazione
        File f = new File(OBJECTNODO_PATH);

        if(f.exists() && !f.isDirectory()) { // Controlla l'esistenza del file object.txt
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(OBJECTNODO_PATH));

            String rispostaSerial = (String)in.readObject();
            Nodo c2 = (Nodo)in.readObject();
            in.close();

            lc.setSerialSolution(rispostaSerial); // Prende l'ultima istanza della soluzione
            labelQuestion.setText(c2.getDomanda()); // Prende la domanda dal nodo serializzato
            idLivello = c2.getIdProprio(); // Prende l'id del nodo serializzato
        } else {
            idLivello = 1; // Inizializzazione dell'algoritmo al primo nodo
        }

    }

    private void makeDraggable(){
        anchorParent.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParent.setOnMouseDragged((event -> {
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


    public void initialize() throws IOException, ClassNotFoundException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();

        labelQuestion.setAlignment(Pos.CENTER);
        labelQuestion.setMaxWidth(526);
        labelQuestion.setWrapText(true);  // Per far andare a capo la linea.
        anchorResultGr.setVisible(false);

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);

        inizializeIdLivello();
    }

    /**
     * Setta i tooltip su i bottoni del menu.
     */
    public void setTooltipMenu(){
        button1Gr.setTooltip(new Tooltip("Home"));
        button2Gr.setTooltip(new Tooltip("Profilo"));
        button3Gr.setTooltip(new Tooltip("Carica Opera"));
        button4Gr.setTooltip(new Tooltip("Offerte Mostre"));
        button5Gr.setTooltip(new Tooltip("Opere Vendute"));
        button6Gr.setTooltip(new Tooltip("Cosa Disegno?"));
    }

    public void getArtist(ArtistBean loggedArtist) {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
    }

    /**
     * Serializza il nodo passato, come oggetto nel file "object.txt".
     */
    public void makeSerializable(Nodo n) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(OBJECTNODO_PATH));
        out.writeObject(lc.getSerialSolution()); // Serializza l'ultima istanza di soluzione creata
        out.writeObject(n);  // Serializza l'ultimo nodo
        out.close(); // Also flushes the output
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta negativa e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerNo() throws IOException, GetRaccomandationProblemException {
        // In caso di risposta negativa

        n = lc.decisionTree("N", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
                                                             // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}           // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());               // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                        // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                 // Serializza il nodo corrente
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta positiva e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerYes() throws IOException, GetRaccomandationProblemException {
        n = lc.decisionTree("Y", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
                                                             // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}           // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());               // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                        // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                 // Serializza il nodo corrente
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta randomica e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerRand() throws IOException, GetRaccomandationProblemException {
        n = lc.decisionTree("YN", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
                                                              // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}            // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());                // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                         // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                  // Serializza il nodo corrente
    }

    public void showSolution(){
        String[] soluzione = lc.getSolution();

        // Visualizza risposte.
        anchorResultGr.setVisible(true);
        labelResultSoggettoGr.setText(soluzione[0]);
        labelResultCaractGr.setText(soluzione[1]);
        labelResultStatoGr.setText(soluzione[2]);
        labelResultColoriGr.setText(soluzione[3]);
        labelResultStileGr.setText(soluzione[4]);
    }


    /**
     * Resetta l'algoritmo.
     */
    public void resetAlgo() throws IOException {
        Files.delete(Path.of(OBJECTNODO_PATH));    // Cerca il file contenente l'oggetto serializzato e lo elimina

        buttonReset.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {                // Ricarica la scena
            SceneController sc = new SceneController();
            try {
                sc.switchToSceneProfiloAlgoritmo(event, art);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloOfferteMostre(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloVenduto(event, art);
    }
}
