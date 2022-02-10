package com.artistcorner.controller.guicontroller.mobile.getreccomandation;

import com.artistcorner.controller.applicationcontroller.GetReccomandation;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.others.Nodo;
import com.artistcorner.engclasses.exceptions.GetRaccomandationProblemException;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.engclasses.others.SceneControllerMobile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerMobileGetReccomandation implements Serializable {
    @FXML
    private TextFlow textFlowResultMobile;
    @FXML
    private AnchorPane anchorMainMobile;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Label labelQuestion;
    @FXML
    private AnchorPane anchorResult;
    @FXML
    private Button buttonReset;

    public static final String FONT_RESULT = "System";
    public static final String NOTHING_ANSW = "Nessuna Risposta";

    private double x=0;
    private double y=0;
    private Stage stage;

    private GetReccomandation lcm = new GetReccomandation();
    private List<Nodo> arraylistM = lcm.initializeTreeTxt(); // Inizializza albero
    private int idLivelloMobile; // Variabile che tiene conto del livello corrente dell'albero
    private ArtistBean art;
    private Nodo n;


    /**
     * Deserializza lo stato dell'algoritmo di decisione, nel caso in cui il file contenente l'ultima istanza
     * serializzata ("object.txt") non fosse presente, inizializza l'algoritmo da zero.
     */
    public void inizializeIdLivello() throws IOException, ClassNotFoundException {
        Nodo deserialNode = lcm.deserializaStartNode(art);

        if(deserialNode.getSolutionPart() != null) {

            labelQuestion.setText(deserialNode.getDomanda()); // Prende la domanda dal nodo serializzato
            idLivelloMobile = deserialNode.getIdProprio(); // Prende l'id del nodo serializzato

            if(idLivelloMobile == 0){showSolution();}

        } else {
            idLivelloMobile = 1; // Inizializzazione dell'algoritmo al primo nodo
        }

    }
    
    public void initialize() throws IOException, ClassNotFoundException {
        makeDraggable();

        labelQuestion.setAlignment(Pos.CENTER);
        labelQuestion.setMaxWidth(362);
        labelQuestion.setWrapText(true);  // Per far andare a capo la linea.

        labelUsernameDisplay.setAlignment(Pos.CENTER);
        anchorResult.setVisible(false);
    }

    public void getArtist(ArtistBean loggedArtist) throws IOException, ClassNotFoundException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());

        inizializeIdLivello();
    }


    /**
     * Visualizza il nodo corretto a seconda della risposta negativa e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerNo() throws IOException, GetRaccomandationProblemException {
        // In caso di risposta negativa

        n = lcm.decisionTree("N", arraylistM, idLivelloMobile);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
        // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}           // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());               // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivelloMobile = n.getIdProprio();                        // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        lcm.makeSerializable(art, n);                                 // Serializza il nodo corrente
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta positiva e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerYes() throws IOException, GetRaccomandationProblemException {
        n = lcm.decisionTree("Y", arraylistM, idLivelloMobile);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
        // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}           // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());               // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivelloMobile = n.getIdProprio();                        // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        lcm.makeSerializable(art, n);                                  // Serializza il nodo corrente
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta randomica e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerRand() throws IOException, GetRaccomandationProblemException {
        n = lcm.decisionTree("YN", arraylistM, idLivelloMobile);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
        // (ritornato da decisionTree) relativo alla risposta fornita

        if(n.getIdProprio() == 0){showSolution();}            // Se viene tornato il nodo di fine albero, mostro la soluzione.

        labelQuestion.setText(n.getDomanda());                // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivelloMobile = n.getIdProprio();                         // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        lcm.makeSerializable(art, n);                                    // Serializza il nodo corrente
    }

    public void showSolution(){
        String[] soluzione = lcm.getSolution();

        Text textPREResult1Mob = new Text("Ti consiglio di disegnare ");
        Text textResult1Mob = new Text("qualcosa (non hai risposto)");

        textResult1Mob.setFill(Color.rgb(209, 62, 10));
        textPREResult1Mob.setFill(Color.rgb(45, 132, 101));
        textResult1Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));
        textPREResult1Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));

        if(!soluzione[0].equals(NOTHING_ANSW)){textResult1Mob.setText(soluzione[0]);}

        Text textResult2Mob = new Text("");
        textResult2Mob.setFill(Color.rgb(209, 62, 10));
        textResult2Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));

        if(!soluzione[1].equals(NOTHING_ANSW)){textResult2Mob.setText(" " + soluzione[1]);}

        Text textPREResult3Mob = new Text("");
        Text textResult3Mob = new Text("");

        textResult3Mob.setFill(Color.rgb(209, 62, 10));
        textPREResult3Mob.setFill(Color.rgb(45, 132, 101));
        textResult3Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));
        textPREResult3Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));

        if(!soluzione[2].equals(NOTHING_ANSW)){textResult3Mob.setText(soluzione[2]); textPREResult3Mob.setText(" in moto ");}

        Text textPREResult4Mob = new Text("");
        Text textResult4Mob = new Text("");

        textResult4Mob.setFill(Color.rgb(209, 62, 10));
        textPREResult4Mob.setFill(Color.rgb(45, 132, 101));
        textResult4Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));
        textPREResult4Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));

        if(!soluzione[3].equals(NOTHING_ANSW)){textResult4Mob.setText(soluzione[3]); textPREResult4Mob.setText(", dai colori ");}

        Text textPREResult5Mob = new Text("");
        Text textResult5Mob = new Text("");

        textResult5Mob.setFill(Color.rgb(209, 62, 10));
        textPREResult5Mob.setFill(Color.rgb(45, 132, 101));
        textResult5Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));
        textPREResult5Mob.setFont(Font.font(FONT_RESULT, FontWeight.BOLD, 20));

        if(!soluzione[4].equals(NOTHING_ANSW)){textResult5Mob.setText(soluzione[4]); textPREResult5Mob.setText(" con uno stile ");}


        textFlowResultMobile.getChildren().addAll(textPREResult1Mob, textResult1Mob, textResult2Mob, textPREResult3Mob, textResult3Mob, textPREResult4Mob, textResult4Mob, textPREResult5Mob, textResult5Mob);
        textFlowResultMobile.setTextAlignment(TextAlignment.CENTER);


        anchorResult.setVisible(true);
    }


    /**
     * Resetta l'algoritmo.
     */
    public void resetAlgo() throws IOException {
        SceneController.deleteSerialNodo(art.getIdArtista());   // Cerca il file contenente l'oggetto serializzato e lo elimina

        buttonReset.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {                // Ricarica la scena
            SceneControllerMobile sc = new SceneControllerMobile();
            try {
                sc.switchToSceneProfiloAlgoritmo(event, art);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }


    public void exitWindow() throws IOException {
        SceneController.deleteSerialNodo(art.getIdArtista());

        stage = (Stage) anchorMainMobile.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorMainMobile.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeDraggable(){
        anchorMainMobile.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorMainMobile.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        SceneControllerMobile scmg = new SceneControllerMobile();
        scmg.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneControllerMobile scmg = new SceneControllerMobile();
        scmg.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneControllerMobile scmg = new SceneControllerMobile();
        scmg.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloVenduto(ActionEvent event) throws IOException {
        SceneControllerMobile scmg = new SceneControllerMobile();
        scmg.switchToSceneProfiloVenduto(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneControllerMobile scmg = new SceneControllerMobile();
        scmg.switchToSceneProfiloOfferteMostre(event, art);
    }
}
