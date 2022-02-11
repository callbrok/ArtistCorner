package com.artistcorner.engclasses.bean;

public class ArtistBean {
    private int idArtista;
    private String nome;
    private String cognome;


    public void setIdArtista(int idArtista) {this.idArtista = idArtista;}
    public void setCognome(String cognome) {this.cognome = cognome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getNome() {return nome;}
    public int getIdArtista() {return idArtista;}
    public String getCognome() {return cognome;}
}
