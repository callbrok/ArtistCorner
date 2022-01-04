package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.Nodo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GetReccomandation {

    /**
     * Inizializza l'albero con i nodi, presi dal file "treeStructure.txt".
     */
    public ArrayList<Nodo> initializeTreeTxt(){
        ArrayList<Nodo> arraylist = new ArrayList<Nodo>();
        int idAppartenenza, idProprio;
        String decisione, domanda;

        String line = "";
        String splitBy = ",";

        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/auxiliaryfacilities/treeStructure.txt"));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                if (line.charAt(0) == '/'){continue;}  // salta le righe di commento nel file di testo

                String[] nodo = line.split(splitBy);   // tokenizza la linea tramite il delimitatore ','

                idAppartenenza = Integer.parseInt(nodo[0]);
                idProprio = Integer.parseInt(nodo[1]);
                decisione = nodo[2];
                domanda = nodo[3];

                arraylist.add(new Nodo(idAppartenenza,idProprio,decisione,domanda));   // inizializza un singolo nodo dell'albero

            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return arraylist;
    }

    /**
     * Ritorna il nodo da visualizzare a video.
     */
    public Nodo decisionTree(String str, ArrayList<Nodo> arraylist, int idLivello){
        Nodo errorNode = new Nodo(0, 0, "Error", "FINE DELL'ALBERO");

        String [] arrRandAns = {"Y", "N"};
        Random random = new Random();
        int select = random.nextInt(arrRandAns.length);

        if (str.equals("Y")) {
            for (Nodo n : arraylist) {    // scorri tra tutti i nodi dell'albero associati ad una risposta affermativa
                if (n.getDecisione().contains("Y")) {
                    if (n.getIdAppartenenza() == idLivello) {    // se il nodo di risposta affermativa ha come padre il nodo corrente identificato dall'idLivello
                        return n;                                // sarà il nodo successivo, quindi lo ritorno per mostrarlo a video.
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

        return errorNode; // mostra nodo di fine albero
    }
}
