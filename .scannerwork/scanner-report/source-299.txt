package com.artistcorner.engclasses.bean;

public class ArtGalleryBean {
    int galleria;
    String nome;
    String descrizione;
    String indirizzo;
    String username;

    public  ArtGalleryBean(int newGalleriaB, String newNomeB, String newDescrizioneB, String newIndirizzoB, String newUsernameB){
        this.galleria = newGalleriaB;
        this.nome = newNomeB;
        this.descrizione = newDescrizioneB;
        this.indirizzo = newIndirizzoB;
        this.username = newUsernameB;
    }

    public int getGalleria() {return galleria;}
    public String getNome() {return nome;}
    public String getDescrizione() {return descrizione;}
    public String getIndirizzo() {return indirizzo;}
    public String getUsername() {return username;}
}
