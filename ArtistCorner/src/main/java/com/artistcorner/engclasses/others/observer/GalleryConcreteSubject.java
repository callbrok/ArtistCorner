package com.artistcorner.engclasses.others.observer;


public class GalleryConcreteSubject extends Subject {

    String nameBuy;
    String indirizzo;
    String nomegal;

    public GalleryConcreteSubject(){
    }

    public GalleryConcreteSubject (String nameBuy, String indirizzo, String nomeGal, boolean state) {

        super();
        this.setNameBuy(nameBuy);
        this.setIndirizzoGal(indirizzo);
        this.setNomeGal(nomeGal);

    }

    public void setNameBuy(String nameBuy) {
        this.nameBuy = nameBuy;
    }

    public String getNameBuy() {
        return nameBuy;
    }

    public void setIndirizzoGal(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getIndirizzoGal() {
        return indirizzo;
    }

    public void setNomeGal(String nomegal) {
        this.nomegal = nomegal;
    }

    public String getNomeGal() {
        return nomegal;
    }

    public void notifyChanges(){

        super.notifyObservers();

    }
}
