package com.artistcorner.engclasses.bean;

import java.io.Serializable;

public class Nodo implements Serializable {
    String domanda;
    int idAppartenenza;
    int idProprio;
    String decisione;
    String solutionPart;
    String keyObj;

    public Nodo(int newIdAppartenenza, int newIdProprio, String newDecisione, String newDomanda, String newSolutionPart, String newKeyObj){
        this.domanda = newDomanda;
        this.idAppartenenza = newIdAppartenenza;
        this.idProprio = newIdProprio;
        this.decisione = newDecisione;
        this.keyObj = newKeyObj;
        this.solutionPart = newSolutionPart;
    }

    public int getIdAppartenenza(){ return idAppartenenza; }
    public int getIdProprio(){ return idProprio; }
    public String getDecisione(){ return decisione; }
    public String getDomanda(){ return domanda; }
    public String getKeyObj() {return keyObj;}
    public String getSolutionPart() {return solutionPart;}
}
