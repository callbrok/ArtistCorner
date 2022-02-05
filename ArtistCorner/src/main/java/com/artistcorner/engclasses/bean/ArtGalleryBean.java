package com.artistcorner.engclasses.bean;

public class ArtGalleryBean {
    private int galleria;
    private String nome;
    private String descrizione;
    private String indirizzo;



    public void setNome(String nome) {this.nome = nome;}
    public void setGalleria(int galleria) {this.galleria = galleria;}
    public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
    public void setIndirizzo(String indirizzo) {this.indirizzo = indirizzo;}

    public String getNome() {return nome;}
    public int getGalleria() {return galleria;}
    public String getIndirizzo() {return indirizzo;}
    public String getDescrizione() {return descrizione;}
}
