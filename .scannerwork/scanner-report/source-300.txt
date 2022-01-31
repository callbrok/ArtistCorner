package com.artistcorner.engclasses.bean;

public class ArtWorkBean {

    private int idOpera;
    private String titolo;
    private double prezzo;
    private int flagVendibile;
    private int artistId;
    private String categoria;


    public ArtWorkBean(int newIdOpera, String newTitolo, double newPrezzo, int newFlagVendibile,int newArtistId, String newCategoria){
        this.idOpera=newIdOpera;
        this.titolo=newTitolo;
        this.prezzo=newPrezzo;
        this.flagVendibile=newFlagVendibile;
        this.artistId = newArtistId;
        this.categoria = newCategoria;
    }

    public ArtWorkBean(String newTitolo, double newPrezzo, int newFlagVendibile,int newArtistId,String newCategoria){
        this.idOpera=0;
        this.titolo=newTitolo;
        this.prezzo=newPrezzo;
        this.flagVendibile=newFlagVendibile;
        this.artistId = newArtistId;
        this.categoria = newCategoria;
    }

    public void setIdOpera(int idOpera) {this.idOpera = idOpera;}
    public void setTitolo(String titolo) {this.titolo = titolo;}
    public void setPrezzo(double prezzo) {this.prezzo = prezzo;}
    public void setFlagVendibile(int flagVenduto) {this.flagVendibile = flagVenduto;}
    public void setArtistId(int artistId) {this.artistId = artistId;}
    public void setCategoria(String categoria) {this.categoria = categoria;}

    public double getPrezzo() {return prezzo;}
    public int getFlagVendibile() {return flagVendibile;}
    public int getIdOpera() {return idOpera;}
    public String getTitolo() {return titolo;}
    public int getArtistId() {return artistId;}
    public String getCategoria() {return categoria;}
}


