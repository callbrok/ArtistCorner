package com.artistcorner.engclasses.exceptions;

public class BuyerNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public BuyerNotFoundException (String message) {
        super(message);
    }
}
