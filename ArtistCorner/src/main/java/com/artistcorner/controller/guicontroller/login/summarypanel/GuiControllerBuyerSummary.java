package com.artistcorner.controller.guicontroller.login.summarypanel;

import com.artistcorner.controller.applicationcontroller.login.summaries.ViewBuyerSummary;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerBuyerSummary {
    @FXML
    private AnchorPane anchorGuest;
    @FXML
    private Button button3;
    @FXML
    private AnchorPane anchorParentBuyer;
    @FXML
    private ListView<String> listViewCompra;
    @FXML
    private Label labelUsernameDisplay;
    @FXML
    private Label labelLogOutBuyerSum;
    @FXML
    private Stage stageBuyer;
    @FXML
    private SVGPath svgProfileBuyerSum;

    private double x=0;
    private double y=0;
    private BuyerBean buy;


    public void initialize(){
        makeDraggable();
        makeLogOutBuyer();

        anchorGuest.setVisible(false);
        svgProfileBuyerSum.setScaleX(0.07);
        svgProfileBuyerSum.setScaleY(0.07);
    }

    public void makeLogOutBuyer(){
        labelLogOutBuyerSum.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void getBuyer(BuyerBean loggedBuyer) {
        buy = loggedBuyer;

        if(buy.getIdBuyer() == 13){initGuest();}

        labelUsernameDisplay.setText(buy.getNome()+" "+buy.getCognome());
        initializeOpereComprate(listViewCompra,buy);
    }


    public void initGuest(){
        labelLogOutBuyerSum.setText("ACCEDI");
        button3.setDisable(true);
        anchorGuest.setVisible(true);
    }

    private void makeDraggable(){
        anchorParentBuyer.setOnMousePressed((eventPressBuy -> {
            x=eventPressBuy.getSceneX();
            y= eventPressBuy.getSceneY();
        }));

        anchorParentBuyer.setOnMouseDragged((eventDragBuy -> {
            stageBuyer = (Stage) ((Node)eventDragBuy.getSource()).getScene().getWindow();
            stageBuyer.setX(eventDragBuy.getScreenX() - x);
            stageBuyer.setY(eventDragBuy.getScreenY() - y);
        }));
    }

    public void minimizeWindow() {
        stageBuyer = (Stage) anchorParentBuyer.getScene().getWindow();
        stageBuyer.setIconified(true);
    }

    public void exitWindow() {
        stageBuyer = (Stage) anchorParentBuyer.getScene().getWindow();
        stageBuyer.close();
    }

    public void switchToSearchArtWorkBuyer(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneSearchArtWorkBuyer(actionEvent,buy);
    }

    public void switchToFavouritesBuyer(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneFavouritesBuyer(actionEvent,buy);
    }
    public void initializeOpereComprate(ListView<String> listViewComprate, BuyerBean buy)  {
        ViewBuyerSummary vbs = new ViewBuyerSummary();

        List<ArtWorkBean> arrayOfComprateD = vbs.retrieveAllComprate(buy);

        for (ArtWorkBean n : arrayOfComprateD) {
            ArtWorkBean artworkComprata = vbs.retrieveArtWorks(n.getIdOpera(), 0);
            listViewComprate.getItems().add("Titolo Opera:  " + artworkComprata.getTitolo() + "     Prezzo di acquisto:   € " + artworkComprata.getPrezzo());  // Popola la listView.
        }
    }

}
