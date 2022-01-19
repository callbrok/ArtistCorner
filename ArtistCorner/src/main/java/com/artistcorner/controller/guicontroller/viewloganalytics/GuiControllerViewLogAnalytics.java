package com.artistcorner.controller.guicontroller.viewloganalytics;

import com.artistcorner.controller.applicationcontroller.ViewLogAnalytics;
import com.artistcorner.engclasses.others.Commit;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GuiControllerViewLogAnalytics {
    @FXML
    public AnchorPane anchorParent;
    @FXML
    public Label labelLogOut;
    @FXML
    public ScatterChart scatterChartCommit;
    @FXML
    public SVGPath svgProfile;
    @FXML
    public CategoryAxis xAxisChart;
    @FXML
    public NumberAxis yAxisChart;

    private double xUpload=0;
    private double yUpload=0;
    private Stage stage;


    public void initialize() throws GitAPIException, IOException {
        makeDraggable();
        makeLogOut();
        initChart();

        yAxisChart.setAutoRanging(false);
        yAxisChart.setLowerBound(9);
        yAxisChart.setUpperBound(24);
        yAxisChart.setTickUnit(2);
        yAxisChart.setLabel("Fascia Oraria");

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }


    
    public Dialog showCommitDialog(List<Commit> listOfCommit, String date){
        Dialog<String> dialog = new Dialog<String>();
        ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);

        String dateToCompare = date;

        if(date.length() == 14){dateToCompare = date + "0";}  // Se viene passata una stringa con tempo troncato.

        for (Commit cmt : listOfCommit) {

            if(cmt.getData().equals(dateToCompare)){

                dialog.setTitle("Commit del " + cmt.getData());
                dialog.setHeaderText("Committato da " + cmt.getNome());

                dialog.setContentText(cmt.getMessaggio());

                dialog.getDialogPane().getButtonTypes().add(type);
            }
        }

        dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);  // Rimuove il "..." se messaggio troppo lungo.
        return dialog;
    }


    public void initChart() throws GitAPIException, IOException {
        ViewLogAnalytics vla = new ViewLogAnalytics();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        XYChart.Series<String, Double> series = new XYChart.Series();
        XYChart.Series<String, Double> series1 = new XYChart.Series();

        series.setName("Marco");
        series1.setName("Alessio");

        List<Commit> listOfCommit = vla.initializeCommitArray();


        // Scorre la lista dei commit ed inizializza il grafico.
        for (Commit cmt : listOfCommit) {

            String orario = cmt.getData().substring(10, 15).replace(':','.');
            double orarioDouble = Double.parseDouble(orario);

            String data = cmt.getData().substring(0,8);

            // A seconda del proprietario del commit inserisce in una serie di numeri diversa.
            if(cmt.getNome().equals("Marco Purificato")){
                series.getData().add(new XYChart.Data<String, Double>(data, orarioDouble));
            }
            if(cmt.getNome().equals("Alessio1100")){
                series1.getData().add(new XYChart.Data<String, Double>(data, orarioDouble));
            }
        }

        // Setta il grafico con i dati passati.
        scatterChartCommit.getData().addAll(series, series1);


        // Event handler per i miei commit.
        for(XYChart.Data<String, Double> data: series.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String date = data.getXValue() + ", " + String.valueOf(data.getYValue()).replace(".",":");
                    showCommitDialog(listOfCommit, date).showAndWait();
                }
            });
        }

        // Event handler per i commit di Alessio.
        for(XYChart.Data<String, Double> data: series1.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String date = data.getXValue() + ", " + String.valueOf(data.getYValue()).replace(".",":");
                    showCommitDialog(listOfCommit, date).showAndWait();
                }
            });
        }

    }


    private void makeDraggable(){
        anchorParent.setOnMousePressed((eventUpload -> {
            xUpload=eventUpload.getSceneX();
            yUpload= eventUpload.getSceneY();
        }));

        anchorParent.setOnMouseDragged((eventUpload -> {
            stage = (Stage) ((Node)eventUpload.getSource()).getScene().getWindow();
            stage.setX(eventUpload.getScreenX() - xUpload);
            stage.setY(eventUpload.getScreenY() - yUpload);
        }));
    }

    public void exitWindowUpload() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
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
}
