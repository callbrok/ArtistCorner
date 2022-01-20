package com.artistcorner.engclasses.exceptions;

public class SellArtWorkNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public SellArtWorkNotFoundException (String message) {
        super(message);
    }
}
