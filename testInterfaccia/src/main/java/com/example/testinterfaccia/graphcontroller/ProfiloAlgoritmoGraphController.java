package com.example.testinterfaccia.graphcontroller;

import com.example.testinterfaccia.Nodo;
import com.example.testinterfaccia.SceneController;
import com.example.testinterfaccia.logiccontroller.ProfiloAlgoritmoLogicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ProfiloAlgoritmoGraphController {
    @FXML
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;

    public AnchorPane anchorParent;
    public Label labelQuestion;

    private double x=0, y=0;
    private Stage stage;

    ProfiloAlgoritmoLogicController lc = new ProfiloAlgoritmoLogicController();
    ArrayList<Nodo> arraylist = lc.initializeTreeTxt(); // Inizializza Albero
    int idLivello = 1;    // Variabile che tiene conto del livello corrente dell'albero
    Nodo n;


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


    public void initialize() throws IOException {
        makeDraggable();
        setTooltipMenu();
        labelQuestion.setAlignment(Pos.CENTER);
    }

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


    public void setAnswerNo(ActionEvent actionEvent) {
        // In caso di risposta negativa

        n = lc.decisionTree("N", arraylist, idLivello);  // n prende il nodo figlio relativo alla risposta data (NEGATIVA in questo caso)
        labelQuestion.setText(n.getDomanda());               // mostro a schermo la domanda relativa al nodo corrente ritornato da decisionTree
        idLivello = n.getIdProprio();                        // viene aggiornato il livello attuale con l'id del nodo corrente
    }

    public void setAnswerYes(ActionEvent actionEvent) {
        n = lc.decisionTree("Y", arraylist, idLivello);
        labelQuestion.setText(n.getDomanda());
        idLivello = n.getIdProprio();
    }

    public void setAnswerRand(ActionEvent actionEvent) {
        n = lc.decisionTree("YN", arraylist, idLivello);
        labelQuestion.setText(n.getDomanda());
        idLivello = n.getIdProprio();
    }

}
