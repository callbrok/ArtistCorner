package com.artistcorner.model;

public class ArtGallery {
    int galleria;
    String nome;
    String descrizione;
    String indirizzo;
    String username;

    public  ArtGallery(int newGalleria, String newNome, String newDescrizione, String newIndirizzo, String newUsername){
        this.galleria = newGalleria;
        this.nome = newNome;
        this.descrizione = newDescrizione;
        this.indirizzo = newIndirizzo;
        this.username = newUsername;
    }

    public int getGalleria() {return galleria;}
    public String getNome() {return nome;}
    public String getDescrizione() {return descrizione;}
    public String getIndirizzo() {return indirizzo;}
    public String getUsername() {return username;}

}
