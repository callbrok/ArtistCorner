package com.artistcorner.engclasses.bean;

public class UploadingArtWork {
    String filePath;
    int idArtist;
    String titolo;
    double prezzo;
    int flagVendibile;

    public UploadingArtWork(){
        this.filePath = "NULL";
        this.idArtist = 0;
        this.titolo = "NULL";
        this.prezzo = 0.0;
        this.flagVendibile = 0;
    }


    public int getIdArtist() {return idArtist;}
    public void setIdArtist(int idArtist) {this.idArtist = idArtist;}

    public String getFilePath() {return filePath;}
    public void setFilePath(String filePath) {this.filePath = filePath;}

    public double getPrezzo() {return prezzo;}
    public void setPrezzo(double prezzo) {this.prezzo = prezzo;}

    public String getTitolo() {return titolo;}
    public void setTitolo(String titolo) {this.titolo = titolo;}

    public int getFlagVendibile() {return flagVendibile;}
    public void setFlagVendibile(int flagVendibile) {this.flagVendibile = flagVendibile;}

}
