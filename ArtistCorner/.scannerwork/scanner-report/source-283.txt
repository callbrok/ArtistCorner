package com.artistcorner.engclasses.bean;

public class ProposalBean {
    private int idOfferta;
    private int artista;
    private  int galleria;
    private int flagAccettazione;

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
