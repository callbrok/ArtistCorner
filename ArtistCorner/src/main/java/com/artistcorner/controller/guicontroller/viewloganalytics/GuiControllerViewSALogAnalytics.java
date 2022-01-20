package com.artistcorner.controller.guicontroller.viewloganalytics;

import com.artistcorner.controller.applicationcontroller.ViewLogAnalytics;
import com.artistcorner.engclasses.others.Commit;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiControllerViewSALogAnalytics {
    @FXML
    private LineChart lineChartSA;
    @FXML
    private CategoryAxis xAxisChart;
    @FXML
    public NumberAxis yAxisChart;
    @FXML
    private AnchorPane anchorParentLogSA;
    @FXML
    private Label labelLogOut;
    @FXML
    private SVGPath svgProfile;

    private double xUpload=0;
    private double yUpload=0;
    private Stage stage;


    public void initialize() throws GitAPIException, IOException {
        makeDraggable();
        makeLogOut();
        initChart();

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }


    public double commitCounter(List<Commit> listOfCommit, String data){
        double counter = 0;

        String dateToCompare = data.substring(0, 8);

        for (Commit cmt : listOfCommit) {
            if(cmt.getData().substring(0,8).equals(dateToCompare)){counter=counter+1;}
        }

        return counter;
    }


    public void initChart() throws GitAPIException, IOException {

        ViewLogAnalytics vla = new ViewLogAnalytics();

        XYChart.Series<String, Double> series1 = new XYChart.Series();   // Andamento Commit.

        XYChart.Series<String, Double> series2 = new XYChart.Series();   // Media Commit.
        XYChart.Series<String, Double> series3 = new XYChart.Series();   // Upper Limit.
        XYChart.Series<String, Double> series4 = new XYChart.Series();   // Lower Limit.

        yAxisChart.setLabel("Numero Commit");
        yAxisChart.setTickUnit(1);
        series1.setName("Commit");
        series2.setName("Center Line");
        series3.setName("Upper Control Limit");
        series4.setName("Lower Control Limit");

        int dayCounter = 0;
        double sumNumberCommit = 0;
        double averageCommit = 0;
        double stdv = 0;
        double stdvSum = 0;
        double upperLimit = 0;
        double lowerLimit = 0;

        List<Double> listValue = new ArrayList<>();

        List<Commit> listOfCommit = vla.initializeCommitArray();

        // Scorre la lista dei commit ed inizializza il grafico principale.
        for (int i=0; i < listOfCommit.size(); i++) {
            Commit cmtPrevious = null;
            Commit cmt = listOfCommit.get(i);

            double numberCommit = commitCounter(listOfCommit, cmt.getData());
            String data = cmt.getData().substring(0,8);

            if(i>0){   // Salta inserimenti duplicati nel grafico
                cmtPrevious = listOfCommit.get(i-1);
                String previousData = cmtPrevious.getData().substring(0,8);

                if(data.equals(previousData)){
                    continue;
                }
            }

            dayCounter = dayCounter +1;   // Conta il numero totale dei giorni in cui sono stati effetuati i commit.
            sumNumberCommit = sumNumberCommit + numberCommit;   // Somma il numero di tutti i commit giorno per giorno.
            listValue.add(numberCommit);  // Aggiunge ad un lista il numero dei commit per ogni giorno analizzato.

            series1.getData().add(new XYChart.Data<String, Double>(data, numberCommit));
        }

        averageCommit = sumNumberCommit / dayCounter;
        

        //Calcola deviazione standard
        for (int i=0; i < listValue.size(); i++) {
            stdvSum += Math.pow((listValue.get(i) - averageCommit),2);
        }

        stdv = Math.sqrt(stdvSum / (listValue.size() - 1));  // Deviazione Standard.

        upperLimit = averageCommit + (3*stdv);  // Limite Superiore
        lowerLimit = averageCommit - (3*stdv);  // Limite Inferiore


        // Scorre la lista dei commit ed inizializza i grafici bounder.
        for (int i=0; i < listOfCommit.size(); i++) {
            Commit cmtPrevious = null;
            Commit cmt = listOfCommit.get(i);

            String data = cmt.getData().substring(0,8);

            if(i>0){   // Salta inserimenti duplicati nel grafico
                cmtPrevious = listOfCommit.get(i-1);
                String previousData = cmtPrevious.getData().substring(0,8);

                if(data.equals(previousData)){
                    continue;
                }
            }

            series2.getData().add(new XYChart.Data<String, Double>(data, averageCommit));
            series3.getData().add(new XYChart.Data<String, Double>(data, upperLimit));
            series4.getData().add(new XYChart.Data<String, Double>(data, lowerLimit));
        }


        // Setta il grafico con i dati passati.
        lineChartSA.getData().addAll(series1, series2, series3, series4);
        

        //Personalizza Grafico
        yAxisChart.setAutoRanging(false);
        yAxisChart.setLowerBound(lowerLimit - 2);
        yAxisChart.setUpperBound(upperLimit + 2);


        // Rimuove nodi grafici dalle serie del lineChart.
        for (XYChart.Data<String, Double> data : series2.getData()) {
            StackPane stackPane = (StackPane) data.getNode();
            stackPane.setVisible(false);
        }
        for (XYChart.Data<String, Double> data : series3.getData()) {
            StackPane stackPane = (StackPane) data.getNode();
            stackPane.setVisible(false);
        }
        for (XYChart.Data<String, Double> data : series4.getData()) {
            StackPane stackPane = (StackPane) data.getNode();
            stackPane.setVisible(false);
        }

    }


    private void makeDraggable(){
        anchorParentLogSA.setOnMousePressed((eventUpload -> {
            xUpload=eventUpload.getSceneX();
            yUpload= eventUpload.getSceneY();
        }));

        anchorParentLogSA.setOnMouseDragged((eventUpload -> {
            stage = (Stage) ((Node)eventUpload.getSource()).getScene().getWindow();
            stage.setX(eventUpload.getScreenX() - xUpload);
            stage.setY(eventUpload.getScreenY() - yUpload);
        }));
    }

    public void exitWindowUpload() {
        stage = (Stage) anchorParentLogSA.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentLogSA.getScene().getWindow();
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


    public void goToMainLog(ActionEvent event) throws IOException {
        SceneController scsam = new SceneController();
        scsam.switchToAnalytics(event);
    }
}
