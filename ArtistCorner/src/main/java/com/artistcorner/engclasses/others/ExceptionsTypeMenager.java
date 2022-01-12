package com.artistcorner.engclasses.others;

import java.net.URL;

public enum ExceptionsTypeMenager {

    EMPTYFIELD(0, "/view/exceptions/EmptyFieldException.fxml"),
    EMPTYPATH(1, "/view/exceptions/EmptyPathException.fxml"),
    DUPLICATEARTWORK(2, "/view/exceptions/DuplicateArtWorkException.fxml"),  //Opera già presente
    PROPOSALNOTFOUND(3, "/view/exceptions/ProposalNotFoundException.fxml"), // Nessuna proposta attuale, tramite il controllo sul ritorno del null (da modificare il DAO)
    SELLARTNOTFOUND(4, "/view/exceptions/SellNotFoundException.fxml"); // Nessun opera venduta
    //ARTWORKNOTUPLOADYET(); // Nessun opera caricata, non lo puoi fare qui, bisogna farlo come il login con un anchor nascosto
    // Aggiungere errore per il recupero della serializzazione?

    private final int type;
    private final String path;

    private ExceptionsTypeMenager(int t, String p){
        this.type = t;
        this.path = p;
    }

    public int getType() {
        return type;
    }

    public static URL getUrl(ExceptionsTypeMenager subview){
        return ExceptionsTypeMenager.class.getResource(subview.path);
    }

}
