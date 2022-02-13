package com.artistcorner.engclasses.exceptions;

public class ArtGalleryNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public ArtGalleryNotFoundException (String message) {
        super(message);
    }
}
