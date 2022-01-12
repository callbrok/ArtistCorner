package com.artistcorner.engclasses.exceptions;

import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ExceptionView {
    private Pane exceptionPane;

    public ExceptionView(ExceptionsTypeMenager etm){
        try {
            exceptionPane =  FXMLLoader.load(ExceptionsTypeMenager.getUrl(etm));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane getExceptionPane(){ return exceptionPane;}


}
