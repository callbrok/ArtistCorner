package com.example.testinterfaccia.logiccontroller;

import com.example.testinterfaccia.Nodo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ProfiloAlgoritmoLogicController {

    public ArrayList<Nodo> initializeTree(){
        ArrayList<Nodo> arraylist = new ArrayList<Nodo>();

        arraylist.add(new Nodo(1,2,"Y","una persona anziana?"));
        arraylist.add(new Nodo(1,3,"N","un animale?"));

        arraylist.add(new Nodo(2,4,"Y","OK PERSONA ANZIANA"));
        arraylist.add(new Nodo(2,5, "N","una persona di mezza età?"));

        arraylist.add(new Nodo(3,6,"Y","OK ANIMALE"));
        arraylist.add(new Nodo(3,7,"N", "BELLO DE CASA"));

        arraylist.add(new Nodo(5,8,"Y","OK PERSONA DI MEZZA ETÀ"));
        arraylist.add(new Nodo(5,9,"N","BELLO DE CASA"));

        return arraylist;
    }

    public Nodo decisionTree(String str, ArrayList<Nodo> arraylist, int idLivello){
        Nodo errorNode = new Nodo(0, 0, "Error", "FINE DELL'ALBERO");

        String [] arrRandAns = {"Y", "N"};
        Random random = new Random();
        int select = random.nextInt(arrRandAns.length);

        if (str.equals("Y")) {
            for (Nodo n : arraylist) {
                if (n.getDecisione().contains("Y")) {
                    if (n.getIdAppartenenza() == idLivello) {
                        return n;
                    }
                }
            }
        }

        if (str.equals("N")) {
            for (Nodo n : arraylist) {
                if (n.getDecisione().contains("N")) {
                    if (n.getIdAppartenenza() == idLivello) {
                        return n;
                    }
                }
            }
        }

        if (str.equals("YN")) {
            for (Nodo n : arraylist) {
                if (n.getDecisione().contains(arrRandAns[select])) {
                    if (n.getIdAppartenenza() == idLivello) {
                        return n;
                    }
                }
            }
        }

        return errorNode;
    }
}
