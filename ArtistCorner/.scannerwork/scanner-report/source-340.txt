package com.artistcorner.engclasses.bean;

public class ProposalBean {
    private int idOfferta;
    private int artista;
    private  int galleria;
    private int flagAccettazione;

    public void setArtista(int artista) {this.artista = artista;}
    public void setFlagAccettazione(int flagAccettazione) {this.flagAccettazione = flagAccettazione;}
    public void setGalleria(int galleria) {this.galleria = galleria;}
    public void setIdOfferta(int idOfferta) {this.idOfferta = idOfferta;}

    public int getIdOfferta() {return idOfferta;}
    public int getArtista() {return artista;}
    public int getFlagAccettazione() {return flagAccettazione;}
    public int getGalleria() {return galleria;}
}
