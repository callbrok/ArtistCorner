package com.example.testinterfaccia;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/interface/main_artista.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/main_artista.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloArtista(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/interface/profilo_artista.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/main_artista.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloAlgoritmo(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/interface/profilo_algoritmo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/main_artista.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloVenduto(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/interface/profilo_venduto.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/main_artista.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloOfferteMostre(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/interface/profilo_offerte_mostre.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/main_artista.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
