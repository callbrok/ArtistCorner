package com.artistcorner.controller.guicontroller.viewsearchartworkgallery;

import com.artistcorner.controller.applicationcontroller.ViewSearchArtWorkGallery;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.exceptions.ArtWorkNotFoundException;
import com.artistcorner.engclasses.exceptions.ExceptionView;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import com.artistcorner.engclasses.others.ExceptionsFactory;
import com.artistcorner.engclasses.others.ExceptionsTypeMenager;
import com.artistcorner.engclasses.others.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class GuiControllerViewSearchArtWorkGallery {
    @FXML
    private AnchorPane anchorParent,anchorPane;
    public Pane paneFound;
    public Label labelLogOut;
    public ListView<HBoxCell> listView;
    public TextField textField;
    public Button buttonSearch;
    public Label labelUsernameDisplay;
    public Button buttonCanc;
    public SVGPath svgProfile;
    public Button button1;
    public Button button2;
    public Button button3;
    @FXML
    private Pane paneExceptionLoad;
    private double x=0, y=0;
    Stage stage;
    ArtGalleryBean gal;



    public void initialize() throws SQLException {
        makeDraggable();
        setTooltipMenu();
        makeLogOut();
        svgProfile.setScaleX(0.07);
        svgProfile.setScaleY(0.07);
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

    public void getGallery(ArtGalleryBean loggedGallery) {
        gal = loggedGallery;
        labelUsernameDisplay.setText(gal.getNome());
    }

    private void makeDraggable(){
        anchorParent.setOnMousePressed(((event) -> {
            x=event.getSceneX();
            y= event.getSceneY();
        }));

        anchorParent.setOnMouseDragged(((event) -> {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    public void exitWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow() {
        stage = (Stage) anchorParent.getScene().getWindow();
        stage.setIconified(true);
    }
    public void setTooltipMenu(){
        button1.setTooltip(new Tooltip("Home"));
        button2.setTooltip(new Tooltip("Cerca Opera"));
        button3.setTooltip(new Tooltip("Preferiti"));
    }
    public void enterSearch(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode()== KeyCode.ENTER){
            String input= textField.getText();
            anchorPane.setVisible(true);
            populateListView(input);
        }
    }
    public void buttonSearchOnClick() throws SQLException, IOException{
        String input= textField.getText();
        anchorPane.setVisible(true);
        paneFound.setVisible(false);
        populateListView(input);
    }
    public void buttonCancOnClick(){
        textField.setText("");
    }

    public void switchToProfiloGallery(ActionEvent actionEvent) throws IOException, SQLException {
        SceneController sc = new SceneController();
        sc.switchToSceneProfiloGallery(actionEvent,gal);
    }
    public void switchToGallerySummary(ActionEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToSceneGallerySummary(actionEvent,gal);
    }
    public class HBoxCell extends HBox {
        Label labelArtWorkName = new Label();
        Label labelArtistName = new Label();
        public Button buttonOfferta = new Button();
        Label labelArtistNameDefault = new Label();
        Label labelArtWorkDefault = new Label();

        public HBoxCell(String labelTitolo, String labelArtista, Image img, int idOpera, double price, String labelButton, int idGallery, int idArtista, List<Integer> arrayListProposte,String input) throws SQLException, IOException {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            labelArtWorkName.setText(labelTitolo);
            labelArtWorkName.setAlignment(Pos.CENTER);
            labelArtWorkName.setStyle("-fx-text-fill: #39A67F; -fx-font-weight: bold ");
            labelArtistName.setText(labelArtista);
            labelArtistName.setAlignment(Pos.CENTER);
            labelArtistName.setStyle("-fx-text-fill: #39A67F; -fx-font-weight:bold ");
            labelArtWorkName.setPrefSize(100, 50);
            labelArtistName.setPrefSize(100, 50);
            VBox vBox1 = new VBox(labelArtWorkName, labelArtistName);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold ");
            HBox.setHgrow(labelArtWorkName, Priority.ALWAYS);
            HBox.setMargin(vBox1, new Insets(10, 100, 10, 25));
            VBox vBox2 = new VBox(labelArtWorkDefault, labelArtistNameDefault);
            vBox2.setAlignment(Pos.CENTER);
            labelArtWorkDefault.setText("Titolo Opera: ");
            labelArtWorkDefault.setAlignment(Pos.CENTER);
            labelArtWorkDefault.setPrefSize(100, 50);
            labelArtWorkDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            HBox.setMargin(vBox2, new Insets(10, 75, 10, 75));
            labelArtWorkDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            labelArtistNameDefault.setText("Nome Autore: ");
            labelArtistNameDefault.setAlignment(Pos.CENTER);
            labelArtistNameDefault.setPrefSize(100, 50);
            labelArtistNameDefault.setStyle("-fx-font-size: 14px; -fx-font-weight: bold ;-fx-text-fill: #000000;");
            buttonOfferta.setText(labelButton);
            buttonOfferta.setPrefSize(150, 100);
            buttonOfferta.setStyle("-fx-font-size: 16px;");
            ViewSearchArtWorkGallery sa = new ViewSearchArtWorkGallery();

            if (arrayListProposte.contains(idArtista)) {
                System.out.println(arrayListProposte);
                buttonOfferta.setText("Ritira Proposta");
            }
            buttonOfferta.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    String answer = null;
                    try {
                        answer = sa.manageButtonClick(buttonOfferta, idGallery, idArtista);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    buttonOfferta.setText(answer);
                    try {
                        populateListView(input);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            this.getChildren().addAll(imageView, vBox2, vBox1, buttonOfferta);
        }

    }
    public void populateListView(String input) throws SQLException, IOException {
        if (listView.getItems().size()!=0){
            listView.getItems().clear();
        }
        ViewSearchArtWorkGallery vsg = new ViewSearchArtWorkGallery();
        ArtistBean artist=null;
        Blob artWorkBlob =null;

        try{
            List<ArtWorkBean> arrayOfArtWork = vsg.retrieveGallerySearchArtWorkByName(input);
            List<Integer> artistIdList = vsg.retrieveGallerySearchArtistId(gal);
            for (ArtWorkBean artWork: arrayOfArtWork) {
                artWorkBlob = vsg.retrieveGallerySearchArtWorkBlob(artWork.getIdOpera());
                artist = vsg.retrieveGallerySearchArtistName(artWork);
                InputStream inputStream = null;
                try {
                    inputStream = artWorkBlob.getBinaryStream();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Image image1 = new Image(inputStream, 100, 100, true, false);
                listView.getItems().add(new HBoxCell(artWork.getTitolo(), artist.getNome()+" "+artist.getCognome(),image1, artWork.getIdOpera(), artWork.getPrezzo(),"Invia Proposta", gal.getGalleria(), artist.getIdArtista(),artistIdList,input));

            }
        } catch ( ArtWorkNotFoundException throwables) {
            ExceptionsFactory ef = ExceptionsFactory.getInstance();
            ExceptionView ev;

            ev = ef.createView(ExceptionsTypeMenager.ARTWORKNOTFOUND);
            paneExceptionLoad.getChildren().add(ev.getExceptionPane());
        }
    }

}
