package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.Nodo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GetReccomandation {
    // Inizializza la risposta dell'algoritmo.
    String[] risposta = {"Nessuna Risposta","Nessuna Risposta","Nessuna Risposta","Nessuna Risposta","Nessuna Risposta"};

    /**
     * Inizializza l'albero con i nodi, presi dal file "treeStructure.txt".
     */
    public ArrayList<Nodo> initializeTreeTxt(){
        ArrayList<Nodo> arraylist = new ArrayList<Nodo>();
        int idAppartenenza, idProprio;
        String decisione, domanda, parteSoluzione, key;

        String line = "";
        String splitBy = ",";

        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("ArtistCorner/src/main/resources/auxiliaryfacilities/treeStructure.txt"));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                if (line.charAt(0) == '/'){continue;}  // salta le righe di commento nel file di testo

                String[] nodo = line.split(splitBy);   // tokenizza la linea tramite il delimitatore ','

                idAppartenenza = Integer.parseInt(nodo[0]);
                idProprio = Integer.parseInt(nodo[1]);
                decisione = nodo[2];
                domanda = nodo[3];
                parteSoluzione = nodo[4];
                key = nodo[5];

                arraylist.add(new Nodo(idAppartenenza,idProprio,decisione,domanda, parteSoluzione, key));   // inizializza un singolo nodo dell'albero

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
        Nodo errorNode = new Nodo(0, 0, "Error", "FINE DELL'ALBERO","ULTIMO NODO", "");
        Nodo currentNode = getCurrentNode(idLivello, arraylist); // Prende il nodo corrente.

        String [] arrRandAns = {"Y", "N"};
        Random random = new Random();
        int select = random.nextInt(arrRandAns.length);
        String randomChoice = arrRandAns[select];  // Inizializza la scelta randomica.

        if (str.equals("Y")) {

            // Passa l'identificativo della soluzione e la parte della soluzione del nodo corrente.
            createSolution(currentNode.getSolutionPart(), currentNode.getKeyObj());

            for (Nodo n : arraylist) {    // Scorre tra tutti i nodi dell'albero associati ad una risposta affermativa.

                // Se il nodo di risposta affermativa ha come padre il nodo corrente identificato dall'idLivello.
                if (n.getDecisione().contains("Y") && n.getIdAppartenenza() == idLivello) {

                    return n;      // Ritorna il nodo successivo per mostrarlo a video.
                }
            }
        }

        if (str.equals("N")) {
            for (Nodo n : arraylist) {
                if (n.getDecisione().contains("N") && n.getIdAppartenenza() == idLivello) {
                    return n;
                }
            }
        }

        if (str.equals("YN")) {

            // Passa l'identificativo della soluzione e la parte della soluzione del nodo corrente.
            if(randomChoice == "Y"){createSolution(currentNode.getSolutionPart(), currentNode.getKeyObj());}

            for (Nodo n : arraylist) {
                if (n.getDecisione().contains(randomChoice) && n.getIdAppartenenza() == idLivello) {
                    return n;
                }
            }
        }

        return errorNode; // mostra nodo di fine albero
    }


    public Nodo getCurrentNode(int idLivello, ArrayList<Nodo> arraylist){
        // Scorre tutti i nodi e ne ritorna quello con idProprio + idLivello, ergo il
        // nodo corrente.
        for (Nodo n : arraylist) {
            if (n.getIdProprio() == idLivello) {
                return n;
            }
        }
        return null;
    }

    // Ritorna la soluzione.
    public String[] getSolution(){ return risposta;}

    // Ritorna la soluzione per la serializzazione.
    public String getSerialSolution(){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i<risposta.length;i++){
            stringBuilder.append(risposta[i]);
            stringBuilder.append("-");
        }

        return stringBuilder.toString();
    }

    // Setta la risposta.
    public void setSerialSolution( String serialRisposta){
        risposta = serialRisposta.split("-", risposta.length);
    }


    public void createSolution(String sol, String key){
        // A seconda della key associata alla domanda inserisco una parte della
        // soluzione nella stringa apposita.
        switch (key){
            case "[A]":
                risposta[0] = sol;
                break;
            case "[W]":
                risposta[1] = sol;
                break;
            case "[S]":
                risposta[2] = sol;
                break;
            case "[D]":
                risposta[3] = sol;
                break;
            case "[R]":
                risposta[4] = sol;
                break;
        }

    }



}
