package com.example.testinterfaccia;

public class Nodo {
    String domanda;
    int idAppartenenza;
    int idProprio;
    String decisione;

    public Nodo(int newIdAppartenenza, int newIdProprio, String newDecisione, String newDomanda){
        this.domanda = newDomanda;
        this.idAppartenenza = newIdAppartenenza;
        this.idProprio = newIdProprio;
        this.decisione = newDecisione;
    }

    public int getIdAppartenenza(){ return idAppartenenza; }
    public int getIdProprio(){ return idProprio; }
    public String getDecisione(){ return decisione; }
    public String getDomanda(){ return domanda; }
}
