package com.artistcorner.engclasses.others;

import com.artistcorner.controller.guicontroller.mobile.getreccomandation.GuiControllerMobileGetReccomandation;
import com.artistcorner.controller.guicontroller.mobile.login.summarypanel.GuiControllerMobileArtistSummary;
import com.artistcorner.controller.guicontroller.mobile.uploadartwork.GuiControllerMobileUploadArtwork;
import com.artistcorner.controller.guicontroller.mobile.viewartgalleryproposals.GuiControllerMobileViewArtGalleryProposals;
import com.artistcorner.controller.guicontroller.mobile.viewprofile.GuiControllerMobileViewProfile;
import com.artistcorner.controller.guicontroller.mobile.viewsaleshistory.GuiControllerMobileViewSalesHistory;
import com.artistcorner.engclasses.bean.ArtistBean;
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

public class SceneControllerMobile {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static  final String CSS_PATH = "/css/mobile/main.css";


    public void switchToSceneMainArtista(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/ArtistSummaryMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileArtistSummary gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloArtista(ActionEvent event, ArtistBean art) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/ViewProfileMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewProfile gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloAlgoritmo(MouseEvent event, ArtistBean art) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/GetReccomandationMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileGetReccomandation gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloVenduto(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/ViewSalesHistoryMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewSalesHistory gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneProfiloOfferteMostre(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/ViewArtGalleryProposalsMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileViewArtGalleryProposals gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneUploadOpera(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mobile/UploadArtWorkMobileView.fxml"));
        root = loader.load();

        GuiControllerMobileUploadArtwork gcas = loader.getController();
        gcas.getArtist(art);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogin(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/mobile/login/LoginMobileView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }



}
