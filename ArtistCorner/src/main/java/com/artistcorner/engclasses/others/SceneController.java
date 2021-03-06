package com.artistcorner.engclasses.others;


import com.artistcorner.controller.guicontroller.findartwork.GuiControllerFindArtwork;
import com.artistcorner.controller.guicontroller.forwardproposal.GuiControllerForwardProposal;
import com.artistcorner.controller.guicontroller.getrecommendation.GuiControllerGetRecommendation;
import com.artistcorner.controller.guicontroller.login.summaries.GuiControllerArtistSummary;
import com.artistcorner.controller.guicontroller.login.summaries.GuiControllerBuyerSummary;
import com.artistcorner.controller.guicontroller.login.summaries.GuiControllerGallerySummary;
import com.artistcorner.controller.guicontroller.managefollowedartist.GuiControllerManageFollowedArtist;
import com.artistcorner.controller.guicontroller.manageproposals.GuiControllerManageProposals;
import com.artistcorner.controller.guicontroller.uploadartwork.GuiControllerUploadArtwork;
import com.artistcorner.controller.guicontroller.manageartworks.GuiControllerManageArtworks;
import com.artistcorner.controller.guicontroller.viewfavourites.GuiControllerViewFavourites;
import com.artistcorner.controller.guicontroller.viewpendingproposals.GuiControllerViewPendingProposals;
import com.artistcorner.controller.guicontroller.viewsoldartworks.GuiControllerViewSoldArtworks;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
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

    public static final String CSS_PATH = "/css/" + "desktop/" + "main.css";

    private SceneController(){ throw new IllegalStateException("Utility class");}

    public static void switchToSceneMainArtista(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/login/ArtistSummaryView.fxml"));
        Parent root = loader.load();

        GuiControllerArtistSummary gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloArtista(ActionEvent event, ArtistBean art) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/ManageArtworksView.fxml"));
        Parent root = loader.load();

        GuiControllerManageArtworks gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloAlgoritmo(MouseEvent event, ArtistBean art) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/GetRecommendationView.fxml"));
        Parent root = loader.load();

        GuiControllerGetRecommendation gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloVenduto(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/ViewSoldArtworksView.fxml"));
        Parent root = loader.load();

        GuiControllerViewSoldArtworks gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloOfferteMostre(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/ManageProposalsView.fxml"));
        Parent root = loader.load();

        GuiControllerManageProposals gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneUploadOpera(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/UploadArtworkView.fxml"));
        Parent root = loader.load();

        GuiControllerUploadArtwork gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToLogin(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(SceneController.class.getResource("/view/login/LoginView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToAnalytics(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(SceneController.class.getResource("/view/ViewLogAnalytics.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToAnalyticsSA(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(SceneController.class.getResource("/view/ViewSALogAnalytics.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneBuyerSummary(ActionEvent event, BuyerBean buy) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/login/BuyerSummaryView.fxml"));
        Parent root = loader.load();

        GuiControllerBuyerSummary gcas = loader.getController();
        gcas.getBuyer(buy);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneSearchArtWorkBuyer(ActionEvent event, BuyerBean buy) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/FindArtworkView.fxml"));
        Parent root = loader.load();

        GuiControllerFindArtwork gcas = loader.getController();
        gcas.getBuyer(buy);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneFavouritesBuyer(ActionEvent event, BuyerBean buy) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/ViewFavouritesView.fxml"));
        Parent root = loader.load();

        GuiControllerViewFavourites gcas = loader.getController();
        gcas.getBuyer(buy);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    public static void switchToSceneSearchArtWorkGallery(ActionEvent event, ArtGalleryBean gal) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/ForwardProposalView.fxml"));
        Parent root = loader.load();

        GuiControllerForwardProposal gcas = loader.getController();
        gcas.getGallery(gal);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public static void switchToSceneGallerySummary(ActionEvent event, ArtGalleryBean gal) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/login/ArtGallerySummaryView.fxml"));
        Parent root = loader.load();

        GuiControllerGallerySummary gcas = loader.getController();
        gcas.getGallery(gal);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloGallery(ActionEvent event, ArtGalleryBean gal) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/ManageFollowedArtistView.fxml"));
        Parent root = loader.load();

        GuiControllerManageFollowedArtist gcas = loader.getController();
        gcas.getGallery(gal);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneSentArtGalleryProposal(ActionEvent event,ArtGalleryBean gal) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/ViewPendingProposalsView.fxml"));
        Parent root = loader.load();
        GuiControllerViewPendingProposals gcas = loader.getController();
        gcas.getGallery(gal);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
