package com.artistcorner.engclasses.observer;

public class BuyerConcreteSubject implements Subject {

    ArtistConcreteObserver art;

    @Override
    public void add(ArtistConcreteObserver art) {
        this.art = art;
    }

    public void notifyArtist(){
        art.update();
    }
    public void checkOut(){
        notifyArtist();
    }
}
