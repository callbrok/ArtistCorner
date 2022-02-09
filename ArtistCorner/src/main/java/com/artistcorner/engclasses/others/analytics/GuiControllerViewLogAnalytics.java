package com.artistcorner.engclasses.others.analytics;

import com.artistcorner.engclasses.others.Commit;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
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
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.*;

public class GuiControllerViewLogAnalytics {
    @FXML
    private AnchorPane anchorParentLog;
    @FXML
    private Label labelLogOut;
    @FXML
    private ScatterChart scatterChartCommit;
    @FXML
    private SVGPath svgProfile;
    @FXML
    private CategoryAxis xAxisChart;
    @FXML
    private NumberAxis yAxisChart;

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
        xAxisChart.setLabel("\nClicca su qualsiasi nodo (cerchio o quadrato)");

        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
    }


    
    public Dialog showCommitDialog(List<Commit> listOfCommit, String date, Dialog<String> dialog){
        String dateToCompare = date;

        if(date.length() == 14){
            // Se viene passata una data con orario dal formato:
            //         #X:XX
            //         XX:X#
            String[] datePart = date.split(":");
            String part1 = datePart[0];
            String part2 = datePart[1];

            if(part2.length() == 2){dateToCompare = date + "0";}
            if(part1.length() == 11){dateToCompare = part1.substring(0,part1.length()-1) + "0" + part1.charAt(part1.length()-1) + ":" + part2;}
        }

        for (Commit cmt : listOfCommit) {

            if(cmt.getData().equals(dateToCompare)){
                dialog.setTitle("Commit del " + cmt.getData());
                dialog.setHeaderText("Committato da " + cmt.getNome());

                dialog.setContentText(cmt.getMessaggio());
            }
        }

        dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);  // Rimuove il "..." se messaggio troppo lungo.
        return dialog;
    }


    public void initChart() throws GitAPIException, IOException {
        Dialog<String> dialog = new Dialog<>();
        ButtonType type = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);

        ViewLogAnalytics vla = new ViewLogAnalytics();

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

            if(data.equals("12/11/21") || data.equals("24/01/22") ){continue;}

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
                    showCommitDialog(listOfCommit, date, dialog).showAndWait();
                }
            });
        }

        // Event handler per i commit di Alessio.
        for(XYChart.Data<String, Double> data1: series1.getData()){
            data1.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String date = data1.getXValue() + ", " + String.valueOf(data1.getYValue()).replace(".",":");
                    showCommitDialog(listOfCommit, date, dialog).showAndWait();
                }
            });
        }

    }


    private void makeDraggable(){
        anchorParentLog.setOnMousePressed((eventUpload -> {
            xUpload=eventUpload.getSceneX();
            yUpload= eventUpload.getSceneY();
        }));

        anchorParentLog.setOnMouseDragged((eventUpload -> {
            stage = (Stage) ((Node)eventUpload.getSource()).getScene().getWindow();
            stage.setX(eventUpload.getScreenX() - xUpload);
            stage.setY(eventUpload.getScreenY() - yUpload);
        }));
    }

    public void exitWindowUpload() {
        stage = (Stage) anchorParentLog.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParentLog.getScene().getWindow();
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

    public void goToSA(ActionEvent event) throws IOException {
        SceneController scsa = new SceneController();
        scsa.switchToAnalyticsSA(event);
    }
}
