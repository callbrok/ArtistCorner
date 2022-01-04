package com.artistcorner.model;

public class ArtWork {
    int idOpera;
    String titolo;
    double prezzo;
    int flagVenduto;

    public ArtWork(int newIdOpera, String newTitolo, double newPrezzo, int newFlagVenduto){
        this.idOpera=newIdOpera;
        this.titolo=newTitolo;
        this.prezzo=newPrezzo;
        this.flagVenduto=newFlagVenduto;
    }

    public double getPrezzo() {return prezzo;}
    public int getFlagVenduto() {return flagVenduto;}
    public int getIdOpera() {return idOpera;}
    public String getTitolo() {return titolo;}
}
