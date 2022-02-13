package com.artistcorner.engclasses.exceptions;

public class SellArtworkNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public SellArtworkNotFoundException(String message) {
        super(message);
    }
}
