package com.artistcorner.model;

public class ArtGallery {
    private int galleria;
    private String nome;
    private String descrizione;
    private String indirizzo;

    public  ArtGallery(int newGalleria, String newNome, String newDescrizione, String newIndirizzo){
        this.galleria = newGalleria;
        this.nome = newNome;
        this.descrizione = newDescrizione;
        this.indirizzo = newIndirizzo;
    }

    public int getGalleria() {return galleria;}
    public String getNome() {return nome;}
    public String getDescrizione() {return descrizione;}
    public String getIndirizzo() {return indirizzo;}

}
