package com.artistcorner.engclasses.others.analytics;

public class Commit {
    String nome;
    String data;
    String messaggio;

    public Commit(String newNome, String newData, String newMessage){
        this.nome = newNome;
        this.data = newData;
        this.messaggio = newMessage;
    }

    public String getNome() {return nome;}
    public String getData() {return data;}
    public String getMessaggio() {return messaggio;}
}
