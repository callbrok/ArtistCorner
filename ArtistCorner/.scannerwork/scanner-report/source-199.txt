package com.artistcorner.engclasses.bean;

public class ProposalBean {
    int idOfferta;
    int artista;
    int galleria;
    int flagAccettazione;

    public  ProposalBean(int newId, int newArtista, int newGalleria, int newFlagAccettazione){
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
