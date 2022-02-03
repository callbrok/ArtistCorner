package com.artistcorner.engclasses.bean;

public class BuyerBean {
    private int idBuyer;
    private String nome;
    private String cognome;



    public void setCognome(String cognome) {this.cognome = cognome;}
    public void setIdBuyer(int idBuyer) {this.idBuyer = idBuyer;}
    public void setNome(String nome) {this.nome = nome;}

    public int getIdBuyer() {return idBuyer;}
    public String getNome() {return nome;}
    public String getCognome(){return cognome;}
}

