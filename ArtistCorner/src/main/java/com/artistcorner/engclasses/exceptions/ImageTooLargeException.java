package com.artistcorner.engclasses.exceptions;

public class ImageTooLargeException extends Exception{
    private static final long serialVersionUID = 1L;

    public ImageTooLargeException (String message) {
        super(message);
    }
}
