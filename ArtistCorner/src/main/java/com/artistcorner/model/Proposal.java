package com.artistcorner.model;

public class Proposal {
    private int idOfferta;
    private  int artista;
    private int galleria;
    private  int flagAccettazione;

    public  Proposal(int newId, int newArtista, int newGalleria, int newFlagAccettazione){
        this.idOfferta = newId;
        this.artista = newArtista;
        this.galleria =  newGalleria;
        this.flagAccettazione = newFlagAccettazione;
    }


    public int getIdOfferta() {return idOfferta;}
    public int getArtista() {return artista;}
    public int getFlagAccettazione() {return flagAccettazione;}
    public int getGalleria() {return galleria;}

}
