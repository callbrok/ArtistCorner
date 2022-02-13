package com.artistcorner.engclasses.exceptions;

public class NotValidEmailException extends Exception{
    private static final long serialVersionUID = 1L;

    public NotValidEmailException (String message) {
        super(message);
    }
}
