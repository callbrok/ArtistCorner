package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.controller.guicontroller.listviewinizializer.GuiControllerListViewInizializer;
import com.artistcorner.engclasses.dao.BuyerDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class ListViewInizilizer {
    GuiControllerListViewInizializer gc = new GuiControllerListViewInizializer();

    public void manageButtonClick(ActionEvent arg0,Button buttonAcquista, Button buttonPreferiti, int idOpera, int idBuyer ){

                switch (buttonPreferiti.getText()){
                    case "Rimuovi dai Preferiti":{
                        try {
                            BuyerDAO.removeArtWorkFromFavourites(idOpera, idBuyer);
                            gc.buttonSwitch(buttonPreferiti,"Aggiungi ai Preferiti");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                    }
                    case "Aggiungi ai Preferiti":{
                        try {
                            BuyerDAO.addArtWorkToFavourites(idOpera,idBuyer);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        gc.buttonSwitch(buttonPreferiti,"Rimuovi dai Preferiti");
                        break;

                    }
                    default:
                }
    }

    public void finishPayment(Button buttonAcquista, Button buttonPreferiti, int idOpera, int idBuyer){
        buttonPreferiti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                try {
                    BuyerDAO.addArtWorkComprata(idOpera,idBuyer);
                    BuyerDAO.switchFlagVendibile(idOpera);
                    BuyerDAO.removeArtWorkFromFavourites(idOpera,idBuyer);
                    gc.buttonSwitch(buttonAcquista,"Opera Acquistata!");gc.buttonDisable(buttonPreferiti,buttonAcquista);
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
                    gc.buttonSwitch(buttonAcquista,"Opera Acquistata!");gc.buttonDisable(buttonPreferiti,buttonAcquista);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });
    }

}
