package com.artistcorner.engclasses.exceptions;

public class ArtistNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public ArtistNotFoundException (String message) {
        super(message);
    }
}
