package com.artistcorner.engclasses.others;

import java.net.URL;

public enum ExceptionsTypeManager {

    EMPTYFIELD(0, "/view/exceptions/EmptyFieldException.fxml"),
    EMPTYPATH(1, "/view/exceptions/EmptyPathException.fxml"),
    DUPLICATEARTWORK(2, "/view/exceptions/DuplicateArtWorkException.fxml"),  //Opera già presente
    PROPOSALNOTFOUND(3, "/view/exceptions/ProposalNotFoundException.fxml"), // Nessuna proposta attuale
    SELLARTNOTFOUND(4, "/view/exceptions/SellNotFoundException.fxml"), // Nessun opera venduta
    ARTWORKNOTFOUND(5, "/view/exceptions/ArtworkNotFoundException.fxml"), // Nessun opera caricata
    SENTPROPOSALNOTFOUND(6,"/view/exceptions/SentProposalNotFoundException.fxml"),//Nessuna proposta inviata

    EMPTYFIELD_MOBILE(7, "/view/mobile/exceptions/EmptyFieldMobileException.fxml"),
    EMPTYPATH_MOBILE(8, "/view/mobile/exceptions/EmptyPathMobileException.fxml"),
    DUPLICATEARTWORK_MOBILE(9, "/view/mobile/exceptions/DuplicateArtWorkMobileException.fxml"),  //Opera già presente
    PROPOSALNOTFOUND_MOBILE(10, "/view/mobile/exceptions/ProposalNotFoundMobileException.fxml"), // Nessuna proposta attuale
    SELLARTNOTFOUND_MOBILE(11, "/view/mobile/exceptions/SellNotFoundMobileException.fxml"), // Nessun opera venduta
    ARTWORKNOTFOUND_MOBILE(12, "/view/mobile/exceptions/ArtworkNotFoundMobileException.fxml"), // Nessun opera caricata
    SENTPROPOSALNOTFOUND_MOBILE(13,"/view/mobile/exceptions/SentProposalNotFoundMobileException.fxml");//Nessuna proposta inviata


    private final int type;
    private final String path;

    private ExceptionsTypeManager(int t, String p){
        this.type = t;
        this.path = p;
    }

    public int getType() {
        return type;
    }

    public static URL getUrl(ExceptionsTypeManager subview){
        return ExceptionsTypeManager.class.getResource(subview.path);
    }

}
