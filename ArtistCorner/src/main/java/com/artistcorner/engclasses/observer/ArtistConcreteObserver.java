package com.artistcorner.engclasses.observer;

public class ArtistConcreteObserver implements Observer {

    String email;
    String nameBuy;
    String title;

    public ArtistConcreteObserver(String email,String nameBuy,String title){
    super();
    this.email = email;
    this.nameBuy = nameBuy;
    this.title = title;

    }
    @Override
    public void update() {
        SendMail sd = new SendMail();
        sd.send(email,nameBuy,title);
    }

}
