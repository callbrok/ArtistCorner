package com.artistcorner.controller.guicontroller.getreccomandation;

import com.artistcorner.controller.applicationcontroller.GetReccomandation;
import com.artistcorner.engclasses.bean.Nodo;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class GuiControllerGetReccomandation implements Serializable{
    @FXML
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;

    public AnchorPane anchorParent;
    public Label labelQuestion;
    public Button buttonReset;

    private double x=0, y=0;
    private Stage stage;

    GetReccomandation lc = new GetReccomandation();
    ArrayList<Nodo> arraylist = lc.initializeTreeTxt(); // Inizializza albero
    int idLivello; // Variabile che tiene conto del livello corrente dell'albero
    Nodo n;

    /**
     * Deserializza lo stato dell'algoritmo di decisione, nel caso in cui il file contenente l'ultima istanza
     * serializzata ("object.txt") non fosse presente, inizializza l'algoritmo da zero.
     */
    public void inizializeIdLivello() throws IOException, ClassNotFoundException {
        // Controlla prima se c'è un file su cui fare al deserializzazione
        File f = new File("ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo.txt");

        if(f.exists() && !f.isDirectory()) { // Controlla l'esistenza del file object.txt
            System.out.println("Retriving the Serialized Object nodo\n");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo.txt"));

            String s = (String)in.readObject();
            Nodo c2 = (Nodo)in.readObject();
            System.out.println(s + "livello recuperato = " + c2.getIdProprio());
            in.close();

            System.out.println(c2.getDomanda());
            labelQuestion.setText(c2.getDomanda()); // Prende la domanda dal nodo serializzato
            idLivello = c2.getIdProprio(); // Prende l'id del nodo serializzato
        } else {
            idLivello = 1; // Inizializzazione dell'algoritmo al primo nodo
        }

    }

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


    public void initialize() throws IOException, ClassNotFoundException {
        makeDraggable();
        setTooltipMenu();
        labelQuestion.setAlignment(Pos.CENTER);

        inizializeIdLivello();
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
        button6.setTooltip(new Tooltip("Cosa Disegno?"));
    }

    public void switchToMainArtista(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneMainArtista(actionEvent);
    }

    public void switchToProfiloArtista(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloArtista(actionEvent);
    }

    /**
     * Serializza il nodo passato, come oggetto nel file "object.txt".
     */
    public void makeSerializable(Nodo n) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo.txt"));
        System.out.println("idlivello proprio : "+ n.getIdProprio()+ "\n");
        out.writeObject("Ultimo nodo:\n");
        out.writeObject(n);
        out.close(); // Also flushes the output
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta negativa e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerNo(ActionEvent actionEvent) throws IOException {
        // In caso di risposta negativa

        n = lc.decisionTree("N", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
                                                             // (ritornato da decisionTree) relativo alla risposta fornita

        labelQuestion.setText(n.getDomanda());               // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                        // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                 // Serializza il nodo corrente
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta positiva e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerYes(ActionEvent actionEvent) throws IOException {
        n = lc.decisionTree("Y", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
                                                             // (ritornato da decisionTree) relativo alla risposta fornita

        labelQuestion.setText(n.getDomanda());               // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                        // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                 // Serializza il nodo corrente
    }

    /**
     * Visualizza il nodo corretto a seconda della risposta randomica e serializza il nodo corrente
     * per permettere all'utente, in caso di uscita, di ritornare al passo corrente dell'algoritmo.
     */
    public void setAnswerRand(ActionEvent actionEvent) throws IOException {
        n = lc.decisionTree("YN", arraylist, idLivello);  // Assegna all'oggetto n di tipo Nodo, il nodo figlio
                                                              // (ritornato da decisionTree) relativo alla risposta fornita

        labelQuestion.setText(n.getDomanda());                // Setta la label con la domanda ricavata dal nodo figlio ritornato
        idLivello = n.getIdProprio();                         // Aggiorna il livello attuale con l'id del nodo figlio ritornato, ergo il nodo corrente

        makeSerializable(n);                                  // Serializza il nodo corrente
    }


    public void switchToProfiloOfferteMostre(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloOfferteMostre(actionEvent);
    }

    public void switchToProfiloVenduto(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloVenduto(actionEvent);
    }

    /**
     * Resetta l'algoritmo.
     */
    public void resetAlgo() throws IOException {
        File f = new File("ArtistCorner/src/main/resources/auxiliaryfacilities/objectNodo.txt");    // Cerca il file contenente l'oggetto serializzato e
        f.delete();                                                                             // lo elimina

        buttonReset.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {                // Ricarica la scena
            SceneController sc = new SceneController();
            try {
                sc.switchToSceneProfiloAlgoritmo(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
