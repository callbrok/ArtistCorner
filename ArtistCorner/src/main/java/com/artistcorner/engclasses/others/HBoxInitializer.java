package com.artistcorner.engclasses.others;

import com.artistcorner.controller.guicontroller.viewsearchartworkgallery.GuiControllerViewSearchArtWorkGallery;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class HBoxInitializer extends HBox {
   String labelTitolo;
   String labelArtista;
   Image img;
   int idOpera;
   double price;
   String labelButton;
   int idUser;
   int idArtista;
   ArrayList<Integer> arrayList;
   String input;

    public HBoxInitializer(String labelTitolo, String labelArtista, Image img, int idOpera, double price, String labelButton, int idUser, int idArtista, ArrayList<Integer> arrayList,String input) {
            this.labelTitolo = labelTitolo;
            this.labelArtista = labelArtista;
            this.arrayList = arrayList;
            this.idUser = idUser;
            this.img = img;
            this.idOpera = idOpera;
            this.price = price;
            this.labelButton = labelButton;
            this.idArtista = idArtista;
            this.input = input;
    }

    public int getIdOpera() {return idOpera;}
    public ArrayList<Integer> getArrayList() {return arrayList;}
    public double getPrice() {return price;}
    public Image getImg() {return img;}
    public int getIdArtista() {return idArtista;}
    public int getIdUser() {return idUser;}
    public String getLabelArtista() {return labelArtista;}
    public String getLabelButton() {return labelButton;}
    public String getLabelTitolo() {return labelTitolo;}
    public String getInput() {return input;}
}
