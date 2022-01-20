package com.artistcorner.engclasses.exceptions;

public class ProposalNotFoundException extends  Exception{

    private static final long serialVersionUID = 1L;

    public ProposalNotFoundException (String message) {
        super(message);
    }

}
