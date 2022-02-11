package com.artistcorner.engclasses.exceptions;

public class EmptyPathException extends Exception{
    private static final long serialVersionUID = 1L;

    public EmptyPathException (String message) {
        super(message);
    }
}
