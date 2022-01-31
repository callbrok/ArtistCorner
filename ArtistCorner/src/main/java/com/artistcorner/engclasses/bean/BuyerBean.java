package com.artistcorner.engclasses.bean;

public class BuyerBean {
    private int idBuyer;
    private String nome;
    private String cognome;


    public BuyerBean(int newIdBuyer, String newNome, String newCognome) {
        this.idBuyer = newIdBuyer;
        this.nome = newNome;
        this.cognome = newCognome;
    }
    public BuyerBean(String newNome,String newCognome){
        this.nome = newNome;
        this.cognome = newCognome;
    }

    public int getIdBuyer() {return idBuyer;}
    public String getNome() {return nome;}
    public String getCognome(){return cognome;}
}

