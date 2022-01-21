package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.Nodo;
import com.artistcorner.engclasses.exceptions.GetRaccomandationProblemException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GetReccomandation {
    // Inizializza la risposta dell'algoritmo.
    public static final String NO_ANSWER = "Nessuna Risposta";
    private Random random = new Random();

    private String[] risposta = {NO_ANSWER,NO_ANSWER,NO_ANSWER,NO_ANSWER,NO_ANSWER};

    /**
     * Inizializza l'albero con i nodi, presi dal file "treeStructure.txt".
     */
    public List<Nodo> initializeTreeTxt() {
        ArrayList<Nodo> arraylist = new ArrayList<>();

        String line = "";
        String splitBy = ",";

        try(BufferedReader br = new BufferedReader(new FileReader("ArtistCorner/src/main/resources/auxiliaryfacilities/treeStructure.txt"))) {

            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                if (line.charAt(0) == '/'){continue;}  // salta le righe di commento nel file di testo

                String[] nodo = line.split(splitBy);   // tokenizza la linea tramite il delimitatore ','

                int idAppartenenza = Integer.parseInt(nodo[0]);
                int idProprio = Integer.parseInt(nodo[1]);
                String decisione = nodo[2];
                String domanda = nodo[3];
                String parteSoluzione = nodo[4];
                String key = nodo[5];

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
    public Nodo decisionTree(String str, List<Nodo> arraylist, int idLivello) throws GetRaccomandationProblemException {
        Nodo errorNode = new Nodo(0, 0, "Error", "Fine dell'algoritmo","ULTIMO NODO", "");
        Nodo currentNode = getCurrentNode(idLivello, arraylist); // Prende il nodo corrente.

        String [] arrRandAns = {"Y", "N"};
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
            if(randomChoice.equals("Y")){createSolution(currentNode.getSolutionPart(), currentNode.getKeyObj());}

            for (Nodo n : arraylist) {
                if (n.getDecisione().contains(randomChoice) && n.getIdAppartenenza() == idLivello) {
                    return n;
                }
            }
        }

        return errorNode; // mostra nodo di fine albero
    }

    /**
     * Ritorna il nodo corrente.
     */
    public Nodo getCurrentNode(int idLivello, List<Nodo> arraylist){
        // Scorre tutti i nodi e ne ritorna quello con idProprio + idLivello, ergo il
        // nodo corrente.
        for (Nodo n : arraylist) {
            if (n.getIdProprio() == idLivello) {
                return n;
            }
        }
        return null;
    }

    /**
     * Ritorna la soluzione.
     */
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

    /**
     * Imposta la risposta.
     */
    public void setSerialSolution( String serialRisposta){
        risposta = serialRisposta.split("-", risposta.length);
        risposta[4] = risposta[4].substring(0, risposta[4].length()-1);
    }

    /**
     * Crea la soluzione.
     */
    public void createSolution(String sol, String key) throws GetRaccomandationProblemException {
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
            default:
                throw new GetRaccomandationProblemException("Problema nella compilazione della risposta");
        }

    }



}
