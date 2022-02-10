package com.artistcorner.engclasses.exceptions;

import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class ExceptionView {
    private Pane exceptionPane;

    protected ExceptionView(ExceptionsTypeManager etm){
        try {
            exceptionPane =  FXMLLoader.load(ExceptionsTypeManager.getUrl(etm));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane getExceptionPane(){ return exceptionPane;}


}
