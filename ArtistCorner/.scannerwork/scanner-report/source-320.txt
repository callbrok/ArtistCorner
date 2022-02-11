package com.artistcorner.engclasses.exceptions;

public class EmptyFieldException extends Exception{

    private static final long serialVersionUID = 1L;

    public EmptyFieldException (String message) {
        super(message);
    }
}
