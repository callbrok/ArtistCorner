package com.artistcorner.engclasses.bean;

public class ArtGalleryBean {
    private int galleria;
    private String nome;
    private String descrizione;
    private String indirizzo;
    private String username;

    public  ArtGalleryBean(int newGalleriaB, String newNomeB, String newDescrizioneB, String newIndirizzoB, String newUsernameB){
        this.galleria = newGalleriaB;
        this.nome = newNomeB;
        this.descrizione = newDescrizioneB;
        this.indirizzo = newIndirizzoB;
        this.username = newUsernameB;
    }
    public ArtGalleryBean(String newNome, String newDescrizione, String newIndirizzo, String newUsername){
        this.nome=newNome;
        this.descrizione = newDescrizione;
        this.indirizzo = newIndirizzo;
        this.username = newUsername;
    }

    public String getNome() {return nome;}
    public int getGalleria() {return galleria;}
    public String getIndirizzo() {return indirizzo;}
    public String getDescrizione() {return descrizione;}
    public String getUsername() {return username;}
}
