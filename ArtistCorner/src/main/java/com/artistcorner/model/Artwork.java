package com.artistcorner.model;

import java.sql.Blob;

public class Artwork {
    private int idOpera;
    private String titolo;
    private double prezzo;
    private int flagVenduto;
    private  int artistaId;
    private String categoria;
    private Blob immagine;

    public Artwork(int newIdOpera, String newTitolo, double newPrezzo, int newFlagVenduto, int newArtistaId, String newCategoria, Blob newImmagine){
        this.idOpera=newIdOpera;
        this.titolo=newTitolo;
        this.prezzo=newPrezzo;
        this.flagVenduto=newFlagVenduto;
        this.artistaId = newArtistaId;
        this.categoria = newCategoria;
        this.immagine = newImmagine;
    }

    public Artwork(String newTitolo, double newPrezzo, int newFlagVenduto, int newArtistaId, String newCategoria){
        this.idOpera=0;
        this.titolo=newTitolo;
        this.prezzo=newPrezzo;
        this.flagVenduto=newFlagVenduto;
        this.artistaId = newArtistaId;
        this.categoria = newCategoria;
        this.immagine = null;
    }


    public double getPrezzo() {return prezzo;}
    public int getFlagVenduto() {return flagVenduto;}
    public int getIdOpera() {return idOpera;}
    public String getTitolo() {return titolo;}
    public int getArtistaId() {return artistaId;}
    public String getCategoria() {return categoria;}
    public Blob getImmagine() {return immagine;}
}
