package com.artistcorner.engclasses.exceptions;

public class ArtWorkNotFoundException extends  Exception{

    private static final long serialVersionUID = 1L;

    public ArtWorkNotFoundException (String message) {
        super(message);
    }
}
