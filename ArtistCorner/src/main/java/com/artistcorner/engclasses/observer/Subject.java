package com.artistcorner.engclasses.observer;

public interface Subject {
    void add(ArtistConcreteObserver artistConcreteObserver);
    void notifyArtist();
}
