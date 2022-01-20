package com.artistcorner.engclasses.bean;

public class BuyerBean {
    int idBuyer;
    String nome;
    String cognome;


    public BuyerBean(int newIdBuyer, String newNome, String newCognome) {
        this.idBuyer = newIdBuyer;
        this.nome = newNome;
        this.cognome = newCognome;
    }

    public int getIdBuyer() {return idBuyer;}
    public String getNome() {return nome;}
    public String getCognome(){return cognome;}
}

