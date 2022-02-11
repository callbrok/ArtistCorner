package com.artistcorner.engclasses.exceptions;

public class DuplicateArtworkException extends  Exception{

    private static final long serialVersionUID = 1L;

    public DuplicateArtworkException(String message) {
        super(message);
    }
}
