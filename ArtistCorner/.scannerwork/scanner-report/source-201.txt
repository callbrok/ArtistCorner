package com.artistcorner.engclasses.bean;

public class ArtistBean {
    int idArtista;
    String nome;
    String cognome;

    public ArtistBean(int newId, String newNome, String newCognome){
        this.nome = newNome;
        this.cognome = newCognome;
        this.idArtista = newId;
    }

    public String getNome() {return nome;}
    public int getIdArtista() {return idArtista;}
    public String getCognome() {return cognome;}
}
