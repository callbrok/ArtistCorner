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
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerGetReccomandation implements Serializable{
    @FXML
    private TextFlow textFlowResult;
    @FXML
    private Button button1Gr;
    @FXML
    private Button button2Gr;
    @FXML
    private Button button3Gr;
    @FXML
    private Button button4Gr;
    @FXML
    private Button button5Gr;
    @FXML
    private Button button6Gr;
    @FXML
    private AnchorPane anchorParentReccD;
    @FXML
    private Label labelQuestion;
    @FXML
    private Button buttonReset;
    @FXML
    private AnchorPane anchorResultGr;
    @FXML
    private Label labelLogOut;
    @FXML
    private SVGPath svgProfile;
    @FXML
    private Label labelUsernameDisplay;

    private double x=0;
    private double y=0;
    private Stage stage;

    public static final String OBJECTNODO_PATH = "ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo.txt";
    public static final String FONT_RESULT = "System";

    private GetReccomandation lc = new GetReccomandation();
    private List<Nodo> arraylist = lc.initializeTreeTxt(); // Inizializza albero
    private int idLivello; // Variabile che tiene conto del livello corrente dell'albero
    private ArtistBean art;
    private Nodo n;


    /**
     * Deserializza lo stato dell'algoritmo di decisione, nel caso in cui il file contenente l'ultima istanza
     * serializzata ("object.txt") non fosse presente, inizializza l'algoritmo da zero.
     */
    public void inizializeIdLivello() throws IOException, ClassNotFoundException {
        // Controlla prima se c'è un file su cui fare al deserializzazione
        File f = new File(OBJECTNODO_PATH);

        if(f.exists() && !f.isDirectory()) { // Controlla l'esistenza del file object.txt
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(OBJECTNODO_PATH))) {
                String rispostaSerial = (String) in.readObject();
                Nodo c2 = (Nodo) in.readObject();

                lc.setSerialSolution(rispostaSerial); // Prende l'ultima istanza della soluzione
                labelQuestion.setText(c2.getDomanda()); // Prende la domanda dal nodo serializzato
                idLivello = c2.getIdProprio(); // Prende l'id del nodo serializzato

                if(idLivello == 0){showSolution();}
            }
        } else {
            idLivello = 1; // Inizializzazione dell'algoritmo al primo nodo
        }

    }

    private void makeDraggable(){
        anchorParentReccD.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParentReccD.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void exitWindow() {
        stage = (Stage) anchorParentReccD.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentReccD.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeLogOut(){
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
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(OBJECTNODO_PATH))) {
            out.writeObject(lc.getSerialSolution()); // Serializza l'ultima istanza di soluzione creata
            out.writeObject(n);  // Serializza l'ultimo nodo
        }
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

    public void showSolution() throws IOException {
        String[] soluzione = lc.getSolution();

        Text textPREResult1 = new Text("Ti consiglio di disegnare ");
        Text textResult1 = new Text("qualcosa (non hai risposto)");

        textResult1.setFill(Color.rgb(209, 62, 10));
        textPREResult1.setFill(Color.rgb(45, 132, 101));
        textResult1.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));
        textPREResult1.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));

        if(!soluzione[0].equals("Nessuna Risposta")){textResult1.setText(soluzione[0]);}

        Text textResult2 = new Text("");
        textResult2.setFill(Color.rgb(209, 62, 10));
        textResult2.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));

        if(!soluzione[1].equals("Nessuna Risposta")){textResult2.setText(" " + soluzione[1]);}

        Text textPREResult3 = new Text("");
        Text textResult3 = new Text("");

        textResult3.setFill(Color.rgb(209, 62, 10));
        textPREResult3.setFill(Color.rgb(45, 132, 101));
        textResult3.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));
        textPREResult3.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));

        if(!soluzione[2].equals("Nessuna Risposta")){textResult3.setText(soluzione[2]); textPREResult3.setText(" in moto ");}

        Text textPREResult4 = new Text("");
        Text textResult4 = new Text("");

        textResult4.setFill(Color.rgb(209, 62, 10));
        textPREResult4.setFill(Color.rgb(45, 132, 101));
        textResult4.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));
        textPREResult4.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));

        if(!soluzione[3].equals("Nessuna Risposta")){textResult4.setText(soluzione[3]); textPREResult4.setText(", dai colori ");}

        Text textPREResult5 = new Text("");
        Text textResult5 = new Text("");

        textResult5.setFill(Color.rgb(209, 62, 10));
        textPREResult5.setFill(Color.rgb(45, 132, 101));
        textResult5.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));
        textPREResult5.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 34));

        if(!soluzione[4].equals("Nessuna Risposta")){textResult5.setText(soluzione[4]); textPREResult5.setText(" con uno stile ");}


        textFlowResult.getChildren().addAll(textPREResult1, textResult1, textResult2, textPREResult3, textResult3, textPREResult4, textResult4, textPREResult5, textResult5);
        textFlowResult.setTextAlignment(TextAlignment.CENTER);

        anchorResultGr.setVisible(true);
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
        SceneController scrd = new SceneController();
        scrd.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneController scrd = new SceneController();
        scrd.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneController scrd = new SceneController();
        scrd.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneController scrd = new SceneController();
        scrd.switchToSceneProfiloOfferteMostre(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneController scrd = new SceneController();
        scrd.switchToSceneProfiloVenduto(event, art);
    }
}
