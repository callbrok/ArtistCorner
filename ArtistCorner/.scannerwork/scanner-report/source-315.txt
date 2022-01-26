package com.artistcorner.controller.guicontroller.viewsaleshistory;

import com.artistcorner.controller.applicationcontroller.ViewSalesHistory;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.SellArtWorkNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import com.artistcorner.model.ArtWork;
import com.artistcorner.model.Artist;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerViewSalesHistory {
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
        SceneController.deleteSerialNodo();

        stage = (Stage) anchorParentSHD.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentSHD.getScene().getWindow();
        stage.setIconified(true);
    }

    public void makeLogOut(){
        labelLogOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public void initialize() throws IOException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);

        initializeLineChart();
    }

    public void getArtist(ArtistBean loggedArtist) throws IOException {
        art = loggedArtist;
        labelUsernameDisplay.setText(art.getNome() + " " + art.getCognome());
        populateListView(loggedArtist);
    }

    public void populateListView(ArtistBean art) throws IOException {
        ViewSalesHistory vsh = new ViewSalesHistory();
        List<ArtWorkBean> arrayOfArtwork = null;

        try {
            arrayOfArtwork = vsh.retrieveSellArtWorks(art);

            for (ArtWorkBean n : arrayOfArtwork) {
                listViewSale.getItems().add(n.getTitolo());  // Popola la listView.
                    }

            List<ArtWorkBean> finalArrayOfArtwork = arrayOfArtwork;

            listViewSale.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    int index = listViewSale.getSelectionModel().getSelectedIndex();  // Prende l'indice della riga cliccata.

                    ArtWorkBean currentArt = finalArrayOfArtwork.get(index);   // Prende l'i-esima (index) opera d'arte dal result set.

                    labelArtWorkTitle.setText(currentArt.getTitolo());
                    labelArtWorkPrice.setText(String.valueOf(currentArt.getPrezzo()));
                }
            });

        } catch (SellArtWorkNotFoundException e) {
            // Eccezione: Nessun opera venduta.
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.SELLARTNOTFOUND);
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


    public void switchToSceneMainArtista(ActionEvent event) throws IOException, SQLException {
        SceneController scshd = new SceneController();
        scshd.switchToSceneMainArtista(event, art);
    }

    public void switchToProfiloArtista(ActionEvent event) throws SQLException, IOException {
        SceneController scshd = new SceneController();
        scshd.switchToSceneProfiloArtista(event, art);
    }

    public void switchToUploadOpera(ActionEvent event) throws IOException {
        SceneController scshd = new SceneController();
        scshd.switchToSceneUploadOpera(event, art);
    }

    public void switchToProfiloOfferteMostre(ActionEvent event) throws IOException {
        SceneController scshd = new SceneController();
        scshd.switchToSceneProfiloOfferteMostre(event, art);
    }
}
