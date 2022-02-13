package com.artistcorner.engclasses.others.observer;

public class ArtistConcreteObserver implements Observer {


    GalleryConcreteSubject gal;
    private String email;

    public ArtistConcreteObserver(GalleryConcreteSubject galleryConcreteSubject, String email){

        this.gal = galleryConcreteSubject;
        this.gal.attach(this);
        this.email = email;
    }

    @Override
    public void update()  {
        SendMail.send(email,gal.getNameBuy(),gal.getIndirizzoGal(),gal.getNomeGal());
    }

}
