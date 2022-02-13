package com.artistcorner.controller.guicontroller.viewsoldartworks;

import com.artistcorner.controller.applicationcontroller.ViewSoldArtworks;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.SellArtworkNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeManager;
import com.artistcorner.engclasses.others.SceneController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerViewSoldArtworks {
    @FXML
    private HBox borderImageView;
    @FXML
    private ImageView imageOfArtworkSold;
    @FXML
    private Label labelArtBuyer;
    @FXML
    private AnchorPane anchorParentSHD;
    @FXML
    private Button button1SHD;
    @FXML
    private Button button2SHD;
    @FXML
    private Button button3SHD;
    @FXML
    private Button button4SHD;
    @FXML
    private Button button5SHD;
    @FXML
    private ListView listViewSale;
    @FXML
    private Label labelArtWorkTitle;
    @FXML
    private Label labelArtWorkPrice;
    @FXML
    private LineChart lineChartSell;
    @FXML
    private Label labelLogOut;
    @FXML
    private Pane paneExceptionLoad;
    @FXML
    private SVGPath svgProfile;
    @FXML
    private Label labelUsernameDisplay;

    private double x=0;
    private double y=0;
    private Stage stage;

    private ArtistBean art;

    private void makeDraggable(){
        anchorParentSHD.setOnMousePressed((event -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParentSHD.setOnMouseDragged((event -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
    public void exitWindow() throws IOException {
        stage = (Stage) anchorParentSHD.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentSHD.getScene().getWindow();
        stage.setIconified(true);
    }

    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                SceneController.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public void initialize() throws IOException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();

        borderImageView.setMinWidth(224);
        borderImageView.setMinHeight(152);
        borderImageView.setAlignment(Pos.CENTER);

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);

        initializeLineChart();
    }

    public void getArtist(ArtistBean loggedArtist) throws IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        populateListView(loggedArtist);
    }

    /**
     * Inizializza la listView contenente le opere vendute dall'artista loggato.
     */
    public void populateListView(ArtistBean art) throws IOException {
        ViewSoldArtworks vsh = new ViewSoldArtworks();
        List<ArtworkBean> arrayOfArtwork = null;

        try {
            arrayOfArtwork = vsh.retrieveSellArtWorks(art);

            for (ArtworkBean n : arrayOfArtwork) {
                listViewSale.getItems().add(n.getTitolo());  // Popola la listView.
                    }

            List<ArtworkBean> finalArrayOfArtwork = arrayOfArtwork;

            listViewSale.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    InputStream inputStream = null;
                    int index = listViewSale.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.

                    ArtworkBean currentArt = finalArrayOfArtwork.get(index);   // Prende l'i-esima (index) opera d'arte dal result set.
                    BuyerBean currentBuyer = vsh.retrieveBuyerName(currentArt);

                    try {
                        inputStream = currentArt.getImmagine().getBinaryStream();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Image image = new Image(inputStream);
                    imageOfArtworkSold.setImage(image);

                    labelArtWorkPrice.setText(String.valueOf(currentArt.getPrezzo()) + "\u20ac");
                    labelArtBuyer.setText(currentBuyer.getNome() + " " + currentBuyer.getCognome());
                }
            });

        } catch (SellArtworkNotFoundException e) {
            // Eccezione: Nessun opera venduta.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeManager.SELLARTNOTFOUND);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }

    public void initializeLineChart(){
        // DA FARE: Implement make line chart feature.
    }

    public void setTooltipMenu(){
        button1SHD.setTooltip(new Tooltip("Home"));
        button2SHD.setTooltip(new Tooltip("Profilo"));
        button3SHD.setTooltip(new Tooltip("Carica Opera"));
        button4SHD.setTooltip(new Tooltip("Offerte Mostre"));
        button5SHD.setTooltip(new Tooltip("Opere Vendute"));
    }


    public void switchToSceneMainArtistaSoldArtD(ActionEvent event) throws IOException, SQLException {
        SceneController.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtistaSoldArtD(ActionEvent event) throws SQLException, IOException {
        SceneController.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOperaSoldArtD(ActionEvent event) throws IOException {
        SceneController.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloOfferteMostreSoldArtD(ActionEvent event) throws IOException {
        SceneController.switchToSceneProfiloOfferteMostre(event, art);
    }

    public void switchToProfiloVendutoSoldArtD(ActionEvent event) throws IOException {
        SceneController.switchToSceneProfiloVenduto(event, art);
    }
}
