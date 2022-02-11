package com.artistcorner.engclasses.others.observer;

import java.util.ArrayList;

public abstract class Subject {

        private ArrayList<Observer> observerArrayList ;

        protected Subject() {
            observerArrayList = new ArrayList<>() ;
        }

        public void attach(Observer newObserver) {
            observerArrayList.add(newObserver) ;
        }

        public void detach(Observer removeObserver) {
            observerArrayList.remove(removeObserver) ;
        }

        protected void notifyObservers() {
            for (Observer observer : observerArrayList) {
                observer.update();
            }
        }
}
