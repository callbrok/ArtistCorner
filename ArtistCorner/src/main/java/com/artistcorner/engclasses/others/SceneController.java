package com.artistcorner.engclasses.others;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneMainArtista(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ArtistSummaryView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloArtista(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/ViewProfileView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloAlgoritmo(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/GetReccomandationView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloVenduto(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ViewSalesHistoryView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloOfferteMostre(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ViewArtGalleryProposalsView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneUploadOpera(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/UploadArtWork.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
