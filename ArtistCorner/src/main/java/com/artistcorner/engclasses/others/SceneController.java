package com.artistcorner.engclasses.others;

import com.artistcorner.controller.guicontroller.getreccomandation.GuiControllerGetReccomandation;
import com.artistcorner.controller.guicontroller.login.summarypanel.GuiControllerArtistSummary;
import com.artistcorner.controller.guicontroller.uploadartwork.GuiControllerUploadArtwork;
import com.artistcorner.controller.guicontroller.viewartgalleryproposals.GuiControllerViewArtGalleryProposals;
import com.artistcorner.controller.guicontroller.viewprofile.GuiControllerViewProfile;
import com.artistcorner.controller.guicontroller.viewsaleshistory.GuiControllerViewSalesHistory;
import com.artistcorner.engclasses.bean.User;
import com.artistcorner.model.Artist;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneMainArtista(ActionEvent event, Artist art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ArtistSummaryView.fxml"));
        root = loader.load();

        GuiControllerArtistSummary gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloArtista(ActionEvent event, Artist art) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewProfileView.fxml"));
        root = loader.load();

        GuiControllerViewProfile gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloAlgoritmo(MouseEvent event, Artist art) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GetReccomandationView.fxml"));
        root = loader.load();

        GuiControllerGetReccomandation gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloVenduto(ActionEvent event, Artist art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewSalesHistoryView.fxml"));
        root = loader.load();

        GuiControllerViewSalesHistory gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloOfferteMostre(ActionEvent event, Artist art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewArtGalleryProposalsView.fxml"));
        root = loader.load();

        GuiControllerViewArtGalleryProposals gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneUploadOpera(ActionEvent event, Artist art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UploadArtWork.fxml"));
        root = loader.load();

        GuiControllerUploadArtwork gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogin(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/login/LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/artist/main.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

}
