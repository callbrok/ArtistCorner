package com.artistcorner.engclasses.bean;

public class ArtWorkBean {
    private int idOpera;
    private String titolo;
    private double prezzo;
    private int flagVendibile;

    public ArtWorkBean(int newIdOpera, String newTitolo, double newPrezzo, int newFlagVendibile){
        this.idOpera=newIdOpera;
        this.titolo=newTitolo;
        this.prezzo=newPrezzo;
        this.flagVendibile=newFlagVendibile;
    }

    public ArtWorkBean(String newTitolo, double newPrezzo, int newFlagVendibile){
        this.idOpera=0;
        this.titolo=newTitolo;
        this.prezzo=newPrezzo;
        this.flagVendibile=newFlagVendibile;
    }

    public void setIdOpera(int idOpera) {this.idOpera = idOpera;}
    public void setTitolo(String titolo) {this.titolo = titolo;}
    public void setPrezzo(double prezzo) {this.prezzo = prezzo;}
    public void setFlagVendibile(int flagVenduto) {this.flagVendibile = flagVenduto;}

    public double getPrezzo() {return prezzo;}
    public int getFlagVendibile() {return flagVendibile;}
    public int getIdOpera() {return idOpera;}
    public String getTitolo() {return titolo;}
}


