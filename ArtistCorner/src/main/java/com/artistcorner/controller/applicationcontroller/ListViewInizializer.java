package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.dao.BuyerDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListViewInizializer {

    public static class HBoxCell extends HBox {
        Label labelArtWorkName = new Label();
        Label labelCompleted = new Label();
        Label labelArtistName = new Label();
        Button buttonAcquista = new Button();
        public Button buttonPreferiti = new Button();
        Label prezzo = new Label();
        Label prezzo1 = new Label();
        Label labelArtistNameDefault = new Label();
        Label labelArtWorkDefault = new Label();

        public HBoxCell(String labelText, String labelText1, double price, Image img, int idOpera, String labelPreferiti, int idBuyer, ArrayList<Integer> arrayListArtWorkId) throws SQLException, IOException {
            super();
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            labelArtWorkName.setText(labelText);
            labelArtWorkName.setAlignment(Pos.CENTER);
            labelArtWorkName.setStyle("-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistName.setText(labelText1);
            labelArtistName.setAlignment(Pos.CENTER);
            labelArtistName.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtWorkName.setPrefSize(100, 50);
            labelArtistName.setPrefSize(100, 50);
            prezzo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #39A67F;");
            prezzo.setMaxWidth(Double.MAX_VALUE);
            prezzo.setText("€ " + Double.toString(price));prezzo.setPrefSize(100, 100);prezzo.setAlignment(Pos.CENTER);
            prezzo1.setText("Prezzo Opera: ");prezzo1.setPrefSize(100, 100);prezzo1.setAlignment(Pos.CENTER);prezzo1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setAlignment(Pos.CENTER);vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");
            HBox.setHgrow(labelArtWorkName, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 25, 10, 25));
            VBox vBox2 = new VBox(labelArtWorkDefault, labelArtistNameDefault);
            vBox2.setAlignment(Pos.CENTER);
            labelArtWorkDefault.setText("Titolo Opera: ");labelArtWorkDefault.setAlignment(Pos.CENTER);labelArtWorkDefault.setPrefSize(100, 50);
            labelArtWorkDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            HBox.setMargin(vBox2, new Insets(10, 25, 10, 25));
            labelArtWorkDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            labelArtistNameDefault.setText("Nome Autore: ");
            labelArtistNameDefault.setAlignment(Pos.CENTER);
            labelArtistNameDefault.setPrefSize(100, 50);
            labelArtistNameDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            buttonAcquista.setText("Acquista");
            buttonAcquista.setPrefSize(150, 50);
            buttonAcquista.setStyle(" -fx-font-size: 14px;");
            buttonPreferiti.setText(labelPreferiti);
            buttonPreferiti.setPrefSize(150, 50);
            buttonPreferiti.setStyle("-fx-font-size: 14px;");
            VBox vBox = new VBox(buttonAcquista, buttonPreferiti);
            vBox.setAlignment(Pos.CENTER);
            ActionEvent actionEvent = new ActionEvent();
            buttonAcquista.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    buttonPreferiti.setText("Paga con Carte");
                    buttonAcquista.setText("Paga con PayPal");
                    finishPayment(buttonAcquista,buttonPreferiti,idOpera,idBuyer,vBox);
                }

            });
            if (arrayListArtWorkId.contains(idOpera)){
                buttonPreferiti.setText("Rimuovi dai Preferiti");
            }
            buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    switch (buttonPreferiti.getText()){
                        case "Rimuovi dai Preferiti":{
                            try {
                                BuyerDAO.removeArtWorkFromFavourites(idOpera, idBuyer);
                                buttonPreferiti.setText("Aggiungi ai Preferiti");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                        case "Aggiungi ai Preferiti":{
                            try {
                                BuyerDAO.addArtWorkToFavourites(idOpera,idBuyer);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            buttonPreferiti.setText("Rimuovi dai Preferiti");

                        }
                        default:
                    }
                }

            });

            this.getChildren().addAll(imageView, vBox2, vBox1, prezzo1, prezzo, vBox);
        }

        public void finishPayment(Button buttonAcquista,Button buttonPreferiti,int idOpera,int idBuyer,VBox vBox){
            buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        BuyerDAO.addArtWorkComprata(idOpera,idBuyer);
                        BuyerDAO.switchFlagVendibile(idOpera);
                        BuyerDAO.removeArtWorkFromFavourites(idOpera,idBuyer);
                        buttonAcquista.setText("Opera Acquistata!");buttonAcquista.setDisable(true);buttonPreferiti.setVisible(false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            });
            buttonAcquista.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        BuyerDAO.addArtWorkComprata(idOpera,idBuyer);
                        BuyerDAO.switchFlagVendibile(idOpera);
                        BuyerDAO.removeArtWorkFromFavourites(idOpera,idBuyer);
                        buttonAcquista.setText("Opera Acquistata!");buttonAcquista.setDisable(true);buttonPreferiti.setVisible(false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

}