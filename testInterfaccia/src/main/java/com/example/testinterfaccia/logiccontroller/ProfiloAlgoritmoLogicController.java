package com.example.testinterfaccia.logiccontroller;

import com.example.testinterfaccia.Nodo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ProfiloAlgoritmoLogicController {

    public ArrayList<Nodo> initializeTreeTxt(){
        ArrayList<Nodo> arraylist = new ArrayList<Nodo>();
        int idAppartenenza, idProprio;
        String decisione, domanda;

        String line = "";
        String splitBy = ",";

        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/treeStructure.txt"));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                if (line.charAt(0) == '/'){continue;}

                String[] nodo = line.split(splitBy);
                //use comma as separator

                idAppartenenza = Integer.parseInt(nodo[0]);
                idProprio = Integer.parseInt(nodo[1]);
                decisione = nodo[2];
                domanda = nodo[3];

                arraylist.add(new Nodo(idAppartenenza,idProprio,decisione,domanda));

            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

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
