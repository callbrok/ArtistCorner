package com.artistcorner.engclasses.exceptions;

public class SentProposalNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public SentProposalNotFoundException (String message) {
        super(message);
    }
}
