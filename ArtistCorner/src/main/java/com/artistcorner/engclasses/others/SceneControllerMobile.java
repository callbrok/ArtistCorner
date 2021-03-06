package com.artistcorner.engclasses.others;

import com.artistcorner.controller.guicontroller.mobile.findartwork.GuiControllerMobileFindArtwork;
import com.artistcorner.controller.guicontroller.mobile.forwardproposal.GuiControllerMobileForwardProposal;
import com.artistcorner.controller.guicontroller.mobile.getrecommendation.GuiControllerMobileGetRecommendation;
import com.artistcorner.controller.guicontroller.mobile.login.summaries.GuiControllerMobileArtistSummary;
import com.artistcorner.controller.guicontroller.mobile.login.summaries.GuiControllerMobileBuyerSummary;
import com.artistcorner.controller.guicontroller.mobile.login.summaries.GuiControllerMobileGallerySummary;
import com.artistcorner.controller.guicontroller.mobile.managefollowedartist.GuiControllerMobileManageFollowedArtist;
import com.artistcorner.controller.guicontroller.mobile.uploadartwork.GuiControllerMobileUploadArtwork;
import com.artistcorner.controller.guicontroller.mobile.manageproposals.GuiControllerMobileManageProposals;
import com.artistcorner.controller.guicontroller.mobile.manageartworks.GuiControllerMobileManageArtworks;
import com.artistcorner.controller.guicontroller.mobile.viewfavourites.GuiControllerMobileViewFavourites;
import com.artistcorner.controller.guicontroller.mobile.viewpendingproposals.GuiControllerMobileViewPendingProposals;
import com.artistcorner.controller.guicontroller.mobile.viewsoldartworks.GuiControllerMobileViewSoldArtworks;
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

public class SceneControllerMobile {

    public static  final String CSS_PATH = "/css/" + "mobile/" + "main.css";

    private SceneControllerMobile() { throw new IllegalStateException("Utility class");}

    public static void switchToSceneMainArtista(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/login/ArtistSummaryMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileArtistSummary gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloArtista(ActionEvent event, ArtistBean art) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/ManageArtworksMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileManageArtworks gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloAlgoritmo(MouseEvent event, ArtistBean art) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/GetRecommendationMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileGetRecommendation gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloVenduto(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/ViewSoldArtworksMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileViewSoldArtworks gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneProfiloOfferteMostre(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/ManageProposalsMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileManageProposals gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneUploadOpera(ActionEvent event, ArtistBean art) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/UploadArtworkMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileUploadArtwork gcas = loader.getController();
        gcas.getArtist(art);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToLogin(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(SceneControllerMobile.class.getResource("/view/mobile/login/LoginMobileView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneSearchArtWorkBuyer(ActionEvent event, BuyerBean buy) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/FindArtworkMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileFindArtwork gcas = loader.getController();
        gcas.getBuyer(buy);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneBuyerSummary(ActionEvent event, BuyerBean buy) throws IOException{
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/login/BuyerSummaryMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileBuyerSummary gcas = loader.getController();
        gcas.getBuyer(buy);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }

    public static void switchToSceneFavouritesBuyer(ActionEvent event, BuyerBean buy) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/ViewFavouritesMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileViewFavourites gcas = loader.getController();
        gcas.getBuyer(buy);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneSentArtGalleryProposal(ActionEvent event, ArtGalleryBean gal) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/ViewPendingProposalsMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileViewPendingProposals gcas = loader.getController();
        gcas.getGallery(gal);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneGallerySummary(ActionEvent event, ArtGalleryBean gal) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/login/GallerySummaryMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileGallerySummary gcas = loader.getController();
        gcas.getGallery(gal);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
    public static void switchToSceneSearchArtWorkGallery(ActionEvent event, ArtGalleryBean gal) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/ForwardProposalMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileForwardProposal gcas = loader.getController();
        gcas.getGallery(gal);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
    public static void switchToSceneProfiloGallery(ActionEvent event,ArtGalleryBean gal) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(SceneControllerMobile.class.getResource("/view/mobile/ManageFollowedArtistMobileView.fxml"));
        Parent root = loader.load();

        GuiControllerMobileManageFollowedArtist gcas = loader.getController();
        gcas.getGallery(gal);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneControllerMobile.class.getResource(CSS_PATH).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}
