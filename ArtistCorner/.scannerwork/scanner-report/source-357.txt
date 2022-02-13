package com.artistcorner.engclasses.others;

import java.io.Serializable;

public class Nodo implements Serializable {
    String domanda;
    int idAppartenenza;
    int idProprio;
    String decisione;
    String solutionPart;
    String keyObj;
    String solutionS;


    public void setDomanda(String domanda) {this.domanda = domanda;}
    public void setIdAppartenenza(int idAppartenenza) {this.idAppartenenza = idAppartenenza;}
    public void setIdProprio(int idProprio) {this.idProprio = idProprio;}
    public void setDecisione(String decisione) {this.decisione = decisione;}
    public void setSolutionPart(String solutionPart) {this.solutionPart = solutionPart;}
    public void setKeyObj(String keyObj) {this.keyObj = keyObj;}
    public void setSolutionS(String solutionS) {this.solutionS = solutionS;}

    public int getIdAppartenenza(){ return idAppartenenza; }
    public int getIdProprio(){ return idProprio; }
    public String getDecisione(){ return decisione; }
    public String getDomanda(){ return domanda; }
    public String getKeyObj() {return keyObj;}
    public String getSolutionPart() {return solutionPart;}
    public String getSolutionS() {return solutionS;}
}
