package com.artistcorner.engclasses.bean;

public class ProposalBean {
    int idOfferta;
    int artista;
    int galleria;
    int flagAccettazione;

    public  ProposalBean(int newIdB, int newArtistaB, int newGalleriaB, int newFlagAccettazioneB){
        this.idOfferta = newIdB;
        this.artista = newArtistaB;
        this.galleria =  newGalleriaB;
        this.flagAccettazione = newFlagAccettazioneB;
    }


    public int getIdOfferta() {return idOfferta;}
    public int getArtista() {return artista;}
    public int getFlagAccettazione() {return flagAccettazione;}
    public int getGalleria() {return galleria;}
}
