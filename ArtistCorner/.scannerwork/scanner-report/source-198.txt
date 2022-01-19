package com.artistcorner.model;

public class ArtWork {
    int idOpera;
    String titolo;
    double prezzo;
    int flagVenduto;
    int artistaId;

    public ArtWork(int newIdOpera, String newTitolo, double newPrezzo, int newFlagVenduto,int newArtistaId){
        this.idOpera=newIdOpera;
        this.titolo=newTitolo;
        this.prezzo=newPrezzo;
        this.flagVenduto=newFlagVenduto;
        this.artistaId = newArtistaId;
    }

    public double getPrezzo() {return prezzo;}
    public int getFlagVenduto() {return flagVenduto;}
    public int getIdOpera() {return idOpera;}
    public String getTitolo() {return titolo;}
    public int getArtistaId() {return artistaId;}
}
