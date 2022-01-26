package com.artistcorner.engclasses.exceptions;

public class DuplicateArtWorkException extends  Exception{

    private static final long serialVersionUID = 1L;

    public DuplicateArtWorkException (String message) {
        super(message);
    }
}
